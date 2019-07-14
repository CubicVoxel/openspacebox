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
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Basically a {@link TextField} and a "..."-{@link Button} for choosing a file. A {@link FileChooser} is shown when the
 * "..."-{@link Button} is clicked.
 * <p>
 * The action the {@link FileChooser} executes on clicking "..." can be specified via {@link
 * #setFileChooserAction(FileChooserAction)} ({@link FileChooser#showOpenDialog(Window)}, {@link
 * FileChooser#showOpenMultipleDialog(Window)} or {@link FileChooser#showSaveDialog(Window)}).
 * <p>
 * The {@link FileChooser} can be accessed via {@link #getFileChooser()}.
 * <p>
 * Also look at the documentation of {@link AbstractFileOrDirectoryPicker}.
 */
public class FilePicker extends AbstractFileOrDirectoryPicker {

    private FileChooserAction fileChooserAction;
    private FileChooser fileChooser;

    public FilePicker() {
        setTextFieldPromptText("Select or enter files...");
        setTextFieldTooltip("Separate files via '" + FILE_SEPARATOR + "'." +
                " Write all other '" + FILE_SEPARATOR + "' as '\\" + FILE_SEPARATOR + "' and '\\' as '\\\\'.");

        fileChooser = new FileChooser();
    }

    public FilePicker(FileChooserAction fileChooserAction) {
        this();
        this.fileChooserAction = fileChooserAction;
    }


    @Override
    public List<File> openChooser() {
        return fileChooserAction.execute(fileChooser, getScene().getWindow());
    }

    @Override
    protected boolean allowMultiple() {
        return fileChooserAction.equals(FileChooserAction.OPEN_MULTIPLE);
    }

    @Override
    protected boolean filesMustExist() {
        return !fileChooserAction.equals(FileChooserAction.SAVE);
    }

    @Override
    protected boolean allowFiles() {
        return true;
    }

    @Override
    protected boolean allowDirectories() {
        return false;
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooserAction(FileChooserAction fileChooserAction) {
        this.fileChooserAction = fileChooserAction;
    }

    public enum FileChooserAction {
        OPEN((fileChooser1, ownerWindow) ->
                Collections.singletonList(fileChooser1.showOpenDialog(ownerWindow))),
        OPEN_MULTIPLE(FileChooser::showOpenMultipleDialog),
        SAVE((fileChooser1, ownerWindow) ->
                Collections.singletonList(fileChooser1.showSaveDialog(ownerWindow)));

        private final FileChooserActionExecutor actionExecutor;

        FileChooserAction(FileChooserActionExecutor actionExecutor) {
            this.actionExecutor = actionExecutor;
        }

        public List<File> execute(FileChooser fileChooser, Window window) {
            return actionExecutor.executeFilePickerAction(fileChooser, window);
        }

        private interface FileChooserActionExecutor {
            List<File> executeFilePickerAction(FileChooser fileChooser, Window window);
        }
    }
}
