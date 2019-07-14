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

import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.ArrayList;

class MenuButton extends Group {

    private static final String STYLE = "rd-menu-button";
    private static final String STYLE_SELECTED = "rd-menu-button-selected";


    private final li.yuri.workspacefx.application.ViewType viewType;
    private final ArrayList<SelectedListener> selectedListeners = new ArrayList<>();
    private final Button button;

    public MenuButton(li.yuri.workspacefx.application.ViewType viewType) {
        this.viewType = viewType;

        button = new Button();
        button.getStyleClass().add(STYLE);
        button.setText(viewType.getTitle());
        button.setOnAction(event -> fireSelectedListeners());
        button.setRotate(-90);

        getChildren().setAll(button);
    }

    public void setSelectedForViewType(li.yuri.workspacefx.application.ViewType viewType) {
        setSelected(viewType.equals(this.viewType));
    }

    public void setSelected(boolean selected) {
        if (selected && !button.getStyleClass().contains(STYLE_SELECTED))
            button.getStyleClass().add(STYLE_SELECTED);
        else
            button.getStyleClass().remove(STYLE_SELECTED);
    }

    public void addSelectedListener(SelectedListener listener) {
        selectedListeners.add(listener);
    }

    protected void fireSelectedListeners() {
        selectedListeners.forEach(listener -> listener.onSelected(viewType));
    }

    public li.yuri.workspacefx.application.ViewType getViewType() {
        return viewType;
    }

    public interface SelectedListener {
        void onSelected(ViewType viewType);
    }


}
