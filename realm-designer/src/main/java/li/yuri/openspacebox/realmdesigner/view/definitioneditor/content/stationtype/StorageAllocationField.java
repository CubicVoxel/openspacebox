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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype;

import javafx.geometry.Pos;
import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common.ItemIdField;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.IntegerField;
import li.yuri.workspacefx.layout.TwoColumnedFieldLabelGrid;

import java.util.Collection;

/**
 * Field for {@link StationType.StorageAllocation}s.
 */
public class StorageAllocationField extends TwoColumnedFieldLabelGrid implements IsField<StationType.StorageAllocation> {

    private ItemIdField itemField;
    private IntegerField sizeField;

    public StorageAllocationField() {
        setAlignment(Pos.CENTER);

        itemField = new ItemIdField();
        sizeField = new IntegerField();

        addTwoFields("Item:", itemField, "Size:", sizeField, 0);
    }

    @Override
    public StationType.StorageAllocation getValue() {
        StationType.StorageAllocation value = new StationType.StorageAllocation();
        value.setItem(itemField.getValue());
        value.setSize(sizeField.getValue());
        return value;
    }

    @Override
    public void setValue(StationType.StorageAllocation value) {
        itemField.setValue(value.getItem());
        sizeField.setValue(value.getSize());
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO
    }

    @Override
    public void resetInvalid() {
        // TODO
    }
}
