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

import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import li.yuri.openspacebox.ingame.object.Ship;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static java8.util.stream.StreamSupport.stream;

public final class IngameObjectRegistry {
    private final Map<String, AbstractIngameObject<?>> objects = new TreeMap<>();

    public void registerObject(AbstractIngameObject<?> object) {
        objects.put(object.getId(), object);
    }

    public void registerAllObjects(Collection<AbstractIngameObject<?>> objects) {
        for (AbstractIngameObject<?> object : objects)
            registerObject(object);
    }

    public void registerAllIn(Sector sector) {
        registerAllObjects(sector.getIngameObjects());
    }

    public void unregisterObject(AbstractIngameObject<?> object) {
        unregisterObject(object.getId());
    }

    public void unregisterObject(String id) {
        objects.remove(id);
    }

    public void getObject(String id) {
        objects.get(id);
    }

    /**
     * Determines the player by searching in all objects. An instanceof makes this an extremely heavy method.
     */
    public java8.util.Optional<Ship> determinePlayer() {
        return stream(objects.values())
                .filter(Ship.class::isInstance)
                .map(obj -> (Ship) obj) // Get every object which is a ship and map it
                .filter(Ship::isPlayer)
                .findFirst(); // Pick any ship which is a player
    }
}
