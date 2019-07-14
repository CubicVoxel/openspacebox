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
import java8.util.stream.StreamSupport;
import li.yuri.openspacebox.ingame.faction.CommonFactions;
import li.yuri.openspacebox.ingame.faction.Faction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java8.util.stream.StreamSupport.stream;

public final class FactionManager {

    private final Set<Faction> customFactions = new HashSet<>();

    public Faction whoOwns(final String objectId) {
        return stream(customFactions)
                .filter(faction -> faction.getBelongings().has(objectId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Either an object with the id '" + objectId + "' " +
                        "does not exist, or the object is ownerless " +
                        "and should not exist."));
    }

    public void registerFactions(final Collection<Faction> factions) {
        customFactions.addAll(factions);
    }

    public Optional<Faction> findById(final String factionId) {
        return findCommonFactionById(factionId).or(() -> findCustomFactionById(factionId));
    }

    private Optional<Faction> findCustomFactionById(final String factionId) {
        return StreamSupport.stream(customFactions)
                .filter(faction -> faction.getId().equals(factionId))
                .findAny();
    }

    private Optional<Faction> findCommonFactionById(final String factionId) {
        return StreamSupport.stream(CommonFactions.all())
                .filter(faction -> faction.getId().equals(factionId))
                .findAny();
    }
}
