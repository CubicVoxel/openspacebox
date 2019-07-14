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

import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;
import li.yuri.workspacefx.style.StyleSheets;

/**
 * Basically a {@link javafx.scene.Scene} with all {@link StyleSheets} and the {@link
 * li.yuri.workspacefx.application.WorkspaceFxApplication#getStylesheets()} (if you use the {@link
 * li.yuri.workspacefx.application.WorkspaceFxApplication}) pre-added.
 */
public class Scene extends javafx.scene.Scene {

    public Scene(Parent root) {
        this(root, -1, -1);
    }

    public Scene(Parent root, double width, double height) {
        this(root, width, height, false, SceneAntialiasing.DISABLED);
    }

    public Scene(Parent root, Paint fill) {
        this(root, -1, -1, fill);
    }

    public Scene(Parent root, double width, double height, Paint fill) {
        this(root, width, height, false);
        setFill(fill);
    }

    public Scene(Parent root, double width, double height, boolean depthBuffer) {
        this(root, width, height, false, SceneAntialiasing.DISABLED);
    }

    public Scene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing) {
        super(root, width, height, depthBuffer, antiAliasing);
        getStylesheets().addAll(li.yuri.workspacefx.application.WorkspaceFxApplication.getAllStylesheets());
        WorkspaceFxApplication.getInstance().ifPresent(wfx -> getStylesheets().addAll(wfx.getStylesheets()));
    }


}
