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

package li.yuri.openspacebox.ingame.faction;

import com.google.common.collect.ImmutableList;
import li.yuri.openspacebox.ingame.common.UniverseDateTime;
import li.yuri.openspacebox.ingame.faction.log.CustomEntry;

import java.util.Collection;

public class CommonFactions {

    public static final String ID_STATIC_OBJECT_FACTION = "staticObjectFaction";
    public static final String ID_PLAYER_FACTION = "playerFaction";

    /**
     * Faction for all props/static objects
     */
    public static final Faction STATIC_OBJECT_FACTION = Faction.createGodFaction(ID_STATIC_OBJECT_FACTION);

    /**
     * Player's faction
     */
    public static final Faction PLAYER_FACTION = Faction.create(ID_PLAYER_FACTION);

    private static final ImmutableList<Faction> ALL = ImmutableList.of(STATIC_OBJECT_FACTION, PLAYER_FACTION);

    // TODO: These are dummy values
    static {
        PLAYER_FACTION.getFactionLog().addEntry(
                new li.yuri.openspacebox.ingame.faction.log.CustomEntry(new UniverseDateTime(Long.MIN_VALUE), "I am logged")
        );
        PLAYER_FACTION.getFactionLog().addEntry(
                new li.yuri.openspacebox.ingame.faction.log.CustomEntry(new UniverseDateTime(Long.MIN_VALUE + 30), "I am logged later.")
        );
        PLAYER_FACTION.getFactionLog().addEntry(
                new li.yuri.openspacebox.ingame.faction.log.CustomEntry(new UniverseDateTime(Long.MIN_VALUE + 5055), "I am logged much later")
        );
        PLAYER_FACTION.getFactionLog().addEntry(
                new CustomEntry(
                        new UniverseDateTime(Long.MIN_VALUE + 813),
                        "I am a very very very very very very very very very very very very very very very very very " +
                                "very very very very very very very very very very very very very very very very very" +
                                " very very very very very very very very very very very very very very very very " +
                                "very very very very very very very very very very very very very very very very very" +
                                " very very very very very very very very very very very very very very very very " +
                                "very very very very very very very very very very very very very very very very very" +
                                " long message."
                ));
    }

    public static Collection<Faction> all() {
        return ALL;
    }
}
