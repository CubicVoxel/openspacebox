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

package li.yuri.openspacebox.util.widget;

import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.util.LabelStyleBuilder;

/**
 * Label with smaller font
 */
public class SmallLabel extends OsbLabel {
    public SmallLabel(String text) {
        super(
                text,
                new LabelStyleBuilder().font(OpenSpaceBox.getAsset(FontAsset.SMALL_LABEL)).build()
        );
    }
}
