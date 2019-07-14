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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import java8.util.Optional;
import li.yuri.openspacebox.util.Updatable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java8.util.stream.StreamSupport.stream;

public final class SectorManager implements Updatable, Disposable, li.yuri.openspacebox.ingame.controller.ChunkManager.HighPriorityChunksProvider {

    // The amount of chunks around the player which need to get updated with high priority.
    private static final int HIGHPRIO_CHUNK_RADIUS = 3;

    private final FocusedObjectProvider focusedObjectProvider;
    private final li.yuri.openspacebox.ingame.controller.PlayerManager playerManager;

    @Getter private List<Sector> sectors = new ArrayList<>();


    public SectorManager(FocusedObjectProvider focusedObjectProvider, PlayerManager playerManager) {
        this.focusedObjectProvider = focusedObjectProvider;
        this.playerManager = playerManager;
    }

    public void addSector(Sector sector) {
        sectors.add(sector);
    }

    @Override
    public void update(float delta) {
        stream(sectors).forEach(x -> x.update(delta));
    }

    @Override
    public void dispose() {
        stream(sectors).forEach(Sector::dispose);
    }


    @Override
    public Optional<li.yuri.openspacebox.ingame.controller.ChunkManager.ChunksIndexes> getHighPriorityChunksIndexes(Sector sector) {
        if (!sector.equals(focusedObjectProvider.getFocusedObject().getSector())) {
            return Optional.empty();
        } else {
            Vector2 playerPosition = playerManager.getPlayer().get().getPosition();
            int playerChunkX = Chunk.getChunkXFor(playerPosition.x);
            int playerChunkY = Chunk.getChunkYFor(playerPosition.y);
            return Optional.of(new ChunkManager.ChunksIndexes(
                    playerChunkX - HIGHPRIO_CHUNK_RADIUS, playerChunkX + HIGHPRIO_CHUNK_RADIUS,
                    playerChunkY - HIGHPRIO_CHUNK_RADIUS, playerChunkY + HIGHPRIO_CHUNK_RADIUS
            ));
        }
    }

}
