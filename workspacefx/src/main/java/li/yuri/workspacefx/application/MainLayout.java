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

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import li.yuri.workspacefx.style.Styles;


class MainLayout extends HBox implements li.yuri.workspacefx.application.ViewContainer {

    private li.yuri.workspacefx.application.Menu menu;
    private Pane viewContainer;
    private li.yuri.workspacefx.application.View view;

    public MainLayout(ViewType... viewTypes) {
        getStyleClass().add(Styles.DARK_BACKGROUND);

        menu = new li.yuri.workspacefx.application.Menu(this, viewTypes);
        getChildren().add(menu);

        viewContainer = new AnchorPane();
        HBox.setHgrow(viewContainer, Priority.ALWAYS);
        getChildren().add(viewContainer);

    }

    public void onStop() {
        if (view != null) view.onLeave();
    }

    @Override
    public void setView(View view) {
        if (this.view != null) this.view.onLeave();
        this.view = view;
        viewContainer.getChildren().setAll(this.view);
    }
}
