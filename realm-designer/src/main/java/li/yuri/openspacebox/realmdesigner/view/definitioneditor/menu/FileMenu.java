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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.menu;

import com.google.common.eventbus.Subscribe;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import li.yuri.openspacebox.realmdesigner.common.EventBus;
import li.yuri.openspacebox.realmdesigner.common.preferences.Preferences;
import li.yuri.openspacebox.realmdesigner.common.preferences.PreferencesManager;
import li.yuri.openspacebox.realmdesigner.common.preferences.RecentFileAddedEvent;
import lombok.Setter;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileMenu extends Menu {

    public static final FileChooser.ExtensionFilter TYPE_DEFINITION_FILTER = new FileChooser.ExtensionFilter
            ("TypeDefinition files", "*.xml");
    private final FileChooser openFileChooser = new FileChooser();
    private final FileChooser saveAsFileChooser = new FileChooser();
    private final MenuItem openMenuItem;
    private final Menu recentMenu;
    private final MenuItem saveMenuItem;
    private final MenuItem saveAsMenuItem;
    @Setter private FileMenuOwner fileMenuOwner;

    public FileMenu() {
        super("File");

        openFileChooser.getExtensionFilters().setAll(TYPE_DEFINITION_FILTER);
        saveAsFileChooser.getExtensionFilters().setAll(TYPE_DEFINITION_FILTER);

        recentMenu = createRecentMenu();
        updateRecentFilesMenu();

        openMenuItem = createOpenMenuItem();
        getItems().add(openMenuItem);
        getItems().add(recentMenu);
        getItems().add(new SeparatorMenuItem());
        saveMenuItem = createSaveMenuItem();
        getItems().add(saveMenuItem);
        saveAsMenuItem = createSaveAsMenuItem();
        getItems().add(saveAsMenuItem);

        EventBus.getInstance().register(this);
    }

    private MenuItem createOpenMenuItem() {
        MenuItem openMenuItem = new MenuItem("Open...");
        openMenuItem.setOnAction(event -> open(openMenuItem));
        return openMenuItem;
    }

    private Menu createRecentMenu() {
        return new Menu("Open Recent");
    }

    private MenuItem createSaveMenuItem() {
        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setOnAction(event -> save(saveMenuItem));
        return saveMenuItem;
    }


    private MenuItem createSaveAsMenuItem() {
        MenuItem saveAsMenuItem = new MenuItem("Save as...");
        saveAsMenuItem.setOnAction(event -> saveAs(saveAsMenuItem));
        return saveAsMenuItem;
    }

    private void open(MenuItem sender) {
        File file = openFileChooser.showOpenDialog(sender.getParentPopup().getOwnerWindow());
        if (file != null) {
            saveAsFileChooser.setInitialDirectory(file.getParentFile());
            saveAsFileChooser.setInitialFileName(file.getName());
            fileMenuOwner.onOpen(file);
        }
    }

    private void save(MenuItem sender) {
        if (fileMenuOwner.saveOpensSaveAs())
            saveAs(sender);
        else
            fileMenuOwner.save();
    }

    private void saveAs(MenuItem sender) {
        File file = saveAsFileChooser.showSaveDialog(sender.getParentPopup().getOwnerWindow());
        if (file != null)
            fileMenuOwner.saveAs(file);
    }

    private void updateRecentFilesMenu() {
        recentMenu.getItems().clear();
        List<MenuItem> menuItems = PreferencesManager
                .getInstance()
                .getPreferences()
                .getDefinitionEditorRecentFiles()
                .stream()
                .sorted(Comparator.comparing(Preferences.RecentFile::getDateTime).reversed())
                .limit(10)
                .map(recentFile -> {
                    MenuItem menuItem = new MenuItem(recentFile.getFile());
                    menuItem.setOnAction(event -> fileMenuOwner.onOpen(new File(recentFile.getFile())));
                    return menuItem;
                })
                .collect(Collectors.toList());
        recentMenu.getItems().addAll(menuItems);
    }

    @Subscribe
    public void recentFileAdded(RecentFileAddedEvent event) {
        updateRecentFilesMenu();
    }


    public interface FileMenuOwner {
        void onOpen(File file);

        void save();

        void saveAs(File file);

        boolean saveOpensSaveAs();
    }
}
