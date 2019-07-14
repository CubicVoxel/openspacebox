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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor;

import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.DefinitionEditorContent;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.menu.DefinitionEditorMenuBar;
import li.yuri.workspacefx.application.View;
import li.yuri.workspacefx.style.Defaults;

public class DefinitionEditorView extends View {
    public DefinitionEditorView() {
        VBox layout = new VBox();
        setContentWithoutMargin(layout);

        DefinitionEditorMenuBar menu = new DefinitionEditorMenuBar();
        layout.getChildren().add(menu);
        VBox.setVgrow(menu, Priority.NEVER);

        DefinitionEditorContent definitionEditorContent = new DefinitionEditorContent();
        menu.setOwner(definitionEditorContent);
        layout.getChildren().add(definitionEditorContent);
        VBox.setVgrow(definitionEditorContent, Priority.ALWAYS);
        VBox.setMargin(definitionEditorContent, new Insets(Defaults.getInstance().spacingDefault));
    }

    @Override
    public void onLeave() {
        // Nothing
    }


}
