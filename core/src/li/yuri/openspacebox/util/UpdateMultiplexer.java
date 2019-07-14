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

package li.yuri.openspacebox.util;

import com.badlogic.gdx.utils.Array;
import com.google.common.collect.ImmutableList;
import lombok.val;

public class UpdateMultiplexer implements Updatable {

    private final Array<Updatable> updatables = new Array<>();


    public void register(Updatable updatable) {
        updatables.add(updatable);
    }

    public void unregister(Updatable updatable) {
        updatables.removeValue(updatable, true);
    }


    @Override
    public void update(float delta) {
        for (val updatable : ImmutableList.copyOf(updatables)) updatable.update(delta);
    }
}
