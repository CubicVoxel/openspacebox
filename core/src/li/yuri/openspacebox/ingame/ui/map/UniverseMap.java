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

package li.yuri.openspacebox.ingame.ui.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.ingame.controller.Sector;
import li.yuri.openspacebox.ingame.ui.hud.SectorButton;

import java.util.ArrayList;
import java.util.Collection;

import static java8.util.stream.StreamSupport.stream;

/**
 * A widgets which displays a map of the given sectors.
 */
public class UniverseMap extends WidgetGroup {

    Image background;
    private ArrayList<SectorButton.SectorSelectedListener> sectorSelectedListeners = new ArrayList<>();

    public UniverseMap(Collection<li.yuri.openspacebox.ingame.controller.Sector.SectorProperties> sectors) {
        TiledDrawable drawable = new TiledDrawable(new TextureRegion(OpenSpaceBox.getAsset(TextureAsset
                .UI_UNIVERSEMAP_BACKGROUND)));
        background = new Image(drawable);
        background.setFillParent(true);
        addActor(background);

        for (li.yuri.openspacebox.ingame.controller.Sector.SectorProperties sector : sectors) {
            SectorButton actor = new SectorButton(sector);
            actor.addSectorSelectedListener(this::fireSectorSelectedListeners);
            addActor(actor);
        }


    }

    public void addSectorSelectedListener(SectorButton.SectorSelectedListener listener) {
        sectorSelectedListeners.add(listener);
    }

    private void fireSectorSelectedListeners(Sector.SectorProperties sector) {
        stream(sectorSelectedListeners).forEach(listener -> listener.onSectorSelected(sector));
    }

}
