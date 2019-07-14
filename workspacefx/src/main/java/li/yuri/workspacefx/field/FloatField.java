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

package li.yuri.workspacefx.field;

import javafx.scene.control.TextField;
import li.yuri.workspacefx.data.IsField;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * A {@link TextField} which only allows {@link Double}s.
 */
public class FloatField extends TextField implements IsField<Double> {

    private Double floatValue = null;

    public FloatField() {
        textProperty().addListener(((observable, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue)) {
                floatValue = null;
            } else {
                try {
                    floatValue = Double.parseDouble(newValue);
                } catch (NumberFormatException e) {
                    setText(oldValue);
                    floatValue = Double.parseDouble(oldValue);
                }
            }
        }));
    }

    public Double getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(Double value) {
        this.floatValue = value;
        if (value == null)
            setText("");
        else
            setText(String.valueOf(value));
    }

    @Override
    public Double getValue() {
        return getFloatValue();
    }

    @Override
    public void setValue(Double value) {
        setFloatValue(value);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        setTooltip(new FailedValidationTooltip(message));
    }

    @Override
    public void resetInvalid() {
        setTooltip(null);
    }

}
