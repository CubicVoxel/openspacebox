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

package li.yuri.openspacebox.definition.template;

import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.definition.util.Dimensions;
import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "SectorTemplate")
@Data
public class SectorTemplate {
    @Attribute
    private String id;

    @Element(name = "Dimensions")
    private Dimensions dimensions;

    @Element(name = "Background")
    private Background background;

    @ElementList(name = "Stations", required = false)
    private List<StationTemplate> stations = new ArrayList<>();

    @ElementList(name = "Ships", required = false)
    private List<ShipTemplate> ships = new ArrayList<>();

    @ElementList(name = "AsteroidRegions", required = false)
    private List<AsteroidRegion> asteroidRegions = new ArrayList<>();

    @ElementList(name = "SectorBeacons", required = false)
    private List<SectorBeaconTemplate> sectorBeacons = new ArrayList<>();

    @Root(name = "Background")
    @Data
    public static class Background {
        @Attribute
        private TextureAsset textureAsset;

        @Attribute
        private float width;
        @Attribute
        private float height;
    }
}
