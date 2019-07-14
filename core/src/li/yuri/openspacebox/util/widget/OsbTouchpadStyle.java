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

package li.yuri.openspacebox.util.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;

public class OsbTouchpadStyle extends Touchpad.TouchpadStyle {
    // Height equals width
    public static final float SIZE = Gdx.graphics.getHeight() / 3;

    public OsbTouchpadStyle() {
        Skin touchpadSkin = new Skin();
        touchpadSkin.add("background", OpenSpaceBox.getAsset(TextureAsset.UI_TOUCHPAD_BACKGROUND));
        touchpadSkin.add("knob", OpenSpaceBox.getAsset(TextureAsset.UI_TOUCHPAD_KNOB));

        background = touchpadSkin.getDrawable("background");
        knob = touchpadSkin.getDrawable("knob");
        knob.setMinWidth(SIZE * .72f);
        knob.setMinHeight(SIZE * .72f);
    }
}
