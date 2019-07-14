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

package li.yuri.openspacebox.ingame.ui.hud;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.ingame.controller.Sector;
import lombok.val;

import java.util.ArrayList;

import static java8.util.stream.StreamSupport.stream;

public class SectorButton extends ImageButton {

    private li.yuri.openspacebox.ingame.controller.Sector.SectorProperties sector;
    private ArrayList<SectorSelectedListener> sectorSelectedListeners = new ArrayList<>();

    private SectorButtonLabel label;

    public SectorButton() {
        super(createStyle());
        label = new SectorButtonLabel();
        add(label).fill().expandY();

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fireSectorSelectedListeners();
            }
        });
    }

    public SectorButton(li.yuri.openspacebox.ingame.controller.Sector.SectorProperties sector) {
        this();
        setSector(sector);
    }

    public void setSector(li.yuri.openspacebox.ingame.controller.Sector.SectorProperties sector) {
        this.sector = sector;
        label.setText(sector.getSectorDefinition().getDisplayName());
        setPosition(sector.getSectorDefinition().getX(), sector.getSectorDefinition().getY());
        setSize(100, 100);
    }

    public void addSectorSelectedListener(SectorSelectedListener listener) {
        sectorSelectedListeners.add(listener);
    }

    private void fireSectorSelectedListeners() {
        stream(sectorSelectedListeners).forEach(listener -> listener.onSectorSelected(sector));
    }

    private static ImageButtonStyle createStyle() {
        val thickness = 25;

        NinePatch unselected = new NinePatch(li.yuri.openspacebox.OpenSpaceBox.getAsset(li.yuri.openspacebox.assetmanagement.asset.TextureAsset.UI_BOUNDINGBOX), 110, 110, 110, 110);
        unselected.setRightWidth(thickness);
        unselected.setLeftWidth(thickness);
        unselected.setTopHeight(thickness);
        unselected.setBottomHeight(thickness);
        NinePatchDrawable unselectedDrawable = new NinePatchDrawable(unselected);

        NinePatch selected = new NinePatch(li.yuri.openspacebox.OpenSpaceBox.getAsset(TextureAsset.UI_BOUNDINGBOX_SELECTED), 128, 128,
                128, 128);
        selected.setRightWidth(thickness);
        selected.setLeftWidth(thickness);
        selected.setTopHeight(thickness);
        selected.setBottomHeight(thickness);
        NinePatchDrawable selectedDrawable = new NinePatchDrawable(selected);

        ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
        imageButtonStyle.up = unselectedDrawable;
        imageButtonStyle.down = selectedDrawable;
        return imageButtonStyle;
    }


    public interface SectorSelectedListener {
        void onSectorSelected(Sector.SectorProperties sector);
    }

    private static class SectorButtonLabel extends Label {

        // TODO: I should really switch to skins or an enum for styles.
        public SectorButtonLabel() {
            super("", createStyle());
            setAlignment(Align.center);
        }

        private static LabelStyle createStyle() {
            LabelStyle style = new LabelStyle();
            style.font = OpenSpaceBox.getAsset(FontAsset.SECTOR_BUTTON_LABEL);
            return style;
        }
    }

}
