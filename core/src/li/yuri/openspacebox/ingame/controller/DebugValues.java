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

package li.yuri.openspacebox.ingame.controller;

import lombok.Getter;
import lombok.val;

import java.util.HashMap;


/**
 * Holds values which can be displayed for debugging purposes.
 */
public final class DebugValues {
    @Getter
    private static DebugValues instance = new DebugValues();

    private HashMap<String, String> values = new HashMap<>();

    private DebugValues() {

    }

    public void updateValue(String key, String value) {
        values.put(key, value);
    }

    public void removeValue(String key) {
        values.remove(key);
    }

    public String buildText() {
        StringBuilder builder = new StringBuilder();
        for (val entry : values.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }

}
