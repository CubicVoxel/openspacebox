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

package li.yuri.openspacebox.ingame.faction;

import li.yuri.openspacebox.ingame.object.AbstractIngameObject;

import java.util.Set;
import java.util.TreeSet;

public class Belongings {

    private Set<String> objectIds = new TreeSet<>();

    public void add(AbstractIngameObject<?> object) {
        add(object.getId());
    }

    public void add(String objectId) {
        objectIds.add(objectId);
    }

    public void remove(AbstractIngameObject<?> object) {
        remove(object.getId());
    }

    private void remove(String id) {
        objectIds.remove(id);
    }


    public void transferTo(Belongings target, String id) {
        remove(id);
        target.acceptTransfer(this, id);
    }

    public void acceptTransfer(Belongings source, String id) {
        add(id);
    }

    public boolean has(String id) {
        return objectIds.contains(id);
    }
}
