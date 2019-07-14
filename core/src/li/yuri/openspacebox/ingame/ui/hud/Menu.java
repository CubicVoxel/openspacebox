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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FA;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.assetmanagement.asset.TextureAsset;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.ui.logbook.PlayerLogBook;
import li.yuri.openspacebox.util.UpdateMultiplexer;
import li.yuri.openspacebox.util.animation.Anim;
import li.yuri.openspacebox.util.animation.Easing;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbTextButton;

public class Menu extends VerticalGroup {
    public static final float BUTTON_SIZE = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) / 7.0f;
    private static final int SPACING = 20;

    private final li.yuri.openspacebox.util.UpdateMultiplexer updateMultiplexer = new UpdateMultiplexer();

    private final li.yuri.openspacebox.util.widget.OsbTextButton openCloseMenuButton;
    private final VerticalGroup menuItemsGroup;
    private final li.yuri.openspacebox.util.widget.OsbTextButton logMenuButton;
    private final li.yuri.openspacebox.util.widget.OsbTextButton debugMenuButton;
    private final li.yuri.openspacebox.util.widget.OsbGui stage;
    private final li.yuri.openspacebox.ingame.controller.GameControllers gameControllers;
    private final li.yuri.openspacebox.assetmanagement.asset.ResourceBundles resourceBundles;

    public Menu(
            OsbGui stage,
            GameControllers gameControllers,
            ResourceBundles resourceBundles
    ) {
        this.stage = stage;
        this.gameControllers = gameControllers;
        this.resourceBundles = resourceBundles;

        space(SPACING);
        setWidth(BUTTON_SIZE);
        align(Align.top);

        openCloseMenuButton = new li.yuri.openspacebox.util.widget.OsbTextButton(li.yuri.openspacebox.assetmanagement.asset.FA.BARS.asString(), new MenuButtonStyle());
        openCloseMenuButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
        openCloseMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (isOpened()) closeMenu();
                else openMenu();
            }
        });
        addActor(openCloseMenuButton);

        menuItemsGroup = new VerticalGroup();
        menuItemsGroup.space(SPACING);
        menuItemsGroup.columnTop();

        logMenuButton = new li.yuri.openspacebox.util.widget.OsbTextButton(li.yuri.openspacebox.assetmanagement.asset.FA.BOOK.asString(), new MenuButtonStyle());
        logMenuButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
        logMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final li.yuri.openspacebox.ingame.ui.logbook.PlayerLogBook playerLogBook = new PlayerLogBook(
                        Menu.this.resourceBundles,
                        Menu.this.gameControllers,
                        Menu.this.stage
                );

            }
        });
        menuItemsGroup.addActor(logMenuButton);

        debugMenuButton = new OsbTextButton(li.yuri.openspacebox.assetmanagement.asset.FA.BUG.asString(), new MenuButtonStyle());
        debugMenuButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
        menuItemsGroup.addActor(debugMenuButton);
    }

    private boolean isOpened() {
        return getChildren().contains(menuItemsGroup, true);
    }

    private void openMenu() {
        openCloseMenuButton.setText(li.yuri.openspacebox.assetmanagement.asset.FA.TIMES_CIRCLE.asString());
        openCloseMenuButton.setColor(Color.GRAY);
        addActor(menuItemsGroup);

        menuItemsGroup.layout();

        final float openCloseButtonYOnMenuItemsGroup = openCloseButtonYOnMenuItemsGroup();

        for (final Actor menuItem : menuItemsGroup.getChildren()) {
            final float menuItemOriginalY = menuItem.getY();
            menuItem.setY(openCloseButtonYOnMenuItemsGroup);

            li.yuri.openspacebox.util.animation.Anim.on(menuItem)
                    .duration(.35f)
                    .easing(li.yuri.openspacebox.util.animation.Easing.CUBIC_OUT)
                    .animatePosition(menuItem.getX(), menuItemOriginalY)
                    .using(updateMultiplexer)
                    .finishedListener(menuItemsGroup::invalidate)
                    .start();
        }
    }

    private void closeMenu() {
        openCloseMenuButton.setText(FA.BARS.asString());
        openCloseMenuButton.setColor(Color.WHITE);

        final float openCloseButtonYOnMenuItemsGroup = openCloseButtonYOnMenuItemsGroup();

        for (final Actor menuItem : menuItemsGroup.getChildren()) {
            final float menuItemOriginalY = menuItem.getY();

            Anim.on(menuItem)
                    .easing(Easing.CUBIC_OUT)
                    .duration(.25f)
                    .animatePosition(menuItem.getX(), openCloseButtonYOnMenuItemsGroup())
                    .using(updateMultiplexer)
                    .finishedListener(() -> {
                        menuItem.setY(menuItemOriginalY);
                        removeActor(menuItemsGroup);
                    })
                    .start();
        }

    }

    private float openCloseButtonYOnMenuItemsGroup() {
        Vector2 openButtonLocalCoordinates =
                new Vector2(openCloseMenuButton.getX(), openCloseMenuButton.getY());
        Vector2 openButtonGlobalCoordinates =
                openCloseMenuButton.getParent().localToStageCoordinates(openButtonLocalCoordinates);
        Vector2 openButtonItemsGroupLocalCoordinates =
                menuItemsGroup.stageToLocalCoordinates(openButtonGlobalCoordinates);

        return openButtonItemsGroupLocalCoordinates.y;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateMultiplexer.update(delta);
    }

    private static class MenuButtonStyle extends TextButton.TextButtonStyle {
        public MenuButtonStyle() {
            font = li.yuri.openspacebox.OpenSpaceBox.getAsset(FontAsset.FONT_AWESOME_MENU);

            Skin skin = new Skin();
            skin.add("up", li.yuri.openspacebox.OpenSpaceBox.getAsset(li.yuri.openspacebox.assetmanagement.asset.TextureAsset.UI_MENU_BUTTON));
            skin.add("down", OpenSpaceBox.getAsset(TextureAsset.UI_MENU_BUTTON));

            up = skin.getDrawable("up");
            up.setMinWidth(BUTTON_SIZE);
            up.setMinHeight(BUTTON_SIZE);

            down = skin.getDrawable("down");
            down.setMinHeight(BUTTON_SIZE);
            down.setMinWidth(BUTTON_SIZE);
        }
    }
}
