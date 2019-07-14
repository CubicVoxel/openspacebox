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

/**
 * {@link IsField}-Implementation for {@link javafx.scene.control.TextArea}.
 */
public class TextArea extends javafx.scene.control.TextArea implements IsField<String> {
    public TextArea() {
    }

    public TextArea(String text) {
        super(text);
    }

    @Override
    public String getValue() {
        return getText();
    }

    @Override
    public void setValue(String value) {
        setText(value);
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
