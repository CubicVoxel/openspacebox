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

import com.badlogic.gdx.utils.Disposable;
import li.yuri.openspacebox.definition.universe.UniverseDefinition;
import li.yuri.openspacebox.util.Updatable;
import lombok.Getter;
import lombok.val;


/**
 * Facade for game controllers.
 */
public final class GameControllers implements Updatable, Disposable {

    @Getter private final li.yuri.openspacebox.ingame.controller.GameEventBus gameEventBus;
    @Getter private final li.yuri.openspacebox.ingame.controller.SectorManager sectorManager;
    @Getter private final li.yuri.openspacebox.ingame.controller.ChunkManager.DrawableChunksProvider drawableChunksProvider;
    @Getter private final IngameObjectRegistry ingameObjectRegistry;
    @Getter private final UniverseTimer universeTimer;
    @Getter private final li.yuri.openspacebox.ingame.controller.PlayerManager playerManager;
    @Getter private final FocusedObjectProvider focusedObjectProvider;
    @Getter private final FactionManager factionManager;


    public GameControllers(
            li.yuri.openspacebox.ingame.controller.ChunkManager.DrawableChunksProvider drawableChunksProvider
    ) {
        this.drawableChunksProvider = drawableChunksProvider;

        gameEventBus = new GameEventBus();
        ingameObjectRegistry = new IngameObjectRegistry();
        universeTimer = new UniverseTimer();
        playerManager = new PlayerManager(gameEventBus);
        focusedObjectProvider = new FocusedObjectProvider(playerManager);
        sectorManager = new SectorManager(focusedObjectProvider, playerManager);
        factionManager = new FactionManager();
    }

    /**
     * Initializes the SectorManager by building all the sectors in the given universe.
     */
    public void load(UniverseDefinition universe) {
        val universeBuilder = new UniverseBuilder(
                gameEventBus,
                factionManager,
                sectorManager,
                sectorManager,
                ingameObjectRegistry,
                drawableChunksProvider
        );
        universeBuilder.buildUniverse(universe);

        ingameObjectRegistry
                .determinePlayer()
                .ifPresentOrElse(playerManager::changePlayerTo, playerManager::changePlayerToNone);
        universeTimer.reset();
    }


    @Override
    public void update(float delta) {
        sectorManager.update(delta);
        universeTimer.update(delta);
    }

    @Override
    public void dispose() {
        sectorManager.dispose();
    }

    public ChunkManager.HighPriorityChunksProvider getHighPriorityChunksProvider() {
        return sectorManager;
    }
}
