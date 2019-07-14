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
import li.yuri.openspacebox.definition.type.TypeDefinitions;
import lombok.Getter;

import java.util.List;

public enum TypeDefinitionsAsset implements li.yuri.openspacebox.assetmanagement.asset.ManagedAsset<TypeDefinitions> {
    TYPE_DEFINITIONS(new AssetDescriptor<>("definition/TypeDefinitions.xml", TypeDefinitions.class));

    @Getter private final AssetDescriptor<TypeDefinitions> assetDescriptor;

    TypeDefinitionsAsset(AssetDescriptor<TypeDefinitions> assetDescriptor) {
        this.assetDescriptor = assetDescriptor;
    }

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(ManagedAsset::getAssetDescriptor).collect(Collectors.toList());
    }


}
