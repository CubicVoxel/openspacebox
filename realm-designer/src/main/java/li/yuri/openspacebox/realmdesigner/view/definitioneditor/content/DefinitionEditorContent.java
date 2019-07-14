/*
 * This file is part of OpenSpaceBox.
 * Copyright (C) 2019 by Yuri Becker <hi@yuri.li>
 *
 * OpenSpaceBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSpaceBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSpaceBox.  If not, see <http://www.gnu.org/licenses/>.
 */

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import li.yuri.openspacebox.realmdesigner.common.EventBus;
import li.yuri.openspacebox.realmdesigner.common.preferences.Preferences;
import li.yuri.openspacebox.realmdesigner.common.preferences.PreferencesManager;
import li.yuri.openspacebox.realmdesigner.common.preferences.RecentFileAddedEvent;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.menu.DefinitionEditorMenuBar;
import li.yuri.workspacefx.style.Defaults;

import java.io.File;
import java.time.LocalDateTime;

public class DefinitionEditorContent extends HBox implements DefinitionEditorMenuBar.DefinitionEditorMenuOwner {

    private static final double TYPE_SELECTION_PANEL_WIDTH = 220;

    private final TypeSelectionPanel typeSelectionPanel;
    private final TypeEditContainer typeEditContainer;
    private final li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.CurrentFile currentFile;

    public DefinitionEditorContent() {
        currentFile = new CurrentFile();

        typeSelectionPanel = new TypeSelectionPanel(currentFile);
        typeSelectionPanel.setPrefWidth(TYPE_SELECTION_PANEL_WIDTH);
        getChildren().add(typeSelectionPanel);
        setMargin(typeSelectionPanel, new Insets(Defaults.getInstance().spacingDefault));
        setHgrow(typeSelectionPanel, Priority.NEVER);

        currentFile.addTypeDefinitionsLoadedListener(typeSelectionPanel::setTypeDefinitions);

        typeEditContainer = new TypeEditContainer(currentFile);
        typeSelectionPanel.addTypeSelectedListener(typeEditContainer);
        getChildren().add(typeEditContainer);
        setMargin(typeEditContainer, new Insets(Defaults.getInstance().spacingDefault));
        setHgrow(typeEditContainer, Priority.ALWAYS);

    }

    @Override
    public void onOpen(File file) {
        currentFile.onOpen(file);

        String path = file.getPath();
        PreferencesManager.getInstance()
                .getPreferences()
                .getDefinitionEditorRecentFiles()
                .removeIf(recentFile -> recentFile.getFile().equals(path));
        PreferencesManager.getInstance()
                .getPreferences()
                .getDefinitionEditorRecentFiles()
                .add(new Preferences.RecentFile(path, LocalDateTime.now()));
        PreferencesManager.getInstance().persistPreferences();
        EventBus.getInstance().post(new RecentFileAddedEvent());
    }

    @Override
    public void save() {
        currentFile.save();
    }

    @Override
    public void saveAs(File file) {
        currentFile.saveAs(file);
    }

    @Override
    public boolean saveOpensSaveAs() {
        return currentFile.saveOpensSaveAs();
    }
}
