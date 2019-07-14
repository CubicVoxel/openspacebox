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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import lombok.val;

public class CloseButton extends ImageButton {

    private static final int SIZE = 32;

    public CloseButton() {
        super(createStyle());
        setSize(SIZE, SIZE);
        getImageCell().expand().fill();
    }

    private static ImageButtonStyle createStyle() {
        val style = new ImageButtonStyle();

        val texture = OpenSpaceBox.getAsset(TextureAsset.UI_CROSS);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageDown = drawable;
        style.imageUp = drawable;

        return style;
    }
}
