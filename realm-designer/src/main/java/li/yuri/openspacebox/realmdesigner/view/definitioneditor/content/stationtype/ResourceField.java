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

import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common.ItemIdField;
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.IntegerField;
import li.yuri.workspacefx.layout.TwoColumnedFieldLabelGrid;

import java.util.Collection;

public class ResourceField extends TwoColumnedFieldLabelGrid implements IsField<StationType.Resource> {
    private FieldBinder<StationType.Resource> binder;

    public ResourceField() {
        binder = new FieldBinder<>();

        ItemIdField itemIdField = new ItemIdField();
        binder.bind(itemIdField, StationType.Resource::setItem, StationType.Resource::getItem);

        IntegerField amountField = new IntegerField();
        binder.bind(amountField, StationType.Resource::setAmount, StationType.Resource::getAmount);

        addTwoFields("Item:", itemIdField, "Amount:", amountField, 0);
    }

    @Override
    public StationType.Resource getValue() {
        StationType.Resource resource = new StationType.Resource();
        binder.writeToBean(resource);
        return resource;
    }

    @Override
    public void setValue(StationType.Resource value) {
        binder.readFromBean(value);
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
