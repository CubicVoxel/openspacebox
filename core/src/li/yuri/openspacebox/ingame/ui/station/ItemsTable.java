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

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import java8.util.Optional;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.util.DefinitionFinder;
import li.yuri.openspacebox.definition.type.ItemType;
import li.yuri.openspacebox.ingame.object.TradeEntity;
import li.yuri.openspacebox.util.widget.SmallLabel;

import java.util.HashSet;

import static java8.util.stream.StreamSupport.stream;

/**
 * Table which displays items and their amount of one or two objects implementing {@link
 * li.yuri.openspacebox.ingame.object.TradeEntity}.
 */
public class ItemsTable extends Table {

    private static final String AMOUNT_SIGN = "x";

    private final DefinitionFinder definitionFinder = OpenSpaceBox.createDefinitionFinder();

    private Optional<li.yuri.openspacebox.ingame.object.TradeEntity> leftTrader = Optional.empty();
    private Optional<li.yuri.openspacebox.ingame.object.TradeEntity> rightTrader = Optional.empty();


    public ItemsTable() {
        super();
        columnDefaults(0).right();
        columnDefaults(1).center().expandX();
        columnDefaults(2).right();
    }

    /**
     * Fills with the object's amounts the in the right column.
     */
    public void fillWith(li.yuri.openspacebox.ingame.object.TradeEntity object) {
        fillWith(Optional.empty(), Optional.of(object));
    }

    /**
     * Fills with the leftTrader's amounts in the left column and the rightTrader's amounts in the right column.
     */
    public void fillWith(li.yuri.openspacebox.ingame.object.TradeEntity leftTrader, li.yuri.openspacebox.ingame.object.TradeEntity rightTrader) {
        fillWith(Optional.of(leftTrader), Optional.of(rightTrader));
    }

    private void fillWith(Optional<li.yuri.openspacebox.ingame.object.TradeEntity> leftTrader, Optional<TradeEntity> rightTrader) {
        this.leftTrader = leftTrader;
        this.rightTrader = rightTrader;

        HashSet<String> allItems = new HashSet<>();
        leftTrader.ifPresent(x -> allItems.addAll(x.getCargoHold().getContent().keySet()));
        rightTrader.ifPresent(x -> allItems.addAll(x.getCargoHold().getContent().keySet()));

        // call addItem for each item by checking the existence of each trader and returning either their amount or
        // an empty Optional.
        stream(allItems).forEach(item -> addItem(
                item,
                leftTrader.isPresent() ? Optional.of(leftTrader.get().getCargoHold().getAmountOf(item))
                        : Optional.empty(),
                rightTrader.isPresent() ? Optional.of(rightTrader.get().getCargoHold().getAmountOf(item))
                        : Optional.empty()
        ));
    }


    /**
     * Adds a row with the leftAmount (if present), the item's title, and the rightAmount.
     */
    public void addItem(String itemId, Optional<Integer> leftAmount, Optional<Integer> rightAmount) {
        ItemType item = definitionFinder.findItem(itemId).get();

        li.yuri.openspacebox.util.widget.SmallLabel leftAmountLabel = new li.yuri.openspacebox.util.widget.SmallLabel(leftAmount.isPresent() ?
                (leftAmount.get() + " " + AMOUNT_SIGN) : (""));
        leftAmountLabel.setAlignment(Align.right);
        add(leftAmountLabel);


        li.yuri.openspacebox.util.widget.SmallLabel itemLabel = new li.yuri.openspacebox.util.widget.SmallLabel(item.getDisplayName());
        itemLabel.setAlignment(Align.center);
        add(itemLabel);

        li.yuri.openspacebox.util.widget.SmallLabel rightAmountLabel = new SmallLabel(rightAmount.isPresent() ?
                (AMOUNT_SIGN + " " + rightAmount.get()) : (""));
        rightAmountLabel.setAlignment(Align.right);
        add(rightAmountLabel);

        row();
    }


}
