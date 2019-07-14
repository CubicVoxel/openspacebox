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

package li.yuri.workspacefx.field;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Basically a {@link TextField} and a "..."-{@link Button} for choosing a file. A {@link DirectoryChooser} is shown
 * when the "..."-{@link Button} is clicked.
 * <p>
 * The {@link DirectoryChooser} can be accessed via {@link #getDirectoryChooser()}.
 * <p>
 * Also look at the documentation of {@link li.yuri.workspacefx.field.AbstractFileOrDirectoryPicker}.
 */
public class DirectoryPicker extends AbstractFileOrDirectoryPicker {

    private DirectoryChooser directoryChooser;

    public DirectoryPicker() {
        setTextFieldPromptText("Select or enter directory...");

        directoryChooser = new DirectoryChooser();
    }

    @Override
    protected List<File> openChooser() {
        return Collections.singletonList(directoryChooser.showDialog(getScene().getWindow()));
    }

    @Override
    protected boolean allowMultiple() {
        return false;
    }

    @Override
    protected boolean filesMustExist() {
        return true;
    }

    @Override
    protected boolean allowFiles() {
        return false;
    }

    @Override
    protected boolean allowDirectories() {
        return true;
    }

    public DirectoryChooser getDirectoryChooser() {
        return directoryChooser;
    }
}
