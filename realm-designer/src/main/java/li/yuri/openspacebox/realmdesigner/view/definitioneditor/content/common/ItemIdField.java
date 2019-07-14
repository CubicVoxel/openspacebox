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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common;

import li.yuri.openspacebox.definition.type.ItemType;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.field.TextField;

/**
 * Field for getting the id of an {@link ItemType}.
 * <p>
 * As of now, it is nothing more than a {@link TextField} but that is likely to change sometime.
 */
public class ItemIdField extends TextField implements IsField<String> {
}
