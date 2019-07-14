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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public enum TextureAtlasAsset implements li.yuri.openspacebox.assetmanagement.asset.ManagedAsset<TextureAtlas> {
    SPRITE(new AssetDescriptor<>("sprite/sprite.atlas", TextureAtlas.class));

    @Getter private AssetDescriptor<TextureAtlas> assetDescriptor;

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(ManagedAsset::getAssetDescriptor).collect(Collectors.toList());
    }
}
