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
import javafx.scene.layout.GridPane;

/**
 * Like the {@link FieldLabelGrid} but with two field columns for larger areas or smaller fields.
 */
public class TwoColumnedFieldLabelGrid extends FieldLabelGrid {
    public static final int SECOND_LABEL_COLUMN = 2;
    public static final int SECOND_FIELD_COLUMN = 3;

    public TwoColumnedFieldLabelGrid() {
        getColumnConstraints().add(createLabelColumnConstraints());
        getColumnConstraints().add(createFieldColumnConstraints());
    }

    public void addTwoFields(String firstText, Node firstField, String secondText, Node secondField, int rowIndex) {
        addField(firstText, firstField, rowIndex);

        FieldLabel fieldLabel = new FieldLabel(secondText);
        add(fieldLabel, SECOND_LABEL_COLUMN, rowIndex);
        GridPane.setValignment(fieldLabel, VPos.CENTER);

        add(secondField, SECOND_FIELD_COLUMN, rowIndex);
    }
}
