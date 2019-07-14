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

package li.yuri.workspacefx.application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

class Menu extends VBox {
    private static final String STYLE = "rd-menu";

    private Navigator navigator;

    public Menu(ViewContainer viewContainer, ViewType... viewTypes) {
        navigator = new Navigator(viewContainer);

        getStyleClass().add(STYLE);

        ObservableList<Node> children = getChildren();
        for (ViewType viewType : viewTypes) {
            MenuButton menuButton = new MenuButton(viewType);
            menuButton.addSelectedListener(navigator::navigateTo);
            navigator.addViewChangeListener(menuButton::setSelectedForViewType);
            children.add(menuButton);
        }
    }


}
