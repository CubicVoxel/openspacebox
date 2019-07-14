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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.style.Defaults;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Superclass for {@link DirectoryPicker} and {@link FilePicker}. Additionally to building the layout, it handles stuff
 * like String-conversion.
 * <p>
 * Multiple files must be separated with {@link #FILE_SEPARATOR}. Therefore, all {@link #FILE_SEPARATOR} must be escaped
 * with '\' and all '\' must be escaped with '\\'.
 */
public abstract class AbstractFileOrDirectoryPicker extends HBox implements IsField<List<File>> {
    /**
     * The String files in the {@link #textField} get separated with.
     */
    protected static final String FILE_SEPARATOR = ";";

    private List<File> pickedFiles = Collections.emptyList();

    private TextField textField;
    private Button button;

    public AbstractFileOrDirectoryPicker() {
        setSpacing(Defaults.getInstance().spacingDefault);

        textField = new TextField();
        textField.textProperty().addListener(((observable, oldValue, newValue) -> setPickedFilesFromTextField(newValue)));
        HBox.setHgrow(textField, Priority.ALWAYS);
        getChildren().add(textField);

        button = new Button("...");
        button.setOnAction(x -> setPickedFiles(openChooser(), true));
        getChildren().add(button);

    }

    private void setPickedFiles(List<File> files, boolean updateTextField) {
        if (files == null) files = new ArrayList<>();
        this.pickedFiles = files;

        if (updateTextField) {
            textField.setText(files.stream().map(f -> {
                // *sigh*
                try {
                    if (f != null)
                        return escapeFileName(f.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }).collect(Collectors.joining(FILE_SEPARATOR)));

        }
    }

    private void setPickedFilesFromTextField(String text) {
        ArrayList<File> pickedFiles = new ArrayList<>();
        String[] split = textField.getText().split("(?<![\\\\\\\\])[;]");

        for (String s : split) {
            String deescasped = deescapeFileName(s);
            pickedFiles.add(new File(deescasped));
        }

        setPickedFiles(pickedFiles, false);
    }


    public ValidationResult validate() {

        if (getPickedFiles().isEmpty())
            return ValidationResult.EMPTY;
        else if (!allowMultiple() && getPickedFiles().size() > 1)
            return ValidationResult.MULTIPLE_FILES_ENTERED;
        else if (!allowDirectories() && filesContainDirectories())
            return ValidationResult.FILES_CONTAIN_DIRECTORIES;
        else if (!allowFiles() && filesContainFiles())
            return ValidationResult.FILES_CONTAIN_FILES;
        else if (filesMustExist() && !doFilesExist())
            return ValidationResult.FILES_NONEXISTENT;
        else
            return ValidationResult.OK;
    }

    private boolean doFilesExist() {
        return getPickedFiles().stream().allMatch(File::exists);
    }

    private boolean filesContainDirectories() {
        return getPickedFiles().stream().anyMatch(File::isDirectory);
    }

    private boolean filesContainFiles() {
        return getPickedFiles().stream().anyMatch(File::isFile);
    }

    public void setTextFieldPromptText(String text) {
        textField.setPromptText(text);
    }

    public void setTextFieldTooltip(String text) {
        if (text != null)
            textField.setTooltip(new Tooltip(text));
        else
            textField.setTooltip(null);
    }

    public List<File> getPickedFiles() {
        return pickedFiles;
    }

    protected abstract List<File> openChooser();

    protected abstract boolean allowMultiple();

    protected abstract boolean filesMustExist();

    protected abstract boolean allowFiles();

    protected abstract boolean allowDirectories();

    @Override
    public List<File> getValue() {
        return getPickedFiles();
    }

    @Override
    public void setValue(List<File> value) {
        setPickedFiles(value, true);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        textField.showInvalid(message);
    }

    @Override
    public void resetInvalid() {
        textField.resetInvalid();
    }

    /**
     * Replaces every occurrence of {@link #FILE_SEPARATOR} with "\" + {@link #FILE_SEPARATOR}.
     */
    private static String escapeFileName(String fileName) {
        return fileName
                .replace("\\", "\\\\")
                .replace(FILE_SEPARATOR, "\\" + FILE_SEPARATOR);
    }

    /**
     * Replaces every occurrence of "\" + {@link #FILE_SEPARATOR} with {@link #FILE_SEPARATOR}.
     */
    private static String deescapeFileName(String escapedFileName) {
        return escapedFileName
                .replace("\\" + FILE_SEPARATOR, FILE_SEPARATOR)
                .replace("\\\\", "\\");
    }

    public enum ValidationResult {
        OK,
        /**
         * One or more of the entered files are directories.
         */
        FILES_CONTAIN_DIRECTORIES,
        /**
         * One or more of the entered directories are files.
         */
        FILES_CONTAIN_FILES,
        /**
         * No file was entered.
         */
        EMPTY,
        /**
         * One or more of the entered files does not exist.
         */
        FILES_NONEXISTENT,
        /**
         * The user entered multiple files although only a single file is allowed.
         */
        MULTIPLE_FILES_ENTERED
    }
}
