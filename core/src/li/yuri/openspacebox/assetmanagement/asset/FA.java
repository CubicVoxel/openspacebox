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

package li.yuri.openspacebox.assetmanagement.asset;

import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import li.yuri.openspacebox.util.CharJoiner;

import java.util.Collection;

/**
 * FontAwesome characters
 */
public enum FA {

    BARS('\uF0C9'),
    BOOK('\uf02d'),
    BUG('\uF188'),
    COMMENT('\uF075'),
    LEVEL_DOWN_ALT('\uF3BE'),
    LEVEL_UP_ALT('\uF3BF'),
    LOCATION_ARROW('\uF124'),
    SHOPPING_CART('\uF07A'),
    TIMES_CIRCLE('\uF057');

    public static final Collection<Character> ALL = J8Arrays.stream(FA.values())
            .map(FA::getChar)
            .collect(Collectors.toList());
    public static final String ALL_JOINED = StreamSupport.stream(ALL).collect(new CharJoiner());
    private final char value;

    FA(char value) {
        this.value = value;
    }

    public char getChar() {
        return value;
    }

    public String asString() {
        return String.valueOf(value);
    }
}
