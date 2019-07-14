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

package li.yuri.openspacebox.assetmanagement.util;

import com.badlogic.gdx.utils.Array;
import java8.util.Optional;
import java8.util.stream.StreamSupport;
import li.yuri.openspacebox.assetmanagement.OsbAssetManager;
import li.yuri.openspacebox.definition.template.SectorTemplate;
import li.yuri.openspacebox.definition.type.ItemType;
import li.yuri.openspacebox.definition.type.ShipType;
import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.definition.type.TypeDefinitions;
import li.yuri.openspacebox.definition.universe.UniverseDefinition;

import java.util.Arrays;
import java.util.List;

import static java8.util.stream.StreamSupport.parallelStream;


/**
 * Helper for finding definitions in the given AssetManager.
 */
public class DefinitionFinder {

    private final OsbAssetManager osbAssetManager;
    // libgdx's arrays don't need to get converted multiple times.
    private Optional<List<TypeDefinitions>> allTypeDefinitions = Optional.empty();
    private Optional<List<UniverseDefinition>> allUniverses = Optional.empty();
    private Optional<List<SectorTemplate>> allSectorTemplates = Optional.empty();


    public DefinitionFinder(OsbAssetManager osbAssetManager) {
        this.osbAssetManager = osbAssetManager;
    }

    private List<TypeDefinitions> getTypeDefinitions() {
        if (!allTypeDefinitions.isPresent())
            allTypeDefinitions = Optional.of(Arrays.asList(osbAssetManager.getAll(TypeDefinitions.class, new Array<>()).toArray()));
        return allTypeDefinitions.get();
    }

    private List<UniverseDefinition> getUniverses() {
        if (!allUniverses.isPresent())
            allUniverses = Optional.of(Arrays.asList(osbAssetManager.getAll(UniverseDefinition.class, new Array<>()).toArray()));
        return allUniverses.get();
    }

    private List<SectorTemplate> getSectorTemplates() {
        if (!allSectorTemplates.isPresent())
            allSectorTemplates = Optional.of(Arrays.asList(osbAssetManager.getAll(SectorTemplate.class, new Array<>()
            ).toArray()));
        return allSectorTemplates.get();
    }

    public Optional<ShipType> findShipType(String id) {
        return parallelStream(getTypeDefinitions()).map(TypeDefinitions::getShipTypes).flatMap(StreamSupport::stream).filter(x -> x.getId().equals(id)).findAny();
    }


    public Optional<StationType> findStationType(String id) {
        return parallelStream(getTypeDefinitions()).map(TypeDefinitions::getStationTypes).flatMap(StreamSupport::stream).filter(x -> x.getId().equals(id)).findAny();
    }

    public Optional<ItemType> findItem(String id) {
        return parallelStream(getTypeDefinitions()).map(TypeDefinitions::getItemTypes).flatMap(StreamSupport::stream).filter(x -> x.getId().equals(id)).findAny();
    }

    public Optional<SectorTemplate> findSectorTemplate(String id) {
        return parallelStream(getSectorTemplates()).filter(x -> x.getId().equals(id)).findAny();
    }

    public Optional<UniverseDefinition> findUniverse(String id) {
        return parallelStream(getUniverses()).filter(x -> x.getId().equals(id)).findAny();
    }
}
