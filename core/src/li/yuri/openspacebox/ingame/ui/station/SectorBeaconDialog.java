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

package li.yuri.openspacebox.ingame.ui.station;

import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.ingame.controller.Sector;
import li.yuri.openspacebox.ingame.ui.map.UniverseMap;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbWindow;

import java.util.Collection;

public class SectorBeaconDialog extends OsbWindow {

    private final SectorBeaconDialogCallback callback;

    private UniverseMap universeMap;

    public SectorBeaconDialog(OsbGui stage, SectorBeaconDialogCallback callback) {
        super(ResourceBundles.getInstance().getIngameHud().get("switchSector"), stage);
        this.callback = callback;
        setModal(true);
    }

    public void loadSectors(Collection<li.yuri.openspacebox.ingame.controller.Sector.SectorProperties> sectors) {
        universeMap = new UniverseMap(sectors);
        universeMap.addSectorSelectedListener(this::onSectorSelected);
        add(universeMap).fill().expand();
    }

    private void onSectorSelected(li.yuri.openspacebox.ingame.controller.Sector.SectorProperties sectorProperties) {
        close();
        callback.sectorSelected(sectorProperties);
    }

    public interface SectorBeaconDialogCallback {
        void sectorSelected(Sector.SectorProperties sector);
    }
}
