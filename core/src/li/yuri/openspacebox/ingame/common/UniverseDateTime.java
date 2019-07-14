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

import li.yuri.openspacebox.ingame.controller.UniverseTimer;

public final class UniverseDateTime implements Comparable<UniverseDateTime> {
    private static final int BASE_YEAR = 10139;

    private final long value;

    /**
     * There is probably no need for you to instantiate this yourself. (See {@link UniverseTimer#now()}).
     *
     * @param value amount of seconds with {@link Long#MIN_VALUE} being zero
     */
    public UniverseDateTime(long value) {
        this.value = value;
    }

    @Override
    public int compareTo(UniverseDateTime o) {
        return Long.compare(this.getValue(), o.getValue());
    }

    public long getValue() {
        return value;
    }

    /**
     * @return the total seconds this instance represents. If the returned value is negative, the seconds overflowed and
     * you actually have more than {@link Long#MAX_VALUE} seconds.
     */
    public long getSecondsTotal() {
        return value - Long.MIN_VALUE;
    }

    private long getDimensionValue(TimeUnit dimension) {
        return getDimensionValue(dimension, getSecondsTotal());
    }

    public long getMTotalValue() {
        return getDimensionValue(TimeUnit.M);
    }

    public long getHTotalValue() {
        return getDimensionValue(TimeUnit.H);
    }

    public long getDTotalValue() {
        return getDimensionValue(TimeUnit.D);
    }

    public long getYTotalValue() {
        return BASE_YEAR + getDimensionValue(TimeUnit.Y);
    }

    /**
     * @return The amount of seconds since the last {@link TimeUnit#M}
     */
    public long getSRelativeValue() {
        final long mTotalValue = getDimensionValue(TimeUnit.M);
        final long mSecondsValue = mTotalValue * TimeUnit.M.getSeconds();
        return getSecondsTotal() - mSecondsValue;
    }

    /**
     * @return The amount of {@link TimeUnit#M} since the last {@link TimeUnit#H}.
     */
    public long getMRelativeValue() {
        final long hTotalValue = getDimensionValue(TimeUnit.H);
        final long hSecondsValue = hTotalValue * TimeUnit.H.getSeconds();
        return getDimensionValue(TimeUnit.M, getSecondsTotal() - hSecondsValue);
    }

    /**
     * @return The amount of {@link TimeUnit#H} since the last {@link TimeUnit#D}
     */
    public long getHRelativeValue() {
        final long dTotalValue = getDimensionValue(TimeUnit.D);
        final long dSecondsValue = dTotalValue * TimeUnit.D.getSeconds();
        return getDimensionValue(TimeUnit.H, getSecondsTotal() - dSecondsValue);
    }

    /**
     * @return The amount of {@link TimeUnit#D} since the last {@link TimeUnit#Y}
     */
    public long getDRelativeValue() {
        final long yTotalValue = getDimensionValue(TimeUnit.Y);
        final long ySecondsValue = yTotalValue * TimeUnit.Y.getSeconds();
        return getDimensionValue(TimeUnit.D, getSecondsTotal() - ySecondsValue);
    }

    private static long getDimensionValue(TimeUnit dimension, long secondsTotal) {
        return secondsTotal / dimension.getSeconds();
    }


}
