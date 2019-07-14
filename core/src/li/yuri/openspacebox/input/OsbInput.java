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

package li.yuri.openspacebox.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.google.common.eventbus.EventBus;
import java8.util.J8Arrays;
import lombok.Getter;


/**
 * Manages the instance of an {@link AbstractInputHandler} and acts as an InputMultiplexer, so all additional
 * InputProcessors can add themselves here.
 */
public class OsbInput extends InputMultiplexer {
    private static OsbInput instance = new OsbInput();

    @Getter
    private li.yuri.openspacebox.input.InputMode inputMode;

    private AbstractInputHandler inputHandler;

    private OsbInput() {
        Gdx.input.setInputProcessor(this);

        // Get the first inputMode of which all required peripherals are available.
        // Don't check the findFirst() for existence. If no suitable InputMode is available, the application may crash.
        inputMode = J8Arrays.stream(li.yuri.openspacebox.input.InputMode.values())
                .filter(InputMode::requiredPeripheralsAvailable)
                .findFirst().get();
        inputHandler = inputMode.createInputHandler();
        addProcessor(inputHandler);
    }

    public static OsbInput getInstance() {
        return instance;
    }

    /**
     * Proxy for getInputHandler().handleInput
     */
    public void handleInput(float deltaTime) {
        getInputHandler().handleInput(deltaTime);
    }

    public AbstractInputHandler getInputHandler() {
        return inputHandler;
    }

    public static EventBus getInputEventBus() {
        return getInstance().getInputHandler().getInputEventBus();
    }
}

