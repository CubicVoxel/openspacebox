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

public class CustomEntry implements LogEntry {
    private final UniverseDateTime dateTime;
    private final String message;

    public CustomEntry(UniverseDateTime dateTime, String message) {
        this.dateTime = dateTime;
        this.message = message;
    }

    @Override
    public UniverseDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String createMessage(
            ResourceBundles resourceBundles, GameControllers gameControllers
    ) {
        return message;
    }

    @Override
    public li.yuri.openspacebox.assetmanagement.asset.FA getIcon() {
        return FA.COMMENT;
    }
}
