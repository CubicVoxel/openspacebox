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

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.faction.CommonFactions;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbWindow;

public class PlayerLogBook extends OsbWindow {

    public PlayerLogBook(
            li.yuri.openspacebox.assetmanagement.asset.ResourceBundles resourceBundles,
            GameControllers gameControllers,
            OsbGui stage
    ) {
        super(ResourceBundles.getInstance().getIngameHud().get("playerLogBook"), stage);
        setModal(true);

        final FactionLogView factionLogView = new FactionLogView(resourceBundles, gameControllers);
        factionLogView.loadFactionLog(CommonFactions.PLAYER_FACTION.getFactionLog());

        final ScrollPane scrollPane = new ScrollPane(factionLogView);
        scrollPane.setScrollingDisabled(true, false);
        add(scrollPane).fill().expand();

    }

}
