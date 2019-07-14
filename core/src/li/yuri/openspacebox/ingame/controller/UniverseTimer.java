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

import com.badlogic.gdx.math.MathUtils;
import li.yuri.openspacebox.ingame.common.UniverseDateTime;
import li.yuri.openspacebox.util.Updatable;

public final class UniverseTimer implements Updatable {

    private long universeSeconds;
    private float fractionalSeconds;

    public UniverseTimer() {
        reset();
    }

    @Override
    public void update(float delta) {
        fractionalSeconds += delta;

        if (fractionalSeconds >= 1) {
            int secs = MathUtils.floor(fractionalSeconds);
            fractionalSeconds -= secs;
            universeSeconds += secs;
        }
    }

    public long getUniverseSeconds() {
        return universeSeconds;
    }

    public void setUniverseSeconds(long universeSeconds) {
        this.universeSeconds = universeSeconds;
    }

    public UniverseDateTime now() {
        return new UniverseDateTime(getUniverseSeconds());
    }

    public void reset() {
        universeSeconds = Long.MIN_VALUE;
    }
}
