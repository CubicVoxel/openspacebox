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
import li.yuri.openspacebox.definition.type.Type;
import li.yuri.openspacebox.definition.type.TypeDefinitions;
import li.yuri.openspacebox.realmdesigner.view.definitioneditor.menu.FileMenu;
import lombok.Getter;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CurrentFile implements FileMenu.FileMenuOwner {
    private List<TypeDefinitionsLoadedListener> typeDefinitionsLoadedListeners = new ArrayList<>();

    private Optional<File> openedFile = Optional.empty();
    @Getter private Optional<TypeDefinitions> openedTypeDefinitions = Optional.of(new TypeDefinitions());
    @Getter private List<Type> removedTypes = new ArrayList<>();

    @Override
    public void onOpen(File file) {
        try {
            Serializer serializer = new Persister();
            openedTypeDefinitions = Optional.of(serializer.read(TypeDefinitions.class, file));
            openedFile = Optional.of(file);

            triggerTypeDefinitionsLoadedListeners();
        } catch (Exception e) {
            // TODO: Show MessageBox
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        saveAs(openedFile.get());
    }

    @Override
    public void saveAs(File file) {
        try {
            TypeDefinitions typeDefinitions = openedTypeDefinitions.get();
            typeDefinitions.setItemTypes(typeDefinitions.getItemTypes().stream().filter(x -> !removedTypes.contains
                    (x)).collect(Collectors.toList()));
            typeDefinitions.setShipTypes(typeDefinitions.getShipTypes().stream().filter(x -> !removedTypes.contains
                    (x)).collect(Collectors.toList()));
            typeDefinitions.setStationTypes(typeDefinitions.getStationTypes().stream().filter(x -> !removedTypes.contains
                    (x)).collect(Collectors.toList()));

            Serializer serializer = new Persister();
            serializer.write(typeDefinitions, openedFile.get());
            openedFile = Optional.of(file);
        } catch (Exception e) {
            // TODO: Show MessageBox
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveOpensSaveAs() {
        return openedFile.isPresent();
    }

    public void removeType(Type type) {
        removedTypes.add(type);
    }

    public void triggerTypeDefinitionsLoadedListeners() {
        typeDefinitionsLoadedListeners.forEach(x -> x.onTypeDefinitionsLoaded(openedTypeDefinitions.orElse(new
                TypeDefinitions())));
    }

    public void addTypeDefinitionsLoadedListener(TypeDefinitionsLoadedListener listener) {
        typeDefinitionsLoadedListeners.add(listener);
    }

    public interface TypeDefinitionsLoadedListener {
        void onTypeDefinitionsLoaded(TypeDefinitions typeDefinitions);
    }

}
