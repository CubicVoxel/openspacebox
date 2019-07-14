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

public class KeyboardInputHandler extends AbstractInputHandler {

    @Override
    void handleInput(float deltaTime) {
        Input input = Gdx.input;

        float x = 0, y = 0;
        if (input.isKeyPressed(Input.Keys.A)) x -= 1.0f;
        if (input.isKeyPressed(Input.Keys.D)) x += 1.0f;
        if (input.isKeyPressed(Input.Keys.W)) y += 1.0f;
        if (input.isKeyPressed(Input.Keys.S)) y -= 1.0f;
        if (x != 0 || y != 0)
            postMoveInputEventWithClampedInput(x, y, deltaTime);

        if (input.isKeyJustPressed(Input.Keys.BACKSPACE))
            inputEventBus.post(new BackInputEvent(deltaTime));
    }
}
