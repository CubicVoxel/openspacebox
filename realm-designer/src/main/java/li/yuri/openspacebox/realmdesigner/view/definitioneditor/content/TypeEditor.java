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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.definition.type.Type;
import li.yuri.workspacefx.layout.FieldLabelGrid;
import li.yuri.workspacefx.style.Defaults;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class TypeEditor<T extends Type> extends VBox {

    private T currentValue;

    private Button deleteButton;
    @Getter(AccessLevel.PROTECTED) private FieldLabelGrid fieldsContainer;
    @Getter(AccessLevel.PROTECTED) private HBox buttonsContainer;

    public TypeEditor(DeleteHandler<T> deleteHandler) {
        buttonsContainer = new HBox();
        buttonsContainer.setAlignment(Pos.TOP_RIGHT);
        getChildren().add(buttonsContainer);
        setMargin(buttonsContainer, new Insets(Defaults.getInstance().spacingDefault));

        deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setOnAction(event -> deleteHandler.delete(currentValue));
        buttonsContainer.getChildren().add(deleteButton);
        deleteButton.setAlignment(Pos.TOP_RIGHT);

        fieldsContainer = new FieldLabelGrid();
        fieldsContainer.setHgap(Defaults.getInstance().spacingDefault);
        fieldsContainer.setVgap(Defaults.getInstance().spacingDefault);
        getChildren().add(fieldsContainer);
        setMargin(fieldsContainer, new Insets(Defaults.getInstance().spacingDefault));

        createFields();
    }

    public T getValue() {
        return fillValue(currentValue);
    }

    public void setValue(T type) {
        this.currentValue = type;
        fillFields(type);
    }

    public abstract void createFields();

    public abstract void fillFields(T value);

    /**
     * Sets all the properties in the oldValue to the user's input and returns it.
     */
    public abstract T fillValue(T oldValue);

    public interface DeleteHandler<T extends Type> {
        void delete(T item);
    }
}
