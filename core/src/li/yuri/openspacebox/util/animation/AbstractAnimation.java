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

import li.yuri.openspacebox.util.Updatable;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractAnimation implements Updatable {

    private final float duration;
    private final List<li.yuri.openspacebox.util.animation.AnimationFinishedListener> animationFinishedListeners = new ArrayList<>();
    private float elapsedTime = 0f;
    private boolean animationFinished = false;

    protected AbstractAnimation(float duration) {
        this.duration = duration;
    }

    public void addAnimationFinishedListener(li.yuri.openspacebox.util.animation.AnimationFinishedListener listener) {
        animationFinishedListeners.add(listener);
    }

    public void removeAnimationFinishedListener(AnimationFinishedListener listener) {
        animationFinishedListeners.remove(listener);
    }

    @Override
    public final void update(float delta) {
        if (!animationFinished) {
            elapsedTime += delta;
            final float timePercentage = Math.min(1f, elapsedTime / duration);

            continueAnimation(timePercentage);

            if (elapsedTime >= duration) {
                for (val listener : animationFinishedListeners) listener.onAnimationFinished();
                animationFinished = true;
            }
        }

    }

    protected abstract void continueAnimation(final float timePercentage);
}
