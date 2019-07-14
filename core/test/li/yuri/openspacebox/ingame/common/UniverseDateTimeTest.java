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

package li.yuri.openspacebox.ingame.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UniverseDateTimeTest {

    @Test
    public void testZeroSeconds() {
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(Long.MIN_VALUE);

        long seconds = universeDateTime.getSecondsTotal();

        assertEquals(0, seconds);
    }

    @Test
    public void testXSeconds() {
        final long x = Long.MAX_VALUE;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(Long.MIN_VALUE + x);

        long seconds = universeDateTime.getSecondsTotal();

        assertEquals(x, seconds);
    }

    @Test
    public void testMaxSeconds() {
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(Long.MIN_VALUE + Long.MAX_VALUE + Long.MAX_VALUE);

        long seconds = universeDateTime.getSecondsTotal();

        // seconds will be overflowed here, nothing we can do about it without unsigned vars.
        //noinspection NumericOverflow 
        assertEquals(Long.MAX_VALUE + Long.MAX_VALUE, seconds);
    }

    @Test
    public void testGetM() {
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(Long.MIN_VALUE + 8);

        long mValue = universeDateTime.getMTotalValue();

        assertEquals(4, mValue);
    }

    @Test
    public void testGetSRelativeValue() {
        long seconds = 1;
        long nineMInSeconds = 9 * 2;
        long threeHInSeconds = 3 * 10 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(
                Long.MIN_VALUE + seconds + nineMInSeconds + threeHInSeconds
        );

        long sRelativeValue = universeDateTime.getSRelativeValue();

        assertEquals(1, sRelativeValue);
    }

    @Test
    public void testGetSRelativeValueFullM() {
        long seconds = 4;
        long nineMInSeconds = 9 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(
                Long.MIN_VALUE + seconds + nineMInSeconds
        );

        long sRelativeValue = universeDateTime.getSRelativeValue();

        assertEquals(0, sRelativeValue);
    }

    @Test
    public void testGetMRelativeValue() {
        long threeHInSeconds = 3 * 10 * 2;
        long nineMInSeconds = 9 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(Long.MIN_VALUE + threeHInSeconds + nineMInSeconds);

        long mRelativeValue = universeDateTime.getMRelativeValue();

        assertEquals(9, mRelativeValue);
    }

    @Test
    public void testGetMRelativeValueFullH() {
        long threeHInSeconds = 3 * 10 * 2;
        long tenMInSeconds = 10 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(
                Long.MIN_VALUE + threeHInSeconds + tenMInSeconds
        );

        long mRelativeValue = universeDateTime.getMRelativeValue();

        assertEquals(0, mRelativeValue);
    }

    @Test
    public void testGetHRelativeValue() {
        long eightMInSeconds = 8 * 2;
        long fiveHInSeconds = 5 * 10 * 2;
        long elevenDInSeconds = 11 * 10 * 10 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new li.yuri.openspacebox.ingame.common.UniverseDateTime(
                Long.MIN_VALUE + eightMInSeconds + fiveHInSeconds + elevenDInSeconds
        );

        long hRelativeValue = universeDateTime.getHRelativeValue();

        assertEquals(5, hRelativeValue);
    }

    @Test
    public void testGetDRelativeValue() {
        long eightHInSeconds = 8 * 10 * 2;
        long dInSeconds = 108 * 10 * 10 * 2;
        long fiveYInSeconds = 5 * 200 * 10 * 10 * 2;
        li.yuri.openspacebox.ingame.common.UniverseDateTime universeDateTime = new UniverseDateTime(
                Long.MIN_VALUE + eightHInSeconds + dInSeconds + fiveYInSeconds
        );

        long dRelativeValue = universeDateTime.getDRelativeValue();

        assertEquals(108, dRelativeValue);
    }


}