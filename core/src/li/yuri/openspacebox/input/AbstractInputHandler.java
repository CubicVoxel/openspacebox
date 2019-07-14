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

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.google.common.eventbus.EventBus;
import lombok.Getter;

/**
 * Manages the input-listeners and provides the abstract handleInput().
 */
public abstract class AbstractInputHandler extends InputAdapter {
    @Getter protected EventBus inputEventBus = new EventBus();

    /**
     * Checks the inputs and fires the listeners accordingly.
     */
    abstract void handleInput(float deltaTime);

    protected void postMoveInputEventWithClampedInput(float x, float y, float deltaTime) {
        Vector2 input = new Vector2(x, y);
        float clampedLength = MathUtils.clamp(input.len(), -1.0f, 1.0f);
        input.setLength(clampedLength);

        inputEventBus.post(new MoveInputEvent(deltaTime, input));
    }


    public static abstract class AbstractInputEvent {
        @Getter private final float deltaTime;

        public AbstractInputEvent(float deltaTime) {
            this.deltaTime = deltaTime;
        }
    }

    public static class MoveInputEvent extends AbstractInputEvent {
        @Getter private final Vector2 input;

        public MoveInputEvent(float deltaTime, Vector2 input) {
            super(deltaTime);
            this.input = input;
        }
    }

    public static class BackInputEvent extends AbstractInputEvent {

        public BackInputEvent(float deltaTime) {
            super(deltaTime);
        }
    }

}
