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

import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "StationType")
@Data
public class StationType implements Type {
    @Attribute
    private String id;

    @Element(name = "DisplayName")
    private String displayName;

    @Element(name = "Description")
    private String description;

    @Element(name = "SpriteAtlas")
    private SpriteAtlas spriteAtlas;

    @Element(name = "Size")
    private Size size;

    @Element(name = "CargoSpace")
    private int cargoSpace;

    @ElementList(name = "StorageAllocations")
    private List<StorageAllocation> storageAllocations;

    @ElementList(name = "Productions")
    private List<Production> productions;

    @Data
    @Root(name = "StorageAllocation")
    public static class StorageAllocation {
        @Attribute
        private String item;

        @Attribute
        private int size;
    }

    @Data
    @Root(name = "Production")
    public static class Production {
        @Attribute
        private float duration;

        @ElementList(name = "Inputs")
        private List<Resource> inputs;

        @ElementList(name = "Outputs")
        private List<Resource> outputs;
    }

    @Data
    @Root(name = "Resource")
    public static class Resource {
        @Attribute
        private String item;

        @Attribute
        private int amount;
    }

    @Data
    @Root(name = "Size")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Size {
        @Attribute
        private double width;

        @Attribute
        private double height;
    }

}
