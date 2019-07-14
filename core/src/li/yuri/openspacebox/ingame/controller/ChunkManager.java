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
import com.google.common.collect.HashBasedTable;
import java8.util.Optional;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import li.yuri.openspacebox.util.Updatable;
import lombok.Value;
import lombok.val;

import static java8.util.stream.StreamSupport.parallelStream;

/**
 * Holds all chunks, updates and draws them and creates new chunks if necessary. Should be created per {@link Sector}.
 */
public final class ChunkManager implements Updatable {
    private final HighPriorityChunksProvider highPriorityChunksProvider;
    private final DrawableChunksProvider drawableChunksProvider;
    private final Sector sector;

    private HashBasedTable<Integer, Integer, Chunk> chunks = HashBasedTable.create();
    private int updateCycle = 0;

    public ChunkManager(
            HighPriorityChunksProvider highPriorityChunksProvider,
            DrawableChunksProvider drawableChunksProvider,
            Sector sector
    ) {
        this.highPriorityChunksProvider = highPriorityChunksProvider;
        this.drawableChunksProvider = drawableChunksProvider;

        this.sector = sector;
    }

    /**
     * Returns the chunk at the given index. If there is no chunk available, it gets created.
     */
    public Chunk getChunkForIndex(Integer x, Integer y) {
        Chunk chunk = chunks.get(x, y);
        if (chunk == null) {
            chunk = new Chunk();
            chunks.put(x, y, chunk);
        }
        return chunk;
    }

    /**
     * Returns the chunk the object needs to be in based on the object's position. Also see {@link
     * #getChunkForIndex(Integer, Integer)}.
     */
    public Chunk getChunkForObject(AbstractIngameObject abstractIngameObject) {
        return getChunkForIndex(
                Chunk.getChunkXFor(abstractIngameObject.getPosition().x),
                Chunk.getChunkYFor(abstractIngameObject.getPosition().y)
        );
    }

    @Override
    public void update(float delta) {
        updateCycle++;

        Optional<ChunksIndexes> highPriorityChunksIndexes =
                highPriorityChunksProvider.getHighPriorityChunksIndexes(sector);
        if (highPriorityChunksIndexes.isPresent())
            updateHighPriorityChunks(highPriorityChunksIndexes.get());

        boolean updatedAChunk = updateSingleNotUpToDateChunk(delta);
        if (!updatedAChunk) {
            markAllChunksAsNotUpToDate();
            updateSingleNotUpToDateChunk(delta);
        }

    }

    /**
     * Draws the objects in the chunks the {@link #drawableChunksProvider} returns. Make sure this is only called for
     * sectors which are definitely the ones needed to be drawn - {@link DrawableChunksProvider}s don't receive the
     * sector and therefore don't check if this {@link ChunkManager} belongs to the right sector.
     */
    public void drawOn(SpriteBatch batch) {
        drawDrawableChunks(drawableChunksProvider.getDrawableChunksIndexes(), batch);
    }

    /**
     * @return was a chunk updated?
     */
    private boolean updateSingleNotUpToDateChunk(float delta) {
        // Special case which should not really occur. Just return true to make sure the values() is not created
        // unnecessarily and the caller doesn't try to reset the chunks because nothing was updated.
        if (chunks.isEmpty())
            return true;

        for (Chunk chunk : chunks.values()) {
            // Since high-priority-chunks are immediately marked as not-up-to-date, we also need to check if the
            // selected chunk was not just updated in this cycle.
            val systemNanoTime = System.nanoTime();
            if (!chunk.isUpToDate() && chunk.getLastUpdateCycle() != updateCycle) {
                chunk.update(systemNanoTime, updateCycle);
                return true;
            }
        }
        return false;
    }

    private void markAllChunksAsNotUpToDate() {
        parallelStream(chunks.values()).forEach(Chunk::markNotUpToDate);
    }

    /**
     * Updates all chunks within the boundaries specified via the given indexes.
     */
    private void updateHighPriorityChunks(ChunksIndexes chunksIndexes) {
        for (int x = chunksIndexes.getMinX(); x <= chunksIndexes.getMaxX(); x++) {
            for (int y = chunksIndexes.getMinY(); y <= chunksIndexes.getMaxY(); y++) {
                Chunk chunk = chunks.get(x, y);
                if (chunk != null) {
                    chunk.update(System.nanoTime(), updateCycle);
                }
            }
        }
    }

    private void drawDrawableChunks(ChunksIndexes chunksIndexes, SpriteBatch batch) {
        for (int x = chunksIndexes.getMinX(); x <= chunksIndexes.getMaxX(); x++) {
            for (int y = chunksIndexes.getMinY(); y <= chunksIndexes.getMaxY(); y++) {
                getChunkForIndex(x, y).drawOn(batch);
            }
        }
    }

    public interface HighPriorityChunksProvider {
        /**
         * @return ChunksIndexes if something in the given sector needs to be updated with high priority (meaning every
         * frame) or an empty optional.
         */
        Optional<ChunksIndexes> getHighPriorityChunksIndexes(Sector sector);
    }

    public interface DrawableChunksProvider {
        ChunksIndexes getDrawableChunksIndexes();
    }

    @Value
    public static class ChunksIndexes {
        private int minX;
        private int maxX;
        private int minY;
        private int maxY;
    }
}
