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
import com.badlogic.gdx.math.Rectangle;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.ingame.controller.GameEventBus;

public class Station extends AbstractIngameObject<Rectangle> implements TradeEntity {

    private li.yuri.openspacebox.definition.type.StationType stationType;
    private li.yuri.openspacebox.ingame.object.CargoHold cargoHold;
    private float rotation = 0f;

    public Station(GameEventBus gameEventBus, li.yuri.openspacebox.definition.type.StationType stationType) {
        super(gameEventBus, createSprite(stationType), createShape(stationType));
        this.stationType = stationType;
        this.cargoHold = new li.yuri.openspacebox.ingame.object.CargoHold(stationType.getCargoSpace());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // TODO: Update prices
    }

    @Override
    public li.yuri.openspacebox.ingame.object.Layer getLayer() {
        return Layer.STATIONS;
    }

    @Override
    public AbstractIngameObject getObject() {
        return this;
    }

    @Override
    public CargoHold getCargoHold() {
        return cargoHold;
    }

    private static Sprite createSprite(li.yuri.openspacebox.definition.type.StationType stationType) {
        Sprite sprite = OpenSpaceBox.createSprite(stationType.getSpriteAtlas());
        sprite.setSize((float) stationType.getSize().getWidth(), (float) stationType.getSize().getHeight());
        sprite.setOriginCenter();
        return sprite;
    }

    private static Rectangle createShape(StationType stationType) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth((float) stationType.getSize().getWidth());
        rectangle.setHeight((float) stationType.getSize().getHeight());
        return rectangle;
    }
}
