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

package li.yuri.openspacebox.ingame.object;

import li.yuri.openspacebox.definition.type.ItemType;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.HashMap;

public class CargoHold {
    // TODO: 11.12.16 Occasionally remove entries with a value of zero
    @Getter private HashMap<String, Integer> content = new HashMap<>();

    private int occupiedSpace = 0;
    @Getter @Setter private int size;

    public CargoHold(int size) {
        this.size = size;
    }

    /**
     * Withdraws the item from this CargoHold and adds them to target.
     */
    public void transferTo(CargoHold target, li.yuri.openspacebox.definition.type.ItemType item, int amount) {
        val withdrawnAmount = withdrawItem(item, amount);
        val addedAmount = target.addItem(item, amount);
        // if not all withdrawn itemTypes could be added, add them back
        addItem(item, withdrawnAmount - addedAmount);
    }


    /**
     * Withdraws the item from target and adds them to this CargoHold (actually calls transferTo at the target).
     */
    public void transferFrom(CargoHold supplier, li.yuri.openspacebox.definition.type.ItemType item, int amount) {
        supplier.transferTo(this, item, amount);
    }


    /**
     * Adds the item to the cargo
     *
     * @return the amount which was actually added.
     */
    public int addItem(li.yuri.openspacebox.definition.type.ItemType item, int amount) {
        // Register item
        if (!content.containsKey(item.getId()))
            content.put(item.getId(), 0);

        // Calculate how much can actually be added
        val remainingSpace = getSize() - occupiedSpace;
        val addableAmount = (int) Math.floor(remainingSpace / (double) item.getSize());

        // Don't add too much
        val addedAmount = Math.min(amount, addableAmount);
        // Update the item's value by adding the addedAmount to the previous value.
        content.put(item.getId(), content.get(item.getId()) + addedAmount);
        occupiedSpace += addedAmount * item.getSize();

        return addedAmount;
    }

    /**
     * Removes the item from the cargo.
     *
     * @return the amount which was actually removed.
     */
    public int withdrawItem(li.yuri.openspacebox.definition.type.ItemType item, int amount) {
        // Default: If the item is not available, nothing is withdrawn.
        int withdrawnAmount = 0;

        if (content.containsKey(item.getId())) {
            int amountInCargo = content.get(item.getId());
            amountInCargo -= amount;

            if (amountInCargo < 0) {
                // Example: 20 itemTypes shall be withdrawn, but there are only 10 available.
                // amountInCargoHold now is -10, so all remaining itemTypes (10) are withdrawn which equals to 20 (desired amount) + -10 ("remaining")
                withdrawnAmount = amount + amountInCargo;
                amountInCargo = 0;
            } else {
                withdrawnAmount = amount;
            }

            // Update content and oocupiedSpace
            content.put(item.getId(), amountInCargo);
            occupiedSpace -= item.getSize() * withdrawnAmount;
        }
        return withdrawnAmount;
    }

    public int getAmountOf(String itemId) {
        if (content.containsKey(itemId))
            return content.get(itemId);
        else
            return 0;
    }

    public int getSpaceOccupiedBy(ItemType item) {
        return getAmountOf(item.getId()) * item.getSize();
    }
}
