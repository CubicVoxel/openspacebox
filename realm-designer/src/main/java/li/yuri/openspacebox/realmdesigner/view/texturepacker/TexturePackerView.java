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

package li.yuri.openspacebox.realmdesigner.view.texturepacker;

import com.google.common.base.Strings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.realmdesigner.common.preferences.PreferencesManager;
import li.yuri.workspacefx.application.View;
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.field.AbstractFileOrDirectoryPicker;
import li.yuri.workspacefx.field.CheckBox;
import li.yuri.workspacefx.field.DirectoryPicker;
import li.yuri.workspacefx.field.TextField;
import li.yuri.workspacefx.layout.FieldLabelGrid;
import li.yuri.workspacefx.style.Defaults;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Collections;

public class TexturePackerView extends View {
    private static final double MAX_WIDTH = 500;

    private final FieldBinder<TexturePackerProperties> binder;
    private final TexturePackerProperties value;

    private VBox layout;
    private FieldLabelGrid fieldsLayout;
    private DirectoryPicker inputDirectoryPicker;
    private DirectoryPicker outputDirectoryPicker;
    private TextField packFileTextField;
    private CheckBox combineSubdirectoriesCheckBox;
    private li.yuri.openspacebox.realmdesigner.view.texturepacker.DimensionsField minSizeField;
    private li.yuri.openspacebox.realmdesigner.view.texturepacker.DimensionsField maxSizeField;
    private Button executeButton;

    public TexturePackerView() {
        binder = new FieldBinder<>();
        value = PreferencesManager.getInstance().getPreferences().getTexturePackerProperties();

        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(Defaults.getInstance().spacingDefault);
        setContent(layout);

        fieldsLayout = new FieldLabelGrid();
        fieldsLayout.setHgap(Defaults.getInstance().spacingDefault);
        fieldsLayout.setVgap(Defaults.getInstance().spacingDefault);
        fieldsLayout.setAlignment(Pos.CENTER);
        fieldsLayout.setPrefWidth(MAX_WIDTH);
        fieldsLayout.setMaxWidth(MAX_WIDTH);
        layout.getChildren().add(fieldsLayout);

        inputDirectoryPicker = new DirectoryPicker();
        binder.bind(
                inputDirectoryPicker,
                (bean, val) -> {
                    try {
                        if (val != null && !val.isEmpty() && val.get(0) != null)
                            bean.setInputDirectory(val.get(0).getCanonicalFile());
                        else
                            bean.setInputDirectory(null);
                    } catch (IOException e) {
                        bean.setInputDirectory(null);
                        throw new RuntimeException(e);
                    }
                },
                bean -> Collections.singletonList(bean.getInputDirectory())
        )
                .withValidator(
                        val -> inputDirectoryPicker.validate().equals(AbstractFileOrDirectoryPicker.ValidationResult.OK),
                        "An existing input directory has to be specified."
                );
        fieldsLayout.addField("Input:", inputDirectoryPicker, 0);

        outputDirectoryPicker = new DirectoryPicker();
        fieldsLayout.addField("Output:", outputDirectoryPicker, 1);
        binder
                .bind(
                        outputDirectoryPicker,
                        (bean, val) -> {
                            try {
                                if (val != null && !val.isEmpty() && val.get(0) != null)
                                    bean.setOutputDirectory(val.get(0).getCanonicalFile());
                                else
                                    bean.setOutputDirectory(null);
                            } catch (IOException e) {
                                bean.setOutputDirectory(null);
                                throw new RuntimeException(e);
                            }
                        },
                        bean -> Collections.singletonList(bean.getOutputDirectory())
                )
                .withValidator(
                        val -> outputDirectoryPicker.validate().equals(AbstractFileOrDirectoryPicker.ValidationResult
                                .OK),
                        "An existing output directory has to be specified."
                );

        packFileTextField = new TextField();
        packFileTextField.setPromptText("Enter a name for the pack-file...");
        binder
                .bind(packFileTextField, TexturePackerProperties::setPackFile, TexturePackerProperties::getPackFile)
                .withValidator(val -> !Strings.isNullOrEmpty(val), "The pack file needs to have a name.");
        fieldsLayout.addField("Pack file:", packFileTextField, 2);

        combineSubdirectoriesCheckBox = new CheckBox();
        binder.bind(
                combineSubdirectoriesCheckBox,
                TexturePackerProperties::setCombineSubdirectories,
                TexturePackerProperties::getCombineSubdirectories
        );
        fieldsLayout.addField("Combine subdirectories:", combineSubdirectoriesCheckBox, 3);

        minSizeField = new li.yuri.openspacebox.realmdesigner.view.texturepacker.DimensionsField();
        binder.bind(minSizeField, TexturePackerProperties::setMinSize, TexturePackerProperties::getMinSize)
                .withNotNullValidator("A minimum size has to be specified.");
        fieldsLayout.addField("Minimum size:", minSizeField, 4);

        maxSizeField = new DimensionsField();
        binder.bind(maxSizeField, TexturePackerProperties::setMaxSize, TexturePackerProperties::getMaxSize)
                .withNotNullValidator("A maximum size has to be specified.");
        fieldsLayout.addField("Maximum size:", maxSizeField, 5);

        executeButton = new Button("Pack Textures");
        executeButton.setOnAction(e -> packTextures());
        layout.getChildren().add(executeButton);

        binder.readFromBean(value);
    }

    @Override
    public void onLeave() {
        binder.writeToBean(value);
    }

    @SneakyThrows
    private void packTextures() {
        binder.writeToBean(value);
        if (binder.validate(value)) {
            PreferencesManager.getInstance().persistPreferences();

            li.yuri.openspacebox.realmdesigner.view.texturepacker.TexturePackerExecutor executor = new TexturePackerExecutor();
            executor.setInputDir(value.getInputDirectory().getCanonicalPath());
            executor.setOutputDir(value.getOutputDirectory().getCanonicalPath());
            executor.setPackFileName(value.getPackFile());
            executor.setCombineSubdirectories(value.getCombineSubdirectories());
            executor.setMinWidth(minSizeField.getValue().getX());
            executor.setMinHeight(minSizeField.getValue().getY());
            executor.setMaxWidth(maxSizeField.getValue().getX());
            executor.setMaxHeight(maxSizeField.getValue().getY());

            executor.execute();
        }
    }

}
