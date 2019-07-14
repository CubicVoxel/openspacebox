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
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.AbstractBiField;
import li.yuri.workspacefx.field.FloatField;

import java.util.Collection;
import java.util.Optional;

public class StationSizeField extends AbstractBiField<Double, FloatField> implements IsField<StationType.Size> {
    public StationSizeField() {
        super(new FloatField(), "*", new FloatField());
    }

    @Override
    public StationType.Size getValue() {
        return new StationType.Size(firstField.getValue(), secondField.getValue());
    }

    @Override
    public void setValue(StationType.Size value) {
        Optional<StationType.Size> optVal = Optional.ofNullable(value);
        firstField.setValue(optVal.map(StationType.Size::getWidth).orElse(0.0));
        secondField.setValue(optVal.map(StationType.Size::getHeight).orElse(0.0));
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
