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

package li.yuri.openspacebox.definition.type;

import lombok.Data;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Object for mapping {@link li.yuri.openspacebox.definition.type.Type}s to/from XMLs.
 */
@Root(name = "TypeDefinitions")
@Data
public class TypeDefinitions {
    @ElementList(name = "ShipTypes", required = false)
    private List<li.yuri.openspacebox.definition.type.ShipType> shipTypes = new ArrayList<>();

    @ElementList(name = "StationTypes", required = false)
    private List<li.yuri.openspacebox.definition.type.StationType> stationTypes = new ArrayList<>();

    @ElementList(name = "ItemTypes", required = false)
    private List<li.yuri.openspacebox.definition.type.ItemType> itemTypes = new ArrayList<>();

    public void add(li.yuri.openspacebox.definition.type.Type type) {
        // Here come dat instanceof!! o ship watbaddesign
        // Seriously, i really don't care at the moment. For today, i just want to get shit done.
        if (type instanceof li.yuri.openspacebox.definition.type.ShipType)
            shipTypes.add((li.yuri.openspacebox.definition.type.ShipType) type);
        else if (type instanceof li.yuri.openspacebox.definition.type.StationType)
            stationTypes.add((li.yuri.openspacebox.definition.type.StationType) type);
        else if (type instanceof li.yuri.openspacebox.definition.type.ItemType)
            itemTypes.add((li.yuri.openspacebox.definition.type.ItemType) type);
    }

    public void remove(Type type) {
        // Yea, it doesn't get any better
        if (type instanceof ShipType)
            shipTypes.remove(type);
        else if (type instanceof StationType)
            stationTypes.remove(type);
        else if (type instanceof ItemType)
            itemTypes.remove(type);
    }
}
