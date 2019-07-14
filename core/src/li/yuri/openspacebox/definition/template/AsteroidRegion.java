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

import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import li.yuri.openspacebox.definition.util.Dimensions;
import li.yuri.openspacebox.util.Range;
import lombok.Data;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Data
@Root(name = "AsteroidRegion")
public class AsteroidRegion {

    /**
     * A regular expression which defines which of the {@link SpriteAtlas}ses may be used for asteroids in this region.
     */
    @Attribute(name = "spriteMatcher")
    private String spriteMatcher;

    @Element(name = "Dimensions")
    private Dimensions dimensions;

    @Element(name = "Count")
    private li.yuri.openspacebox.util.Range count;

    @Element(name = "RotationSpeed")
    private li.yuri.openspacebox.util.Range rotationSpeed;

    @Element(name = "Size")
    private Range size;

}
