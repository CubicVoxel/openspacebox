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

package li.yuri.openspacebox.assetmanagement.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.definition.universe.UniverseDefinition;
import lombok.Getter;

import java.util.List;

/**
 * Collection of all Universes (or rather, the universe) which get loaded when launching the game.
 * <p>
 * Use this when getting a UniverseDefinition from the AssetManager.
 */
public enum UniverseAsset implements ManagedAsset<UniverseDefinition> {
    UNIVERSE(new AssetDescriptor<>("definition/Universe.xml", UniverseDefinition.class));

    @Getter private final AssetDescriptor<UniverseDefinition> assetDescriptor;

    UniverseAsset(AssetDescriptor<UniverseDefinition> assetDescriptor) {
        this.assetDescriptor = assetDescriptor;
    }

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(UniverseAsset::getAssetDescriptor).collect(Collectors.toList());
    }

}
