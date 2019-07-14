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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.google.common.eventbus.Subscribe;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.FA;
import li.yuri.openspacebox.assetmanagement.asset.FontAsset;
import li.yuri.openspacebox.assetmanagement.asset.ResourceBundles;
import li.yuri.openspacebox.ingame.controller.DebugValues;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.event.PlayerChangeEvent;
import li.yuri.openspacebox.ingame.object.Ship;
import li.yuri.openspacebox.input.InputMode;
import li.yuri.openspacebox.input.OsbInput;
import li.yuri.openspacebox.input.TouchInputHandler;
import li.yuri.openspacebox.util.widget.OsbGui;
import li.yuri.openspacebox.util.widget.OsbLabel;
import li.yuri.openspacebox.util.widget.OsbTextButton;
import li.yuri.openspacebox.util.widget.OsbTouchpad;
import lombok.Value;

/**
 * The Hud which is displayed in-game.
 */
public class IngameHud extends OsbGui implements TouchInputHandler.TouchInputProvider {

    private final I18NBundle i18NBundle = li.yuri.openspacebox.assetmanagement.asset.ResourceBundles.getInstance().getIngameHud();
    private final li.yuri.openspacebox.ingame.controller.GameControllers gameControllers;

    private final li.yuri.openspacebox.util.widget.OsbLabel debugLabel;
    private final li.yuri.openspacebox.util.widget.OsbTouchpad touchpad;
    private final li.yuri.openspacebox.util.widget.OsbTextButton switchSectorButton;
    private final li.yuri.openspacebox.util.widget.OsbTextButton enterStationButton;
    private final Menu menu;

    public IngameHud(GameControllers gameControllers) {
        this.gameControllers = gameControllers;

        gameControllers.getGameEventBus().register(this);
        registerAsTouchProcessor();

        debugLabel = createDebugLabel();
        touchpad = createTouchPad();
        switchSectorButton = createSwitchSectorButton();
        enterStationButton = createEnterStationButton();

        menu = new Menu(this, gameControllers, ResourceBundles.getInstance());
        menu.setPosition(
                Gdx.graphics.getWidth() - Menu.BUTTON_SIZE - 50,
                Gdx.graphics.getHeight() - 50
        );
        addWidget(menu);

        getWindowManager().addFirstWindowAddedListener(this::hideAllWidgets);
        getWindowManager().addLastWindowClosedListener(this::unhideAllWidgets);
    }


    private void registerAsTouchProcessor() {
        if (OsbInput.getInstance().getInputMode().equals(InputMode.TOUCH))
            ((TouchInputHandler) OsbInput.getInstance().getInputHandler()).setTouchInputProvider(this);
    }

    private li.yuri.openspacebox.util.widget.OsbLabel createDebugLabel() {
        li.yuri.openspacebox.util.widget.OsbLabel label = new OsbLabel("");
        label.setSize(Gdx.graphics.getWidth() - 10, Gdx.graphics.getHeight() - 10);
        label.setPosition(10, 0);
        label.setAlignment(Align.topLeft, Align.left);
        label.setFontScale(1.0f);
        addWidget(label);
        return label;
    }

    private li.yuri.openspacebox.util.widget.OsbTouchpad createTouchPad() {
        li.yuri.openspacebox.util.widget.OsbTouchpad touchpad = new OsbTouchpad();
        touchpad.setPosition(100, 100);
        addWidget(touchpad);
        return touchpad;
    }

    private li.yuri.openspacebox.util.widget.OsbTextButton createSwitchSectorButton() {
        li.yuri.openspacebox.util.widget.OsbTextButton button = new li.yuri.openspacebox.util.widget.OsbTextButton(li.yuri.openspacebox.assetmanagement.asset.FA.LOCATION_ARROW.asString(), new InteractionButtonStyle());
        button.setSize(100, 100);
        button.setPosition(Gdx.graphics.getWidth() - 130, 30);
        button.setVisible(false);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameControllers.getGameEventBus().post(new SwitchSectorButtonPressedEvent());
            }
        });
        addWidget(button);
        return button;
    }

    private li.yuri.openspacebox.util.widget.OsbTextButton createEnterStationButton() {
        li.yuri.openspacebox.util.widget.OsbTextButton button = new OsbTextButton(FA.SHOPPING_CART.asString(), new InteractionButtonStyle());
        button.setSize(100, 100);
        button.setPosition(Gdx.graphics.getWidth() - 130, 30);
        button.setVisible(false);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameControllers.getGameEventBus().post(new EnterStationButtonPressedEvent());
            }
        });
        addWidget(button);
        return button;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        debugLabel.setText(DebugValues.getInstance().buildText());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public float getMoveInputX() {
        return touchpad.getKnobPercentX();
    }

    @Override
    public float getMoveInputY() {
        return touchpad.getKnobPercentY();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handleShipStationHoveringOverChangedEvent(li.yuri.openspacebox.ingame.object.Ship.StationHoveringOverChangedEvent event) {
        enterStationButton.setVisible(event.getStationHoveringOver().isPresent());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handleShipSectorBeaconHoveringOverChangedEvent(Ship.SectorBeaconHoveringOverChangedEvent event) {
        switchSectorButton.setVisible(event.getSectorBeaconHoveringOver().isPresent());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handlePlayerChangeEvent(PlayerChangeEvent event) {
        // Unregister from the former player's eventbus and register to the new one's
        if (event.getFormerPlayer().isPresent()) {
            event.getFormerPlayer().get().getObjectHoveringOverChangedEventBus().unregister(this);
        }
        if (event.getNewPlayer().isPresent())
            event.getNewPlayer().get().getObjectHoveringOverChangedEventBus().register(this);
    }

    @Value
    public static class SwitchSectorButtonPressedEvent {}

    @Value
    public static class EnterStationButtonPressedEvent {}

    private static class InteractionButtonStyle extends TextButton.TextButtonStyle {
        public InteractionButtonStyle() {
            font = OpenSpaceBox.getAsset(FontAsset.FONT_AWESOME_INTERACTION);
        }
    }

}
