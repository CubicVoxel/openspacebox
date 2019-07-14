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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import java8.util.J8Arrays;
import java8.util.Optional;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.util.OsbScreen;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbLabel;
import lombok.val;

import java.util.List;
import java.util.Locale;

import static java8.util.stream.StreamSupport.stream;

public class LoadingScreen extends OsbScreen {

    private final OsbAssetManager assetManager;
    private final FinishedListener finishedListener;

    private Optional<LoadingGui> loadingGui = Optional.empty();


    public LoadingScreen(OsbAssetManager assetManager, FinishedListener finishedListener) {
        this.assetManager = assetManager;
        this.finishedListener = finishedListener;
    }

    @SafeVarargs
    public LoadingScreen(OsbAssetManager assetManager, FinishedListener finishedListener,
                         List<AssetDescriptor>... assets) {
        this(assetManager, finishedListener);
        // Map List<AssetDescriptor>[] to List<AssetDescriptor>
        List<AssetDescriptor> allAssets = J8Arrays.stream(assets).flatMap(StreamSupport::stream)
                .collect(Collectors.toList());
        loadAll(allAssets);
    }

    public void loadAll(List<AssetDescriptor> assets) {
        stream(assets).forEach(assetManager::load);
    }

    private void initGui() {
        loadingGui = Optional.of(new LoadingGui(assetManager::getLoadedAssets, assetManager::getTotalAssets));
    }

    @Override
    protected void beforeDraw() {
        super.beforeDraw();
        if (assetManager.areLoaded(FontAsset.values()))
            initGui();

        if (assetManager.update())
            finishedListener.loadingFinished();
    }

    @Override
    protected void drawObjectBatch(SpriteBatch batch) {
        // No objects to draw
    }

    @Override
    protected void drawGui() {
        loadingGui.ifPresent(gui -> {
            gui.act();
            gui.draw();
        });
    }

    @Override
    public void dispose() {
        loadingGui.ifPresent(LoadingGui::dispose);
    }

    public interface FinishedListener {
        void loadingFinished();
    }

    private static class LoadingGui extends OsbGui {
        private final I18NBundle i18NBundle = ResourceBundles.getInstance().getLoadingGui();

        private final LoadedAssetsSupplier loadedAssetsSupplier;
        private final TotalAssetsSupplier totalAssetsSupplier;
        private li.yuri.openspacebox.util.widget.OsbLabel loadingLabel;

        public LoadingGui(LoadedAssetsSupplier loadedAssetsSupplier, TotalAssetsSupplier totalAssetsSupplier) {
            this.loadedAssetsSupplier = loadedAssetsSupplier;
            this.totalAssetsSupplier = totalAssetsSupplier;

            loadingLabel = new OsbLabel("");
            loadingLabel.setFillParent(true);
            loadingLabel.setAlignment(Align.center, Align.center);
            addWidget(loadingLabel);
        }

        @Override
        public void act() {
            super.act();
            updateLoadingLabel();
        }

        private void updateLoadingLabel() {
            val loadingProgress = MathUtils.round(
                    (loadedAssetsSupplier.getLoadedAssets() / totalAssetsSupplier.getTotalAssets()) * 100);


            String loadingProgressAsText = loadingProgress + "%" +
                    "   (" +
                    String.format(Locale.getDefault(), "%03d", (int) loadedAssetsSupplier.getLoadedAssets()) +
                    "/" +
                    String.format(Locale.getDefault(), "%03d", (int) totalAssetsSupplier.getTotalAssets()) +
                    ")";

            String loadingText = i18NBundle.format("loading", loadingProgressAsText);

            loadingLabel.setText(loadingText);
        }

        public interface LoadedAssetsSupplier {
            float getLoadedAssets();
        }

        public interface TotalAssetsSupplier {
            float getTotalAssets();
        }
    }
}
