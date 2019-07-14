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

import java8.util.Optional;
import li.yuri.openspacebox.ingame.event.PlayerChangeEvent;
import li.yuri.openspacebox.ingame.object.Ship;

public final class PlayerManager {

    private final li.yuri.openspacebox.ingame.controller.GameEventBus gameEventBus;

    private Ship player;

    public PlayerManager(GameEventBus gameEventBus) {
        this.gameEventBus = gameEventBus;
    }

    public Optional<Ship> getPlayer() {
        return Optional.ofNullable(player);
    }

    private Optional<Ship> deactivatePlayer() {
        // Deactivate former player
        Optional<Ship> formerPlayer = Optional.ofNullable(player);
        formerPlayer.ifPresent(x -> x.setPlayer(false));
        return formerPlayer;
    }

    public void changePlayerToNone() {
        Optional<Ship> formerPlayer = deactivatePlayer();
        this.player = null;
        gameEventBus.post(new li.yuri.openspacebox.ingame.event.PlayerChangeEvent(formerPlayer, Optional.empty()));
    }


    public void changePlayerTo(Ship newPlayer) {
        Optional<Ship> formerPlayer = deactivatePlayer();
        newPlayer.setPlayer(true);
        this.player = newPlayer;
        gameEventBus.post(new PlayerChangeEvent(formerPlayer, Optional.of(newPlayer)));
    }
}
