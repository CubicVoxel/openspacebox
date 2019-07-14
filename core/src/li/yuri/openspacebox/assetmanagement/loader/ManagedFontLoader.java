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

package li.yuri.openspacebox.assetmanagement.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import lombok.Getter;
import lombok.val;

/**
 * Easier loader for load-time generated BitmapFonts.
 */
public class ManagedFontLoader extends SynchronousAssetLoader<BitmapFont, ManagedFontLoader.ManagedFontParameter> {

    public ManagedFontLoader(FontAssetFileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }

    @Override
    public BitmapFont load(AssetManager assetManager, String fileName, FileHandle file, ManagedFontParameter parameter) {
        val generator = new FreeTypeFontGenerator(file);
        val font = generator.generateFont(parameter.getFreeTypeFontParameter());
        generator.dispose();
        return font;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ManagedFontParameter parameter) {
        return new Array<>();
    }

    public static class ManagedFontParameter extends AssetLoaderParameters<BitmapFont> {
        @Getter private final FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;

        public ManagedFontParameter(int size, Color color, String characters) {
            this.freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            freeTypeFontParameter.size = size;
            freeTypeFontParameter.color = color;
            freeTypeFontParameter.characters = characters;
        }

        public ManagedFontParameter(
                int size,
                Color color,
                Color borderColor,
                float borderWidth,
                String characters
        ) {
            this(size, color, characters);
            freeTypeFontParameter.borderColor = borderColor;
            freeTypeFontParameter.borderWidth = borderWidth;
        }
    }

    /**
     * The AssetManager uses solely the filename as key, but the project relies on multiple generated fonts from the
     * same file. This means, if you tried to get a font from which's file multiple fonts have been generated, you would
     * probably get the wrong one.
     * <p>
     * As a workaround, the FontAssets append their name/key in the filename. Now, the AssetManager is able to
     * distinguish the FontAssets, but this FileHandleResolver still needs to remove the font's key in order to load the
     * file.
     */
    public static class FontAssetFileHandleResolver extends InternalFileHandleResolver {
        @Override
        public FileHandle resolve(String fileName) {
            String actualFileName = fileName.substring(0, fileName.lastIndexOf(FontAsset.NAME_SEPARATOR));
            return super.resolve(actualFileName);
        }
    }
}
