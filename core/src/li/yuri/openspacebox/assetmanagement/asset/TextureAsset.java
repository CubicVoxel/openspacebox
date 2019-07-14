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
import com.badlogic.gdx.graphics.Texture;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import lombok.Getter;

import java.util.List;

/**
 * Collection of all Textures which get loaded when launching the game.
 * <p>
 * Use this for getting a Texture from the AssetManager.
 */
public enum TextureAsset implements ManagedAsset<Texture> {


    BACKGROUND_BLACK(new AssetDescriptor<>("background/black.png", Texture.class)),
    BACKGROUND_BLUE(new AssetDescriptor<>("background/blue.png", Texture.class)),
    BACKGROUND_DARKPURPLE(new AssetDescriptor<>("background/darkPurple.png", Texture.class)),
    BACKGROUND_PURPLE(new AssetDescriptor<>("background/purple.png", Texture.class)),
    UI_BOUNDINGBOX(new AssetDescriptor<>("ui/bounding_box.png", Texture.class)),
    UI_BOUNDINGBOX_SELECTED(new AssetDescriptor<>("ui/bounding_box_selected.png", Texture.class)),
    UI_CROSS(new AssetDescriptor<>("ui/cross.png", Texture.class)),
    UI_PANEL(new AssetDescriptor<>("ui/panel.png", Texture.class)),
    UI_MENU_BUTTON(new AssetDescriptor<>("ui/menu_button.png", Texture.class)),
    UI_TOUCHPAD_BACKGROUND(new AssetDescriptor<>("ui/touchpad_background.png", Texture.class)),
    UI_TOUCHPAD_KNOB(new AssetDescriptor<>("ui/touchpad_knob.png", Texture.class)),
    UI_UNIVERSEMAP_BACKGROUND(new AssetDescriptor<>("ui/universemap_background.png", Texture.class)),
    UI_WINDOW(new AssetDescriptor<>("ui/window.png", Texture.class));

    @Getter private final AssetDescriptor<Texture> assetDescriptor;

    TextureAsset(AssetDescriptor<Texture> assetDescriptor) {
        this.assetDescriptor = assetDescriptor;
    }

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(TextureAsset::getAssetDescriptor).collect(Collectors.toList());
    }


}
