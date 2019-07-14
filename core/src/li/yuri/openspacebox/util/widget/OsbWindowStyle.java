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

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;

public class OsbWindowStyle extends Window.WindowStyle {
    private static final float BORDER_THICKNESS = 45.0f;

    public OsbWindowStyle() {
        titleFont = OpenSpaceBox.getAsset(FontAsset.WINDOW_TITLE);
        titleFont.getData().setScale(0.5f, 0.5f);

        NinePatch ninePatch = new NinePatch(OpenSpaceBox.getAsset(TextureAsset.UI_WINDOW), 610, 610, 610, 610);
        ninePatch.setTopHeight(BORDER_THICKNESS);
        ninePatch.setBottomHeight(BORDER_THICKNESS);
        ninePatch.setLeftWidth(BORDER_THICKNESS);
        ninePatch.setRightWidth(BORDER_THICKNESS);
        background = new NinePatchDrawable(ninePatch);
    }
}
