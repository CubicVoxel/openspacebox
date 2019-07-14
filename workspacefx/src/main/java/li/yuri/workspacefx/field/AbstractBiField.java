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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.style.Defaults;

/**
 * Field which contains two fields of the same type.
 */
public abstract class AbstractBiField<T, FIELD extends Region & IsField<T>> extends HBox {
    protected FIELD firstField;
    protected FIELD secondField;

    public AbstractBiField(FIELD firstField, String separator, FIELD secondField) {
        setSpacing(Defaults.getInstance().spacingDefault);

        this.firstField = firstField;
        firstField.setPrefWidth(60);
        HBox.setHgrow(firstField, Priority.ALWAYS);
        getChildren().add(firstField);

        Label separation = new Label(separator);
        separation.setAlignment(Pos.BOTTOM_CENTER);
        getChildren().add(separation);

        this.secondField = secondField;
        this.secondField.setPrefWidth(60);
        HBox.setHgrow(this.secondField, Priority.ALWAYS);
        getChildren().add(this.secondField);
    }
}
