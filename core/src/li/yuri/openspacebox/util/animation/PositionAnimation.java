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

package li.yuri.openspacebox.util.animation;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class PositionAnimation<TARGET extends Actor> extends AbstractAnimation {
    private final TARGET target;

    private final float fromX;
    private final float fromY;

    private final float deltaX;
    private final float deltaY;

    private final Easing easing;


    public PositionAnimation(TARGET target, float toX, float toY, float duration, Easing easing) {
        super(duration);
        this.target = target;

        this.fromX = target.getX();
        this.fromY = target.getY();

        this.deltaX = toX - fromX;
        this.deltaY = toY - fromY;

        this.easing = easing;
    }

    @Override
    protected void continueAnimation(float timePercentage) {
        if (deltaX != .0f)
            target.setX(fromX + easing.getValue(deltaX, timePercentage));
        if (deltaY != .0f)
            target.setY(fromY + easing.getValue(deltaY, timePercentage));
    }

}
