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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.definition.type.*;
import li.yuri.workspacefx.layout.Expandable;
import li.yuri.workspacefx.style.Defaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TypeSelectionPanel extends VBox {
    private final li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.CurrentFile currentFile;
    private TypeSelector<ShipType> shipTypeSelector;
    private TypeSelector<StationType> stationTypeSelector;
    private TypeSelector<ItemType> itemTypeSelector;
    private List<TypeSelectedListener> typeSelectedListeners = new ArrayList<>();

    public TypeSelectionPanel(CurrentFile currentFile) {
        this.currentFile = currentFile;
        setSpacing(Defaults.getInstance().spacing2x);

        shipTypeSelector = new TypeSelector<>(() -> fillWithDefaultsAndAddToCurrentFile(new ShipType()));
        shipTypeSelector.addSelectionListener(type -> {
            if (type.isPresent()) {
                stationTypeSelector.clearSelection();
                itemTypeSelector.clearSelection();
                typeSelectedListeners.forEach(listener -> listener.onTypeSelected(type.get()));
            }
        });
        Expandable shipTypeListContainer = new Expandable("Ship Types", shipTypeSelector);
        getChildren().add(shipTypeListContainer);
        VBox.setVgrow(shipTypeListContainer, Priority.NEVER);
        shipTypeListContainer.addExpandedListener(expanded ->
                VBox.setVgrow(shipTypeListContainer, expanded ? Priority.ALWAYS : Priority.NEVER));

        stationTypeSelector = new TypeSelector<>(() -> fillWithDefaultsAndAddToCurrentFile(new StationType()));
        stationTypeSelector.addSelectionListener(type -> {
            if (type.isPresent()) {
                shipTypeSelector.clearSelection();
                itemTypeSelector.clearSelection();
                typeSelectedListeners.forEach(listener -> listener.onTypeSelected(type.get()));
            }
        });
        Expandable stationTypeListContainer = new Expandable("Station Types", stationTypeSelector);
        getChildren().add(stationTypeListContainer);
        VBox.setVgrow(stationTypeListContainer, Priority.NEVER);
        stationTypeListContainer.addExpandedListener(expanded ->
                VBox.setVgrow(stationTypeListContainer, expanded ? Priority.ALWAYS : Priority.NEVER));

        itemTypeSelector = new TypeSelector<>(() -> fillWithDefaultsAndAddToCurrentFile(new ItemType()));
        itemTypeSelector.addSelectionListener(type -> {
            if (type.isPresent()) {
                shipTypeSelector.clearSelection();
                stationTypeSelector.clearSelection();
                typeSelectedListeners.forEach(listener -> listener.onTypeSelected(type.get()));
            }
        });
        Expandable itemTypeListContainer = new Expandable("Item Types", itemTypeSelector);
        getChildren().add(itemTypeListContainer);
        VBox.setVgrow(itemTypeListContainer, Priority.NEVER);
        itemTypeListContainer.addExpandedListener(expanded ->
                VBox.setVgrow(itemTypeListContainer, expanded ? Priority.ALWAYS : Priority.NEVER));

    }

    public void setTypeDefinitions(TypeDefinitions typeDefinitions) {
        shipTypeSelector.setItems(typeDefinitions.getShipTypes().stream()
                .filter(x -> !currentFile.getRemovedTypes().contains(x)).collect(Collectors.toList()));
        stationTypeSelector.setItems(typeDefinitions.getStationTypes().stream()
                .filter(x -> !currentFile.getRemovedTypes().contains(x)).collect(Collectors.toList()));
        itemTypeSelector.setItems(typeDefinitions.getItemTypes().stream()
                .filter(x -> !currentFile.getRemovedTypes().contains(x)).collect(Collectors.toList()));
    }

    public void addTypeSelectedListener(TypeSelectedListener listener) {
        typeSelectedListeners.add(listener);
    }

    public void fillWithDefaultsAndAddToCurrentFile(Type newInstance) {
        if (currentFile.getOpenedTypeDefinitions().isPresent()) {
            newInstance.setId(UUID.randomUUID().toString());
            newInstance.setDisplayName("New Type");
            currentFile.getOpenedTypeDefinitions().get().add(newInstance);
            currentFile.triggerTypeDefinitionsLoadedListeners();
        }
    }

    public interface TypeSelectedListener {
        void onTypeSelected(Type selected);
    }
}
