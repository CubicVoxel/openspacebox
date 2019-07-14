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

import com.badlogic.gdx.utils.Align;
import java8.util.Optional;
import li.yuri.openspacebox.ingame.object.Station;
import li.yuri.openspacebox.ingame.object.TradeEntity;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbWindow;

/**
 * Window which gets created when a station is "entered" or inspected.
 */
public class StationInspector extends OsbWindow {

    private li.yuri.openspacebox.ingame.ui.station.ItemsTable itemsTable;

    private li.yuri.openspacebox.ingame.object.Station station;
    private Optional<li.yuri.openspacebox.ingame.object.TradeEntity> tradePartner = Optional.empty();

    public StationInspector(OsbGui stage) {
        super("How about loadStation()?", stage);
        setModal(true);

        columnDefaults(0).expandX().fillX().align(Align.topLeft);
        itemsTable = createItemsTable();
        add(itemsTable);

    }

    /**
     * Displays the station's items without enabling trading with another object.
     *
     * @param station The station whose items should be displayed.
     */
    public void loadStation(li.yuri.openspacebox.ingame.object.Station station) {
        setStation(station);
        itemsTable.fillWith(this.station);
    }

    /**
     * Displays the station's items and enables trading.
     *
     * @param station      The station whose items should be displayed
     * @param tradePartner The object which can trade with the station using this dialog.
     */
    public void loadStation(li.yuri.openspacebox.ingame.object.Station station, TradeEntity tradePartner) {
        setStation(station);
        this.tradePartner = Optional.of(tradePartner);

        itemsTable.fillWith(this.station, this.tradePartner.get());
    }

    /**
     * Sets the reference to the station and updates the window's title.
     */
    private void setStation(Station station) {
        this.station = station;
        updateTitle();
    }

    private void updateTitle() {
        getTitleLabel().setText(String.valueOf(station.hashCode()));
    }

    private static li.yuri.openspacebox.ingame.ui.station.ItemsTable createItemsTable() {
        return new ItemsTable();
    }


}
