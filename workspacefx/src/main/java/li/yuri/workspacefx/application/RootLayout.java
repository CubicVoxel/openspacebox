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

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

public class RootLayout extends GridPane {

    private final MainLayout mainLayout;

    public RootLayout(ViewType... viewTypes) {

        setBackground(new Background(new BackgroundFill(Paint.valueOf("#f2a1b1"), null, null)));
        mainLayout = new MainLayout(viewTypes);
        add(mainLayout, 0, 0);

        setHgrow(mainLayout, Priority.ALWAYS);
        setVgrow(mainLayout, Priority.ALWAYS);
    }

    public void onStop() {
        mainLayout.onStop();
    }
}
