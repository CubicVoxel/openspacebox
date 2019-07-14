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

import javafx.scene.control.MenuBar;

public class DefinitionEditorMenuBar extends MenuBar {

    private DefinitionEditorMenuOwner owner;

    private FileMenu fileMenu;

    public DefinitionEditorMenuBar() {
        fileMenu = new FileMenu();
        getMenus().add(fileMenu);
    }

    public void setOwner(DefinitionEditorMenuOwner owner) {
        this.owner = owner;
        fileMenu.setFileMenuOwner(owner);
    }

    public interface DefinitionEditorMenuOwner extends FileMenu.FileMenuOwner {

    }


}