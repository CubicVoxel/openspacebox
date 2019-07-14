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

package li.yuri.openspacebox;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import li.yuri.openspacebox.assetmanagement.LoadingScreen;
import li.yuri.openspacebox.assetmanagement.OsbAssetManager;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.assetmanagement.util.DefinitionFinder;
import li.yuri.openspacebox.context.OsbContext;
import lombok.Getter;

import java.util.Locale;

/**
 * Main
 */
public class OpenSpaceBox extends Game {

    @Getter private static OpenSpaceBox instance;

    @Getter private SpriteBatch objectBatch;
    private li.yuri.openspacebox.assetmanagement.OsbAssetManager assetManager;
    private OsbContext context;


    public OpenSpaceBox(OsbContext context) {
        instance = this;
        this.context = context;
    }

    @Override
    public void create() {
        objectBatch = new SpriteBatch();
        assetManager = new OsbAssetManager();
        li.yuri.openspacebox.assetmanagement.asset.ResourceBundles.initialize(Locale.getDefault());

        setScreen(
                new LoadingScreen(
                        assetManager,
                        this::onLoadingFinished,
                        li.yuri.openspacebox.assetmanagement.asset.FontAsset.valuesAsAssets(),
                        li.yuri.openspacebox.assetmanagement.asset.TypeDefinitionsAsset.valuesAsAssets(),
                        li.yuri.openspacebox.assetmanagement.asset.UniverseAsset.valuesAsAssets(),
                        li.yuri.openspacebox.assetmanagement.asset.SectorTemplateAsset.valuesAsAssets(),
                        li.yuri.openspacebox.assetmanagement.asset.TextureAtlasAsset.valuesAsAssets(),
                        TextureAsset.valuesAsAssets()
                )
        );
    }

    private void onLoadingFinished() {
        assetManager.finishLoading();
        setScreen(context.getPostLoadingScreen());
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        getScreen().dispose();
        objectBatch.dispose();
        assetManager.dispose();
    }

    public synchronized static <T> T getAsset(li.yuri.openspacebox.assetmanagement.asset.ManagedAsset<T> asset) {
        return getInstance().assetManager.get(asset.getAssetDescriptor());
    }

    public static Sprite createSprite(li.yuri.openspacebox.assetmanagement.asset.ManagedAtlas atlas) {
        return getAsset(atlas.getAtlas()).createSprite(atlas.getName());
    }

    public static li.yuri.openspacebox.assetmanagement.util.DefinitionFinder createDefinitionFinder() {
        return new DefinitionFinder(getInstance().assetManager);
    }


}
