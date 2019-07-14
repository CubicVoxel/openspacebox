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

package li.yuri.openspacebox.ingame.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import li.yuri.openspacebox.ingame.controller.GameEventBus;
import lombok.val;

public class SectorBeacon extends AbstractIngameObject<Circle> {

    private static final float DIAMETER = 580;

    public SectorBeacon(GameEventBus gameEventBus) {
        super(gameEventBus, createSprite(), createShape());
    }

    @Override
    public li.yuri.openspacebox.ingame.object.Layer getLayer() {
        return Layer.STATIONS;
    }

    private static Circle createShape() {
        val radius = MathUtils.round(DIAMETER / 2);
        return new Circle(0, 0, radius);

    }

    private static Sprite createSprite() {
        Sprite sprite = OpenSpaceBox.createSprite(SpriteAtlas.STATION_SECTORBEACON);
        sprite.setSize(DIAMETER, DIAMETER);
        sprite.setOriginCenter();
        return sprite;
    }
}
