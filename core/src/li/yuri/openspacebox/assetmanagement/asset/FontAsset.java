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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.assetmanagement.loader.ManagedFontLoader;
import lombok.Getter;

import java.util.List;

public enum FontAsset implements ManagedAsset<BitmapFont> {
    WINDOW_TITLE(
            "font/JosefinSans-Regular.ttf",
            32,
            Color.WHITE
    ),
    LABEL(
            "font/JosefinSans-SemiBold.ttf",
            32,
            Color.WHITE,
            Color.BLACK,
            1.0f
    ),
    SMALL_LABEL(
            "font/JosefinSans-Light.ttf",
            32,
            Color.BLACK
    ),
    SECTOR_BUTTON_LABEL(
            "font/JosefinSans-Light.ttf",
            26,
            Color.DARK_GRAY,
            Color.LIGHT_GRAY,
            1.0f
    ),
    FONT_AWESOME_MENU(
            "font/FontAwesome.ttf",
            40,
            new Color(0.078431f, 0.768627f, 0.768627f, 1f),
            FA.ALL_JOINED
    ),
    FONT_AWESOME_INTERACTION(
            "font/FontAwesome.ttf",
            64,
            Color.WHITE,
            Color.DARK_GRAY,
            2f,
            FA.ALL_JOINED
    ),
    LOG_ENTRY_ICON(
            "font/FontAwesome.ttf",
            32,
            Color.WHITE,
            Color.BLACK,
            1.0f,
            FA.ALL_JOINED
    );
    public static final String NAME_SEPARATOR = "#";

    @Getter private final AssetDescriptor<BitmapFont> assetDescriptor;


    FontAsset(String path, int size, Color color) {
        this(path, size, color, FreeTypeFontGenerator.DEFAULT_CHARS);
    }

    FontAsset(String path, int size, Color color, String characters) {
        this(path, new li.yuri.openspacebox.assetmanagement.loader.ManagedFontLoader.ManagedFontParameter(size, color, characters));
    }

    FontAsset(String path, li.yuri.openspacebox.assetmanagement.loader.ManagedFontLoader.ManagedFontParameter managedFontParameter) {
        String pathAndName = path + NAME_SEPARATOR + name();
        assetDescriptor = new AssetDescriptor<>(pathAndName, BitmapFont.class, managedFontParameter);
    }

    FontAsset(String path, int size, Color color, Color borderColor, float borderWidth) {
        this(path, size, color, borderColor, borderWidth, FreeTypeFontGenerator.DEFAULT_CHARS);
    }

    FontAsset(String path, int size, Color color, Color borderColor, float borderWidth, String characters) {
        this(path, new ManagedFontLoader.ManagedFontParameter(size, color, borderColor, borderWidth, characters));
    }

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(ManagedAsset::getAssetDescriptor).collect(Collectors.toList());
    }
}
