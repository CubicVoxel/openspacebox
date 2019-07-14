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

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.style.Defaults;
import li.yuri.workspacefx.style.Styles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ListField<T, FIELD extends Region & IsField<T>> extends VBox implements IsField<List<T>> {

    private final List<FIELD> fields = new ArrayList<>();
    private Supplier<FIELD> fieldSupplier;
    private Supplier<T> newValueSupplier;

    public ListField(Supplier<FIELD> fieldSupplier, Supplier<T> newValueSupplier) {
        this.fieldSupplier = fieldSupplier;
        this.newValueSupplier = newValueSupplier;

        setSpacing(li.yuri.workspacefx.style.Defaults.getInstance().spacing2x);
        setAlignment(Pos.TOP_RIGHT);

        Button addButton = new Button("Add");
        addButton.getStyleClass().add(Styles.SMALL_BUTTON);
        addButton.setOnAction(event -> this.addField());
        VBox.setVgrow(addButton, Priority.NEVER);
        getChildren().add(addButton);
    }

    public void addField() {
        appendField(newValueSupplier.get());
    }

    @Override
    public List<T> getValue() {
        // I get a BootstrapMethodError when i try to create a stream of fields. Nice!
        ArrayList<T> values = new ArrayList<>();
        for (FIELD field : fields) {
            values.add(field.getValue());
        }
        return values;
    }

    @Override
    public void setValue(List<T> value) {
        getChildren().removeAll(fields);
        if (Objects.nonNull(value))
            value.forEach(this::appendField);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO
    }

    @Override
    public void resetInvalid() {
        // TODO
    }

    private void appendField(T value) {
        HBox fieldAndDeleteLayout = new HBox();
        fieldAndDeleteLayout.setSpacing(Defaults.getInstance().spacingDefault);

        FIELD field = fieldSupplier.get();
        field.setValue(value);
        fields.add(field);
        HBox.setHgrow(field, Priority.ALWAYS);
        fieldAndDeleteLayout.getChildren().add(field);

        Button deleteButton = new Button("-");
        deleteButton.getStyleClass().add(Styles.SMALL_BUTTON);
        deleteButton.setOnAction(event -> {
            fields.remove(field);
            getChildren().remove(fieldAndDeleteLayout);
        });
        HBox.setHgrow(deleteButton, Priority.NEVER);
        fieldAndDeleteLayout.getChildren().add(deleteButton);

        getChildren().add(fieldAndDeleteLayout);

    }
}
