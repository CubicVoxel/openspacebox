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

package li.yuri.openspacebox.ingame.ui.logbook;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.faction.log.FactionLog;
import li.yuri.openspacebox.ingame.faction.log.LogEntry;

public class FactionLogView extends Table {
    private static final float ELEMENT_VERTICAL_PADDING = 3.0f;

    private final li.yuri.openspacebox.assetmanagement.asset.ResourceBundles resourceBundles;
    private final li.yuri.openspacebox.ingame.controller.GameControllers gameControllers;

    public FactionLogView(
            ResourceBundles resourceBundles,
            GameControllers gameControllers
    ) {
        this.resourceBundles = resourceBundles;
        this.gameControllers = gameControllers;

        columnDefaults(0).expandX().fillX().align(Align.topLeft);
    }

    public void loadFactionLog(FactionLog log) {
        getChildren().clear();

        for (LogEntry logEntry : log.getEntries()) {
            LogEntryView view = new LogEntryView(gameControllers, resourceBundles);
            view.loadLogEntry(logEntry);

            add(view).padTop(ELEMENT_VERTICAL_PADDING).padBottom(ELEMENT_VERTICAL_PADDING);
            row();
        }
    }
}
 