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

package li.yuri.workspacefx.layout;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import li.yuri.workspacefx.style.Defaults;

/**
 * A {@link GridPane} with the second column taking as much space as it wants. Typical use is to put fields in the
 * second column and their labels in the first one.
 */
public class FieldLabelGrid extends GridPane {
    public static final int LABEL_COLUMN = 0;
    public static final int FIELD_COLUMN = 1;

    public FieldLabelGrid() {
        setVgap(li.yuri.workspacefx.style.Defaults.getInstance().spacingDefault);
        setHgap(Defaults.getInstance().spacing2x);

        getColumnConstraints().add(createLabelColumnConstraints());
        getColumnConstraints().add(createFieldColumnConstraints());
    }

    protected ColumnConstraints createLabelColumnConstraints() {
        return new ColumnConstraints();
    }

    protected ColumnConstraints createFieldColumnConstraints() {
        ColumnConstraints fieldColumn = new ColumnConstraints();
        fieldColumn.setHgrow(Priority.ALWAYS);
        return fieldColumn;
    }

    public void addField(String text, Node field, int rowIndex) {
        FieldLabel fieldLabel = new FieldLabel(text);
        add(fieldLabel, LABEL_COLUMN, rowIndex);
        GridPane.setValignment(fieldLabel, VPos.CENTER);

        add(field, FIELD_COLUMN, rowIndex);
    }

    protected Label createFieldLabel(String text) {
        return new Label(text);
    }

    protected class FieldLabel extends Label {
        public FieldLabel(String text) {
            super(text);
        }
    }


}
