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

import li.yuri.workspacefx.data.IsField;

import java.util.Collection;

public class CheckBox extends javafx.scene.control.CheckBox implements IsField<Boolean> {
    public CheckBox() {
    }

    public CheckBox(String text) {
        super(text);
    }

    @Override
    public Boolean getValue() {
        return isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        setSelected(value);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO find better way to show validation
        setTooltip(new FailedValidationTooltip(message));
        getStyleClass().add("check-box-invalid");
    }

    @Override
    public void resetInvalid() {
        setTooltip(null);
        getStyleClass().removeAll("check-box-invalid");
    }
}
