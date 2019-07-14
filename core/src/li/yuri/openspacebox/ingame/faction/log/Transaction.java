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

package li.yuri.openspacebox.ingame.faction.log;

import li.yuri.openspacebox.assetmanagement.asset.FA;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.ingame.common.UniverseDateTime;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;

public class Transaction implements LogEntry {

    /**
     * Id of the {@link AbstractIngameObject} which represented the faction (for this transaction) who is owning this
     * {@link Transaction}-event (when a transaction takes place, a {@link Transaction}-event is added to each of the
     * both trading factions).
     */
    private final String ownId;

    /**
     * Id of the {@link AbstractIngameObject} which represented the faction (for this transaction) who was traded with.
     */
    private final String partnerId;

    private final Long cost;
    private final String itemTypeId;
    private final Integer amount;
    private final UniverseDateTime dateTime;

    public Transaction(
            String ownId,
            String partnerId,
            Long cost,
            String itemTypeId,
            Integer amount,
            UniverseDateTime dateTime
    ) {
        this.ownId = ownId;
        this.partnerId = partnerId;
        this.cost = cost;
        this.itemTypeId = itemTypeId;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    @Override
    public UniverseDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String createMessage(ResourceBundles resourceBundles, GameControllers gameControllers) {
        return "something";
    }

    @Override
    public li.yuri.openspacebox.assetmanagement.asset.FA getIcon() {
        return cost < 0 ? li.yuri.openspacebox.assetmanagement.asset.FA.LEVEL_DOWN_ALT : FA.LEVEL_UP_ALT;
    }
}
