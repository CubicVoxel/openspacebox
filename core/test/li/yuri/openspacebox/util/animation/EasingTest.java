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

package li.yuri.openspacebox.util.animation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EasingTest {

    @Test
    public void testLinearZero() {
        float targetValue = 83;
        float timePercentage = 0.0f;

        float value = Easing.LINEAR.getValue(targetValue, timePercentage);

        assertEquals(0, value, .00001f);
    }

    @Test
    public void testLinearFull() {
        float targetValue = 83;
        float timePercentage = 1.0f;

        float value = Easing.LINEAR.getValue(targetValue, timePercentage);

        assertEquals(targetValue, value, .00001f);
    }

    @Test
    public void testLinearHalf() {
        float targetValue = 83;
        float timePercentage = .5f;

        float value = Easing.LINEAR.getValue(targetValue, timePercentage);

        assertEquals(41.5f, value, .0001f);
    }

    @Test
    public void testCubicInZero() {
        float targetValue = 83;
        float timePercentage = 0.0f;

        float value = Easing.CUBIC_IN.getValue(targetValue, timePercentage);

        assertEquals(0, value, .00001f);
    }

    @Test
    public void testCubicInFull() {
        float targetValue = 83;
        float timePercentage = 1.0f;

        float value = Easing.CUBIC_IN.getValue(targetValue, timePercentage);

        assertEquals(83.0f, value, .00001f);
    }


    @Test
    public void testCubicInHalf() {
        float targetValue = 83;
        float timePercentage = .5f;

        float value = Easing.CUBIC_IN.getValue(targetValue, timePercentage);

        assertEquals(10.375f, value, .0001f);
    }

    @Test
    public void testCubicOutZero() {
        float targetValue = 83;
        float timePercentage = 0f;

        float value = Easing.CUBIC_OUT.getValue(targetValue, timePercentage);

        assertEquals(0f, value, .00001f);
    }

    @Test
    public void testCubicOutFull() {
        float targetValue = 83;
        float timePercentage = 1f;

        float value = Easing.CUBIC_OUT.getValue(targetValue, timePercentage);

        assertEquals(targetValue, value, .00001f);
    }

    @Test
    public void testCubicOutHalf() {
        float targetValue = 83;
        float timePercentage = .5f;

        float value = Easing.CUBIC_OUT.getValue(targetValue, timePercentage);

        assertEquals(72.625, value, .00001f);
    }

}