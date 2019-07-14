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
import com.google.common.base.Preconditions;
import java8.util.Objects;
import java8.util.function.Consumer;
import li.yuri.openspacebox.util.Updatable;
import li.yuri.openspacebox.util.UpdateMultiplexer;

/**
 * Class for a concise way of doing animations
 */
public class Anim {

    public static <TARGET extends Actor> AnimationBuilder<TARGET> on(TARGET target) {
        return new AnimationBuilder<>(target);
    }

    public static class AnimationBuilder<TARGET extends Actor> {
        private final TARGET target;
        private Easing easing = Easing.LINEAR;
        private float duration = .35f;
        private li.yuri.openspacebox.util.animation.AnimationFinishedListener finishedListener = () -> {};
        private Consumer<Updatable> registerOn;
        private Consumer<Updatable> unregisterOn;
        private AbstractAnimation animation;


        public AnimationBuilder(TARGET target) {
            this.target = target;
        }

        public AnimationBuilder<TARGET> easing(Easing easing) {
            this.easing = easing;
            return this;
        }

        public AnimationBuilder<TARGET> duration(float duration) {
            this.duration = duration;
            return this;
        }

        public AnimationBuilder<TARGET> registerOn(Consumer<Updatable> registerOn) {
            this.registerOn = registerOn;
            return this;
        }

        public AnimationBuilder<TARGET> unregisterOn(Consumer<Updatable> unregisterOn) {
            this.unregisterOn = unregisterOn;
            return this;
        }

        public AnimationBuilder<TARGET> using(UpdateMultiplexer updateMultiplexer) {
            return registerOn(updateMultiplexer::register).unregisterOn(updateMultiplexer::unregister);
        }

        public AnimationBuilder<TARGET> finishedListener(AnimationFinishedListener finishedListener) {
            this.finishedListener = finishedListener;
            return this;
        }


        public AnimationBuilder<TARGET> animatePosition(float toX, float toY) {
            this.animation = new PositionAnimation<>(target, toX, toY, duration, easing);
            return this;
        }


        public void start() {
            Preconditions.checkNotNull(registerOn, "Animation may only be started once registerOn is set.");
            Preconditions.checkNotNull(animation, "Animation may only be started once the animation type is set.");

            if (Objects.nonNull(unregisterOn))
                animation.addAnimationFinishedListener(() -> unregisterOn.accept(animation));

            animation.addAnimationFinishedListener(finishedListener);
            registerOn.accept(animation);
        }

    }
}
