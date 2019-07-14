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

import li.yuri.openspacebox.ingame.faction.log.FactionLog;
import lombok.Data;

import java.util.UUID;

@Data
public class Faction {

    private final FactionLog factionLog;
    private final Belongings belongings;
    private final li.yuri.openspacebox.ingame.faction.Wallet wallet;

    private String id;
    private String name;

    private Faction(Wallet wallet) {
        this.wallet = wallet;

        factionLog = new FactionLog();
        belongings = new Belongings();
    }


    public static Faction createGodFaction(String id) {
        Faction faction = new Faction(new GodWallet());
        faction.setId(id);
        return faction;
    }

    public static Faction create() {
        return create(UUID.randomUUID().toString());
    }

    public static Faction create(String id) {
        Faction faction = new Faction(new DefaultWallet());
        faction.setId(id);
        return faction;
    }

}
