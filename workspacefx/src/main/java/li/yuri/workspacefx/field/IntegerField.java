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
 * A {@link TextField} which only allows integers.
 */
public class IntegerField extends TextField implements IsField<Integer> {

    private Integer intValue = null;

    public IntegerField() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue)) {
                intValue = null;
            } else {
                try {
                    intValue = Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    setText(oldValue);
                    intValue = Integer.parseInt(oldValue);
                }
            }
        });
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer value) {
        this.intValue = value;
        if (value == null)
            setText("");
        else
            setText(String.valueOf(value));
    }

    @Override
    public Integer getValue() {
        return getIntValue();
    }

    @Override
    public void setValue(Integer value) {
        setIntValue(value);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO find better way to show validation
        setTooltip(new FailedValidationTooltip(message));
        getStyleClass().add("text-field-invalid");
    }

    @Override
    public void resetInvalid() {
        setTooltip(null);
        getStyleClass().removeAll("text-field-invalid");
    }
}
