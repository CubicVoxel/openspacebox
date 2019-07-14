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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.shiptype;

import li.yuri.openspacebox.definition.type.ShipType;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.TypeEditor;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common.SpriteField;
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.field.IntegerField;
import li.yuri.workspacefx.field.TextField;

public class ShipTypeEditor extends TypeEditor<ShipType> {

    private FieldBinder<ShipType> binder;

    private TextField idField;
    private TextField displayNameField;
    private IntegerField cargoSpaceField;
    private PhysicsField physicsField;
    private SpriteField spriteField;

    public ShipTypeEditor(DeleteHandler<ShipType> deleteHandler) {
        super(deleteHandler);
    }

    @Override
    public void createFields() {
        binder = new FieldBinder<>();

        idField = new TextField();
        getFieldsContainer().addField("Identifier:", idField, 0);
        binder.bind(idField, ShipType::setId, ShipType::getId);

        displayNameField = new TextField();
        getFieldsContainer().addField("Name:", displayNameField, 1);
        binder.bind(displayNameField, ShipType::setDisplayName, ShipType::getDisplayName);

        cargoSpaceField = new IntegerField();
        getFieldsContainer().addField("Cargo Space:", cargoSpaceField, 2);
        binder.bind(cargoSpaceField, ShipType::setCargoSpace, ShipType::getCargoSpace);

        physicsField = new PhysicsField();
        getFieldsContainer().addField("Physics:", physicsField, 3);
        binder.bind(physicsField, ShipType::setPhysics, ShipType::getPhysics);

        spriteField = new SpriteField();
        getFieldsContainer().addField("Sprite:", spriteField, 4);
        binder.bind(spriteField, ShipType::setSpriteAtlas, ShipType::getSpriteAtlas);
    }

    @Override
    public void fillFields(ShipType value) {
        binder.readFromBean(value);
    }

    @Override
    public ShipType fillValue(ShipType oldValue) {
        binder.writeToBean(oldValue);
        return oldValue;
    }
}
