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

package li.yuri.openspacebox.ingame.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java8.util.J8Arrays;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import li.yuri.openspacebox.ingame.object.Layer;
import lombok.Getter;

import java.util.ArrayList;

import static java8.util.stream.StreamSupport.stream;

/**
 * Basically a collection of {@link AbstractIngameObject}s.
 * <p>
 * The {@link #CHUNK_SIZE} specifies the area a Chunk covers within a SectorDefinition, {@link AbstractIngameObject}s
 * look for a change of their chunk-index using {@link #getChunkXFor(float)} and {@link #getChunkYFor(float)} (both
 * methods are static since the chunk-size is always the same) when their position is updated and therefore the objects
 * get added to and removed from their Chunks by themselves.
 * <p>
 * The Chunks get managed by a {@link ChunkManager} where they are saved by index and updated sequentially.
 */
public class Chunk {

    public static final int CHUNK_SIZE = 512;

    @Getter private boolean upToDate = false;
    @Getter private long lastUpdateTime;
    @Getter private int lastUpdateCycle = -1;
    private Multimap<Layer, AbstractIngameObject> objectsInChunk;

    public Chunk() {
        objectsInChunk = ArrayListMultimap.create();
        lastUpdateTime = System.nanoTime();
    }

    public void addObject(AbstractIngameObject abstractIngameObject) {
        objectsInChunk.put(abstractIngameObject.getLayer(), abstractIngameObject);
    }

    public void removeObject(AbstractIngameObject abstractIngameObject) {
        objectsInChunk.remove(abstractIngameObject.getLayer(), abstractIngameObject);
    }

    public void update(long systemNanoTime, int updateCycle) {
        float deltaTime = getDeltaTime(systemNanoTime);
        this.lastUpdateTime = systemNanoTime;
        this.lastUpdateCycle = updateCycle;
        this.upToDate = true;

        // Update all objects, but create a copy of objectsInChunk.values() beforehand, because objects may remove
        // themselves in their update.
        stream(new ArrayList<>(objectsInChunk.values())).forEach(object -> object.update(deltaTime));
    }

    public void drawOn(SpriteBatch batch) {
        J8Arrays.stream(Layer.values()).sorted(Layer.COMPARE_BY_DRAW_LAYER).forEachOrdered(layer ->
                stream(objectsInChunk.get(layer)).forEach(x -> x.getSprite().draw(batch))
        );
    }

    public void markNotUpToDate() {
        upToDate = false;
    }

    private float getDeltaTime(long systemNanoTime) {
        // Nanoseconds to seconds
        return (systemNanoTime - lastUpdateTime) / 1000000000.0f;
    }

    /**
     * Calculates the chunk's index for the given x position.
     */
    public static int getChunkXFor(float x) {
        return (int) (x / CHUNK_SIZE);
    }

    /**
     * Calculates the chunk's index for the given y position.
     */
    public static int getChunkYFor(float y) {
        return (int) (y / CHUNK_SIZE);
    }
}
