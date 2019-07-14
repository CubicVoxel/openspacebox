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
import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ShipType")
@Data
public class ShipType implements Type {
    @Attribute
    private String id;

    @Element(name = "DisplayName")
    private String displayName;

    @Element(name = "CargoSpace")
    private int cargoSpace;

    @Element(name = "Physics")
    private Physics physics = new Physics();

    @Element(name = "SpriteAtlas")
    private SpriteAtlas spriteAtlas;

    @Data
    @Root(name = "Physics")
    public static class Physics {

        @Attribute
        private float maxSpeed;

        @Attribute
        private float acceleration;

        @Attribute
        private float deceleration;

        @Attribute
        private float rotationSpeed;

    }
}
