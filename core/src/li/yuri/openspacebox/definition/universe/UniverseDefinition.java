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

package li.yuri.openspacebox.definition.universe;

import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "UniverseDefinition")
@Data
public class UniverseDefinition {

    @Attribute
    private String id;

    @ElementList(name = "Factions", required = false)
    private List<FactionDefinition> factionDefinitions;

    @ElementList(name = "Sectors")
    private List<SectorDefinition> sectors;

    @ElementList(name = "SectorConnections", required = false)
    private List<SectorConnection> sectorConnections;


    @Root(name = "SectorConnection")
    @Data
    public static class SectorConnection {

        @Attribute
        private String a;

        @Attribute
        private String b;
    }
}
