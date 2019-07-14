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
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import li.yuri.openspacebox.ingame.controller.Chunk;
import li.yuri.openspacebox.ingame.controller.GameEventBus;
import li.yuri.openspacebox.ingame.controller.Sector;
import li.yuri.openspacebox.util.Updatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public abstract class AbstractIngameObject<SHAPE extends Shape2D> implements Disposable, Updatable {

    @Getter(AccessLevel.PROTECTED) private final li.yuri.openspacebox.ingame.controller.GameEventBus gameEventBus;
    @Getter @Setter(AccessLevel.PROTECTED) private Sprite sprite;
    // TODO: 25.12.16 Rethink that shape-mechanic
    @Getter @Setter(AccessLevel.PROTECTED) private SHAPE shape;

    /**
     * Should not be messed around when the object is registered.
     */
    @Getter @Setter private String id = UUID.randomUUID().toString();
    @Getter private li.yuri.openspacebox.ingame.controller.Sector sector;

    protected AbstractIngameObject(GameEventBus gameEventBus, Sprite sprite, SHAPE shape) {
        this.gameEventBus = gameEventBus;
        this.sprite = sprite;
        this.shape = shape;

        gameEventBus.register(this);
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    protected final void move(float x, float y) {
        move(new Vector2(x, y));
    }

    protected void move(Vector2 dir) {
        setPosition(getPosition().add(dir));
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    /**
     * Sets the position and makes sure this object is in the correct chunk.
     */
    public void setPosition(Vector2 position) {
        moveToAnotherChunkForPositionIfNecessary(position);
        sprite.setX(position.x);
        sprite.setY(position.y);
    }

    private void moveToAnotherChunkForPositionIfNecessary(Vector2 position) {
        // Special but common case: UniverseBuilder setsPosition, sector is not set.
        if (getSector() != null) {
            int formerChunkIndexX = Chunk.getChunkXFor(getPosition().x), formerChunkIndexY = Chunk.getChunkYFor(getPosition().y);
            int newChunkIndexX = Chunk.getChunkXFor(position.x), newChunkIndexY = Chunk.getChunkYFor(position.y);

            // New chunk index? Move this to the new chunk!
            if (newChunkIndexX != formerChunkIndexX || newChunkIndexY != formerChunkIndexY) {
                getSector().getChunkForIndex(formerChunkIndexX, formerChunkIndexY).removeObject(this);
                getSector().getChunkForIndex(newChunkIndexX, newChunkIndexY).addObject(this);
            }
        }
    }

    public Vector2 getSize() {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    public float getRotation() {
        return sprite.getRotation();
    }

    public void setRotation(float rotation) {
        sprite.setRotation(rotation);
    }

    public void setSector(Sector sector) {
        // Remove object from old chunk
        if (this.sector != null)
            this.sector.getChunkForObject(this).removeObject(this);

        this.sector = sector;

        sector.getChunkForObject(this).addObject(this);
    }


    /**
     * The higher the draw layer, the later the object gets drawn and therefore is always on top of objects with on a
     * lower layer.
     */
    public abstract Layer getLayer();


    /**
     * Override this to implement per-update behaviour. Is called after input-listeners are fired.
     */
    @Override
    public void update(float deltaTime) {
        // Nothing in default implementation as of yet
    }

}

