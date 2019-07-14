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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.assetmanagement.util.AssetUtil;
import li.yuri.openspacebox.ingame.controller.GameEventBus;
import li.yuri.openspacebox.util.Range;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Asteroid extends AbstractIngameObject<Rectangle> {

    private AsteroidProperties asteroidProperties;

    public Asteroid(GameEventBus gameEventBus, AsteroidProperties asteroidProperties) {
        super(gameEventBus, createSprite(asteroidProperties), createRectangle(asteroidProperties));
        this.asteroidProperties = asteroidProperties;
    }

    @Override
    public Layer getLayer() {
        return Layer.SMALL_SHIPS_AND_PROPS;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setRotation(getRotation() + asteroidProperties.getRotationSpeed() * deltaTime);
    }

    private static Sprite createSprite(AsteroidProperties asteroidProperties) {
        Sprite sprite = OpenSpaceBox.createSprite(asteroidProperties.getSpriteAtlas());
        sprite.setSize(asteroidProperties.getSize(), asteroidProperties.getSize());
        sprite.setOriginCenter();
        return sprite;
    }

    private static Rectangle createRectangle(AsteroidProperties asteroidProperties) {
        Rectangle rect = new Rectangle();
        rect.setSize(asteroidProperties.getSize(), asteroidProperties.getSize());
        return rect;
    }

    @Data
    @AllArgsConstructor
    public static class AsteroidProperties {

        private float rotationSpeed;

        /**
         * Asteroids always have same height and width.
         */
        private float size;

        private SpriteAtlas spriteAtlas;


        /**
         * Generates AsteroidProperties based on random parameters.
         *
         * @param assetMatcher  A regexp defining which of the {@link TextureAsset}s may be used.
         * @param rotationSpeed The minimum and maximum rotation speed.
         * @param size          The minimum and maximum size.
         */
        public static AsteroidProperties fromRandom(String assetMatcher, li.yuri.openspacebox.util.Range rotationSpeed, Range size) {
            // TODO: Also generate negative rotationSpeed
            return new AsteroidProperties(
                    MathUtils.random(rotationSpeed.getMin(), rotationSpeed.getMax()),
                    MathUtils.random(size.getMin(), size.getMax()),
                    AssetUtil.randomSpriteAtlasFromRegExp(assetMatcher)
            );
        }
    }
}
