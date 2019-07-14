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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.itemtype;

import li.yuri.openspacebox.definition.type.ItemType;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.TypeEditor;
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.field.FloatField;
import li.yuri.workspacefx.field.IntegerField;
import li.yuri.workspacefx.field.TextField;

public class ItemTypeEditor extends TypeEditor<ItemType> {

    private FieldBinder<ItemType> binder;
    private TextField idField;
    private TextField displayNameField;
    private TextField descriptionField;
    private IntegerField sizeField;
    private FloatField priceField;

    public ItemTypeEditor(DeleteHandler<ItemType> deleteHandler) {
        super(deleteHandler);
    }

    @Override
    public void createFields() {
        binder = new FieldBinder<>();

        idField = new TextField();
        getFieldsContainer().addField("Identifier:", idField, 0);
        binder.bind(idField, ItemType::setId, ItemType::getId);

        displayNameField = new TextField();
        getFieldsContainer().addField("Name:", displayNameField, 1);
        binder.bind(displayNameField, ItemType::setDisplayName, ItemType::getDisplayName);

        descriptionField = new TextField();
        getFieldsContainer().addField("Description:", descriptionField, 2);
        binder.bind(descriptionField, ItemType::setDescription, ItemType::getDescription);

        sizeField = new IntegerField();
        getFieldsContainer().addField("Size:", sizeField, 3);
        binder.bind(sizeField, ItemType::setSize, ItemType::getSize);

        priceField = new FloatField();
        getFieldsContainer().addField("Price:", priceField, 4);
        binder.bind(priceField, ItemType::setPrice, ItemType::getPrice);
    }

    @Override
    public void fillFields(ItemType value) {
        binder.readFromBean(value);
    }

    @Override
    public ItemType fillValue(ItemType oldValue) {
        binder.writeToBean(oldValue);
        return oldValue;
    }
}
