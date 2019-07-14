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
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.TypeEditor;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common.SpriteField;
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.field.IntegerField;
import li.yuri.workspacefx.field.ListField;
import li.yuri.workspacefx.field.TextArea;
import li.yuri.workspacefx.field.TextField;
import li.yuri.workspacefx.style.Styles;

public class StationTypeEditor extends TypeEditor<StationType> {

    private FieldBinder<StationType> binder;
    private TextField idField;
    private TextField displayNameField;
    private TextArea descriptionField;
    private SpriteField spriteField;
    private li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype.StationSizeField sizeField;
    private IntegerField cargoSpaceField;
    private ListField<StationType.StorageAllocation, li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype.StorageAllocationField> storageAllocationsField;
    private ListField<StationType.Production, li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype.ProductionField> productionsField;

    public StationTypeEditor(DeleteHandler<StationType> deleteHandler) {
        super(deleteHandler);
    }

    @Override
    public void createFields() {
        binder = new FieldBinder<>();

        idField = new TextField();
        getFieldsContainer().addField("Identifier:", idField, 0);
        binder.bind(idField, StationType::setId, StationType::getId);

        displayNameField = new TextField();
        getFieldsContainer().addField("Name:", displayNameField, 1);
        binder.bind(displayNameField, StationType::setDisplayName, StationType::getDisplayName);

        descriptionField = new TextArea();
        getFieldsContainer().addField("Description:", descriptionField, 2);
        binder.bind(descriptionField, StationType::setDescription, StationType::getDescription);

        sizeField = new StationSizeField();
        getFieldsContainer().addField("Size:", sizeField, 3);
        binder.bind(sizeField, StationType::setSize, StationType::getSize);

        cargoSpaceField = new IntegerField();
        getFieldsContainer().addField("Cargo Space:", cargoSpaceField, 4);
        binder.bind(cargoSpaceField, StationType::setCargoSpace, StationType::getCargoSpace);

        storageAllocationsField = new ListField<>(
                StorageAllocationField::new,
                StationType.StorageAllocation::new
        );
        storageAllocationsField.getStyleClass().add(Styles.BORDERED);
        getFieldsContainer().addField("Storage Allocations:", storageAllocationsField, 5);
        binder.bind(storageAllocationsField, StationType::setStorageAllocations, StationType::getStorageAllocations);

        productionsField = new ListField<>(
                () -> {
                    li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype.ProductionField field = new ProductionField();
                    field.getStyleClass().add(Styles.BORDERED);
                    return field;
                },
                StationType.Production::new
        );
        productionsField.getStyleClass().add(Styles.BORDERED);
        getFieldsContainer().addField("Productions:", productionsField, 6);
        binder.bind(productionsField, StationType::setProductions, StationType::getProductions);

        spriteField = new SpriteField();
        getFieldsContainer().addField("Sprite:", spriteField, 7);
        binder.bind(spriteField, StationType::setSpriteAtlas, StationType::getSpriteAtlas);
    }

    @Override
    public void fillFields(StationType value) {
        binder.readFromBean(value);
    }

    @Override
    public StationType fillValue(StationType oldValue) {
        binder.writeToBean(oldValue);
        return oldValue;
    }
}
