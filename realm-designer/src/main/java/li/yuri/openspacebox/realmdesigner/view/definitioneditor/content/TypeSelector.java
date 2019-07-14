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
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.definition.type.Type;
import li.yuri.workspacefx.style.Defaults;
import li.yuri.workspacefx.style.Styles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeSelector<T extends Type> extends VBox {

    private List<SelectionListener<T>> selectionListeners = new ArrayList<>();

    private li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.TypeListView<T> typeListView;
    private Button addButton;

    public TypeSelector(NewItemCreator newItemCreator) {
        typeListView = new TypeListView<>();
        getChildren().add(typeListView);
        setVgrow(typeListView, Priority.SOMETIMES);

        typeListView.getSelectionModel().getSelectedItems()
                .addListener((ListChangeListener<T>) c -> fireSelectionListeners());

        addButton = new Button("Add");
        addButton.getStyleClass().add(Styles.SMALL_BUTTON);
        addButton.setOnAction(event -> newItemCreator.createNewItem());
        getChildren().add(addButton);
        setVgrow(addButton, Priority.NEVER);
        setMargin(addButton, new Insets(Defaults.getInstance().spacingDefault, 0, 0, 0));
        addButton.setAlignment(Pos.TOP_LEFT);
    }


    public void setItems(Collection<T> items) {
        typeListView.getItems().setAll(items);
    }

    public void clearSelection() {
        typeListView.getSelectionModel().clearSelection();
    }

    private void fireSelectionListeners() {
        Optional<T> selected = Optional.ofNullable(typeListView.getSelectionModel().getSelectedItem());
        selectionListeners.forEach(x -> x.selected(selected));
    }

    public void addSelectionListener(SelectionListener<T> selectionListener) {
        selectionListeners.add(selectionListener);
    }

    public interface SelectionListener<T> {
        void selected(Optional<T> selected);
    }

    public interface NewItemCreator {
        void createNewItem();
    }

}
