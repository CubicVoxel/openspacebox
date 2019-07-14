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

package li.yuri.openspacebox.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import java8.util.Objects;

public final class LabelStyleBuilder {

    private Drawable background;
    private BitmapFont font;
    private Color fontColor;

    public LabelStyleBuilder background(Drawable background) {
        this.background = background;
        return this;
    }

    public LabelStyleBuilder font(BitmapFont font) {
        this.font = font;
        return this;
    }

    public LabelStyleBuilder fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public Label.LabelStyle build() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        if (Objects.nonNull(background)) labelStyle.background = background;
        if (Objects.nonNull(font)) labelStyle.font = font;
        if (Objects.nonNull(fontColor)) labelStyle.fontColor = fontColor;

        return labelStyle;
    }


}
