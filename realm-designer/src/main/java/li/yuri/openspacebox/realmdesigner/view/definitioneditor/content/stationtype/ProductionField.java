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
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.FloatField;
import li.yuri.workspacefx.field.ListField;
import li.yuri.workspacefx.layout.FieldLabelGrid;
import li.yuri.workspacefx.style.Styles;

import java.util.Collection;

/**
 * TODO: Add some live-statistics for inputs and outputs
 */
public class ProductionField extends FieldLabelGrid implements IsField<StationType.Production> {

    private FieldBinder<StationType.Production> binder;

    private FloatField durationField;
    private ResourcesField inputsField;
    private ResourcesField outputsField;

    public ProductionField() {
        binder = new FieldBinder<>();

        durationField = new FloatField();
        binder.bind(
                durationField,
                (production, value) -> production.setDuration(value.floatValue()),
                production -> Float.valueOf(production.getDuration()).doubleValue()
        );
        addField("Duration:", durationField, 0);

        inputsField = new ResourcesField();
        inputsField.getStyleClass().add(Styles.BORDERED);
        binder.bind(inputsField, StationType.Production::setInputs, StationType.Production::getInputs);
        addField("Inputs:", inputsField, 1);

        outputsField = new ResourcesField();
        outputsField.getStyleClass().add(Styles.BORDERED);
        binder.bind(outputsField, StationType.Production::setOutputs, StationType.Production::getOutputs);
        addField("Outputs:", outputsField, 2);
    }

    @Override
    public StationType.Production getValue() {
        StationType.Production production = new StationType.Production();
        binder.writeToBean(production);
        return production;
    }

    @Override
    public void setValue(StationType.Production value) {
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

    private static class ResourcesField extends ListField<StationType.Resource, ResourceField> {

        public ResourcesField() {
            super(ResourceField::new, StationType.Resource::new);
        }
    }
}
