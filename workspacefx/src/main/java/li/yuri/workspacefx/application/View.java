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

package li.yuri.workspacefx.application;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import li.yuri.workspacefx.style.Defaults;
import li.yuri.workspacefx.util.AnchorPanes;

/**
 * Superclass for views which are typically accessed via a {@link Menu} and contained by a {@link ViewContainer}.
 */
public abstract class View extends StackPane {

    public View() {
        AnchorPanes.fillParent(this);
    }

    protected void setContent(Node node) {
        setContentWithoutMargin(node);
        StackPane.setMargin(node, new Insets(Defaults.getInstance().spacingDefault));
    }

    protected void setContentWithoutMargin(Node node) {
        getChildren().setAll(node);
    }

    public abstract void onLeave();
}
