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

import java8.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import li.yuri.openspacebox.definition.type.ItemType;
import li.yuri.openspacebox.definition.type.ShipType;
import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.definition.type.Type;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.itemtype.ItemTypeEditor;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.shiptype.ShipTypeEditor;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.stationtype.StationTypeEditor;
import li.yuri.workspacefx.style.Defaults;

public class TypeEditContainer extends StackPane implements TypeSelectionPanel.TypeSelectedListener {

    private static final String STYLE = "type-edit-container";
    private final li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.CurrentFile currentFile;
    private Optional<li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.TypeEditor> currentTypeEditor = Optional.empty();

    public TypeEditContainer(CurrentFile currentFile) {
        this.currentFile = currentFile;

        getStyleClass().add(STYLE);
    }

    @Override
    public void onTypeSelected(Type selected) {
        if (storeCurrentValue()) {
            setTypeEditorForAndLoad(selected);
        }
    }

    /**
     * Stores the currentValue in the currentFile.
     *
     * @return Could the value be stored or were there validation errors?
     */
    public boolean storeCurrentValue() {
        if (currentFile.getOpenedTypeDefinitions().isPresent() && currentTypeEditor.isPresent()) {
            Type currentValue = currentTypeEditor.get().getValue();
            // TODO: validate
            return true;
        } else {
            // Storing is successful because empty items don't need to get stored (~‾▿‾)~
            return true;
        }
    }

    private void setTypeEditorForAndLoad(Type selected) {
        // instanceof... (⌐■_■) This code is basically a masterpiece.
        if (selected instanceof ShipType) {
            ShipTypeEditor shipTypeEditor = new ShipTypeEditor(this::deleteItem);
            setTypeEditor(shipTypeEditor);
            shipTypeEditor.setValue((ShipType) selected);
        } else if (selected instanceof StationType) {
            StationTypeEditor stationTypeEditor = new StationTypeEditor(this::deleteItem);
            setTypeEditor(stationTypeEditor);
            stationTypeEditor.setValue((StationType) selected);
        } else if (selected instanceof ItemType) {
            ItemTypeEditor itemTypeEditor = new ItemTypeEditor(this::deleteItem);
            setTypeEditor(itemTypeEditor);
            itemTypeEditor.setValue((ItemType) selected);
        } else {
            setTypeEditor(null);
        }
    }

    private void setTypeEditor(TypeEditor typeEditor) {
        if (typeEditor != null) {
            getChildren().setAll(typeEditor);
            StackPane.setMargin(typeEditor, new Insets(Defaults.getInstance().spacing2x));
            currentTypeEditor = Optional.of(typeEditor);
        } else {
            getChildren().clear();
            currentTypeEditor = Optional.empty();
        }
    }

    private void deleteItem(Type item) {
        // Item is "removed". This just unloads the current editor without re-storing the current value.
        setTypeEditorForAndLoad(null);
        currentFile.removeType(item);
        currentFile.triggerTypeDefinitionsLoadedListeners();
    }
}
