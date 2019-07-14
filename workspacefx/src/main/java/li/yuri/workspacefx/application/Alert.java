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

import javafx.scene.control.ButtonType;
import li.yuri.workspacefx.style.StyleSheets;

/**
 * {@link javafx.scene.control.Alert} with all {@link StyleSheets} and the {@link li.yuri.workspacefx.application.WorkspaceFxApplication#getStylesheets()}
 * (if you use the {@link li.yuri.workspacefx.application.WorkspaceFxApplication} pre-added.
 */
public class Alert extends javafx.scene.control.Alert {
    public Alert(AlertType alertType) {
        super(alertType);
        getDialogPane().getStylesheets().addAll(li.yuri.workspacefx.application.WorkspaceFxApplication.getAllStylesheets());
    }

    public Alert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        getDialogPane().getStylesheets().addAll(WorkspaceFxApplication.getAllStylesheets());
    }
}
