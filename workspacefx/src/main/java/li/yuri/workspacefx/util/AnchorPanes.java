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

package li.yuri.workspacefx.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Util for {@link javafx.scene.layout.AnchorPane}s.
 */
public class AnchorPanes {
    /**
     * Sets the given child of an {@link AnchorPane} to fill its parent.
     *
     * @param child a child of any {@link AnchorPane}.
     */
    public static void fillParent(Node child) {
        setAllAnchors(child, 0.0);
    }

    public static void setAllAnchors(Node child, double value) {
        AnchorPane.setLeftAnchor(child, value);
        AnchorPane.setTopAnchor(child, value);
        AnchorPane.setRightAnchor(child, value);
        AnchorPane.setBottomAnchor(child, value);
    }
}
