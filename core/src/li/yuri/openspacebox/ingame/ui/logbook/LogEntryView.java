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

package li.yuri.openspacebox.ingame.ui.logbook;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.ingame.common.UniverseDateTimes;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.faction.log.LogEntry;
import li.yuri.openspacebox.util.LabelStyleBuilder;
import li.yuri.openspacebox.util.widget.OsbLabel;

public class LogEntryView extends Table {

    private static final int MAGIC_WIDTH_FIXING_VALUE = 73;
    private static final float DRAWABLE_BORDER_THICKNESS = 20.0f;

    private final li.yuri.openspacebox.ingame.controller.GameControllers gameControllers;
    private final li.yuri.openspacebox.assetmanagement.asset.ResourceBundles resourceBundles;

    private final li.yuri.openspacebox.util.widget.OsbLabel dateTimeLabel;
    private final li.yuri.openspacebox.util.widget.OsbLabel iconLabel;
    private final li.yuri.openspacebox.util.widget.OsbLabel messageLabel;

    public LogEntryView(GameControllers gameControllers, ResourceBundles resourceBundles) {
        this.gameControllers = gameControllers;
        this.resourceBundles = resourceBundles;

        setBackground(createBackground());

        columnDefaults(0).left().width(new Value() {
            @Override
            public float get(Actor context) {
                return context.getParent().getWidth() - MAGIC_WIDTH_FIXING_VALUE;
            }
        });
        columnDefaults(1).right();

        dateTimeLabel = new li.yuri.openspacebox.util.widget.OsbLabel("");
        add(dateTimeLabel);

        iconLabel = new li.yuri.openspacebox.util.widget.OsbLabel(
                "",
                new LabelStyleBuilder().font(li.yuri.openspacebox.OpenSpaceBox.getAsset(FontAsset.LOG_ENTRY_ICON)).build()
        );
        add(iconLabel);
        row();

        messageLabel = new OsbLabel("");
        messageLabel.setWrap(true);
        add(messageLabel);
    }

    private NinePatchDrawable createBackground() {
        NinePatch ninePatch = new NinePatch(OpenSpaceBox.getAsset(TextureAsset.UI_PANEL), 270, 270, 270, 270);
        ninePatch.setTopHeight(DRAWABLE_BORDER_THICKNESS);
        ninePatch.setBottomHeight(DRAWABLE_BORDER_THICKNESS);
        ninePatch.setLeftWidth(DRAWABLE_BORDER_THICKNESS);
        ninePatch.setRightWidth(DRAWABLE_BORDER_THICKNESS);
        ninePatch.setMiddleHeight(1);
        ninePatch.setMiddleWidth(1);
        return new NinePatchDrawable(ninePatch);
    }

    public void loadLogEntry(LogEntry logEntry) {
        dateTimeLabel.setText(UniverseDateTimes.format(logEntry.getDateTime()));
        iconLabel.setText(logEntry.getIcon().asString());
        messageLabel.setText(logEntry.createMessage(resourceBundles, gameControllers));
    }
}
