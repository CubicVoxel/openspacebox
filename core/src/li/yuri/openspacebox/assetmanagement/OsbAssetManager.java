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

package li.yuri.openspacebox.assetmanagement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import java8.util.J8Arrays;
import li.yuri.openspacebox.assetmanagement.asset.ManagedAsset;
import li.yuri.openspacebox.assetmanagement.loader.ManagedFontLoader;
import li.yuri.openspacebox.assetmanagement.loader.SerializedXmlLoader;
import li.yuri.openspacebox.definition.template.SectorTemplate;
import li.yuri.openspacebox.definition.type.TypeDefinitions;
import li.yuri.openspacebox.definition.universe.UniverseDefinition;

public class OsbAssetManager extends AssetManager {
    public OsbAssetManager() {
        setLoader(SectorTemplate.class, new li.yuri.openspacebox.assetmanagement.loader.SerializedXmlLoader<>(SectorTemplate.class, new InternalFileHandleResolver()));
        setLoader(UniverseDefinition.class, new li.yuri.openspacebox.assetmanagement.loader.SerializedXmlLoader<>(UniverseDefinition.class, new InternalFileHandleResolver()));
        setLoader(TypeDefinitions.class, new SerializedXmlLoader<>(TypeDefinitions.class, new InternalFileHandleResolver()));
        setLoader(BitmapFont.class, new li.yuri.openspacebox.assetmanagement.loader.ManagedFontLoader(new ManagedFontLoader.FontAssetFileHandleResolver()));
        Texture.setAssetManager(this);
    }

    public int getTotalAssets() {
        return getLoadedAssets() + getQueuedAssets();
    }

    public boolean areLoaded(ManagedAsset... assets) {
        return J8Arrays.stream(assets).allMatch(x -> isLoaded(x.getAssetDescriptor().fileName));
    }
}
