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

package li.yuri.openspacebox.realmdesigner.view.texturepacker;

import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.AbstractBiField;
import li.yuri.workspacefx.field.IntegerField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Two fields for editing one {@link Dimensions}.
 */
public class DimensionsField extends AbstractBiField<Integer, IntegerField> implements IsField<DimensionsField.Dimensions> {


    public DimensionsField() {
        super(new IntegerField(), "*", new IntegerField());
    }

    public Dimensions getValue() {
        if (firstField.getIntValue() == null || secondField.getIntValue() == null)
            return null;
        else
            return new Dimensions(firstField.getIntValue(), secondField.getIntValue());
    }

    public void setValue(Dimensions dimensions) {
        firstField.setIntValue(dimensions.getX());
        secondField.setIntValue(dimensions.getY());
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO show message
        getStyleClass().add("dimensions-field-invalid");
    }

    @Override
    public void resetInvalid() {
        getStyleClass().removeAll("dimensions-field-invalid");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Dimensions {
        private int x;
        private int y;
    }
}
