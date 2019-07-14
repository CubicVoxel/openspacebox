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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Navigator {

    private final li.yuri.workspacefx.application.ViewContainer viewContainer;

    private ArrayList<ViewChangeListener> viewChangeListeners = new ArrayList<>();
    private Map<ViewType, View> viewInstances = new HashMap<>();

    public Navigator(li.yuri.workspacefx.application.ViewContainer viewContainer) {
        this.viewContainer = viewContainer;
    }

    public void navigateTo(ViewType viewType) {
        if (!viewInstances.containsKey(viewType))
            viewInstances.put(viewType, viewType.getInstanceSupplier().get());
        viewContainer.setView(viewInstances.get(viewType));
        fireViewChangeListeners(viewType);
    }

    public void addViewChangeListener(ViewChangeListener listener) {
        viewChangeListeners.add(listener);
    }

    private void fireViewChangeListeners(ViewType viewType) {
        viewChangeListeners.forEach(listener -> listener.onViewChange(viewType));
    }

    public interface ViewChangeListener {
        void onViewChange(ViewType viewType);
    }
}
