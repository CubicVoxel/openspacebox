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
import li.yuri.workspacefx.data.FieldBinder;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.FloatField;
import li.yuri.workspacefx.layout.TwoColumnedFieldLabelGrid;
import li.yuri.workspacefx.style.Defaults;

import java.util.Collection;

/**
 * TODO: This fields is perfectly eligible for adding some nice real-time calculations.
 */
public class PhysicsField extends TwoColumnedFieldLabelGrid implements IsField<ShipType.Physics> {

    private FieldBinder<ShipType.Physics> binder;
    private FloatField maxSpeedField;
    private FloatField accelerationField;
    private FloatField decelerationField;
    private FloatField rotationSpeedField;

    public PhysicsField() {
        binder = new FieldBinder<>();
        setHgap(Defaults.getInstance().spacingDefault);
        setVgap(Defaults.getInstance().spacingDefault);

        maxSpeedField = new FloatField();
        binder.bind(
                maxSpeedField,
                (physics, value) -> physics.setMaxSpeed(value.floatValue()),
                physics -> Float.valueOf(physics.getMaxSpeed()).doubleValue()
        );

        rotationSpeedField = new FloatField();
        binder.bind(
                rotationSpeedField,
                (physics, value) -> physics.setRotationSpeed(value.floatValue()),
                physics -> Float.valueOf(physics.getRotationSpeed()).doubleValue()
        );

        accelerationField = new FloatField();
        binder.bind(
                accelerationField,
                (physics, value) -> physics.setAcceleration(value.floatValue()),
                physics -> Float.valueOf(physics.getAcceleration()).doubleValue()
        );

        decelerationField = new FloatField();
        binder.bind(
                decelerationField,
                (physics, value) -> physics.setDeceleration(value.floatValue()),
                physics -> Float.valueOf(physics.getDeceleration()).doubleValue()
        );

        addTwoFields("Maximum Speed:", maxSpeedField, "Rotation Speed:", rotationSpeedField, 0);
        addTwoFields("Acceleration:", accelerationField, "Deceleration:", decelerationField, 1);
    }

    @Override
    public ShipType.Physics getValue() {
        ShipType.Physics bean = new ShipType.Physics();
        binder.writeToBean(bean);
        return bean;
    }

    @Override
    public void setValue(ShipType.Physics value) {
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
