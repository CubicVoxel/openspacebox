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
import com.badlogic.gdx.Input;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;

import static java8.util.stream.StreamSupport.stream;

public enum InputMode {
    // Sort values by priority, i.e. if the first and the second InputModes are available, the first one will be
    // chosen.
    TOUCH(TouchInputHandler.class, Input.Peripheral.MultitouchScreen),
    KEYBOARD(KeyboardInputHandler.class, Input.Peripheral.HardwareKeyboard);

    private Class<? extends AbstractInputHandler> inputHandlerClass;
    private ArrayList<Input.Peripheral> requiredPeripherals;

    InputMode(Class<? extends AbstractInputHandler> inputHandlerClass, Input.Peripheral... requiredPeripherals) {
        this.inputHandlerClass = inputHandlerClass;
        this.requiredPeripherals = new ArrayList<>(Arrays.asList(requiredPeripherals));
        this.requiredPeripherals.trimToSize();
    }

    public boolean requiredPeripheralsAvailable() {
        return stream(requiredPeripherals).allMatch(x -> Gdx.input.isPeripheralAvailable(x));
    }

    @SneakyThrows
    public AbstractInputHandler createInputHandler() {
        return inputHandlerClass.newInstance();
    }
}
