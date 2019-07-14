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

package li.yuri.openspacebox.ingame.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Shape2D;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.ingame.controller.GameEventBus;
import lombok.val;

import java.util.ArrayList;

import static java8.util.stream.StreamSupport.stream;

/**
 * Detects its collisions/overlaps and fires listeners.
 */
public abstract class CollidableIngameObject<SHAPE extends Shape2D> extends AbstractIngameObject<SHAPE> {

    private ArrayList<EnteredOtherObjectListener> enteredOtherObjectListeners = new ArrayList<>();
    private ArrayList<LeftOtherObjectListener> leftOtherObjectListeners = new ArrayList<>();
    private ArrayList<AbstractIngameObject> currentOverlappingObjects = new ArrayList<>();

    protected CollidableIngameObject(GameEventBus gameEventBus, Sprite sprite, SHAPE shape2D) {
        super(gameEventBus, sprite, shape2D);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        handleObjectOverlaps();
    }

    /**
     * Checks if this object overlaps with other ingameObjects of the sector and fires the {@link
     * EnteredOtherObjectListener}s and {@link LeftOtherObjectListener}s.
     */
    private void handleObjectOverlaps() {
        val allOverlappingObjects = stream(getSector().getIngameObjects())
                .filter(obj -> obj != this).filter(this::isOverlappingWith)
                .collect(Collectors.toList());

        // Determine all overlapping objects which are not already in currentOverlappingObjects and fire the listeners
        val newOverlappingObjects = stream(allOverlappingObjects).filter(x -> !currentOverlappingObjects.contains(x))
                .collect(Collectors.toList());
        stream(newOverlappingObjects).forEach(this::fireEnteredOtherObjectListener);

        // Determine all objects in currentOverlappingObjects which are not in this update's overlapping objects
        // (i.e. objects which no longer overlap) and fire the listeners.
        val noLongerOverlappingObjects = stream(currentOverlappingObjects).filter(x -> !allOverlappingObjects.contains(x))
                .collect(Collectors.toList());
        stream(noLongerOverlappingObjects).forEach(this::fireLeftOtherObjectListener);

        // Update currentOverlappingObjects
        currentOverlappingObjects.removeAll(noLongerOverlappingObjects);
        currentOverlappingObjects.addAll(newOverlappingObjects);
    }

    protected boolean isOverlappingWith(AbstractIngameObject other) {
        return getSprite().getBoundingRectangle().contains(other.getSprite().getBoundingRectangle())
                || other.getSprite().getBoundingRectangle().contains(this.getSprite().getBoundingRectangle());
    }

    public void addEnteredOtherObjectListener(EnteredOtherObjectListener listener) {
        enteredOtherObjectListeners.add(listener);
    }

    public void removeEnteredOtherObjectListener(EnteredOtherObjectListener listener) {
        enteredOtherObjectListeners.remove(listener);
    }

    private void fireEnteredOtherObjectListener(AbstractIngameObject other) {
        stream(enteredOtherObjectListeners).forEach(x -> x.onEnteredOtherObject(this, other));
    }

    public void addLeftOtherObjectListener(LeftOtherObjectListener listener) {
        leftOtherObjectListeners.add(listener);
    }

    public void removeLeftOtherObjectListener(LeftOtherObjectListener listener) {
        leftOtherObjectListeners.remove(listener);
    }

    private void fireLeftOtherObjectListener(AbstractIngameObject other) {
        stream(leftOtherObjectListeners).forEach(x -> x.onLeftOtherObject(this, other));
    }

    /**
     * Gets triggered when this ingameObject begins overlapping with another.
     */
    public interface EnteredOtherObjectListener {
        void onEnteredOtherObject(AbstractIngameObject sender, AbstractIngameObject other);
    }

    /**
     * Gets triggered when this ingameObject stops overlapping with another.
     */
    public interface LeftOtherObjectListener {
        void onLeftOtherObject(AbstractIngameObject sender, AbstractIngameObject other);
    }
}
