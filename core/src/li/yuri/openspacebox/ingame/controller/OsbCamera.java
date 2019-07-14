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

package li.yuri.openspacebox.ingame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import li.yuri.openspacebox.ingame.object.HasVelocity;
import lombok.val;

public class OsbCamera extends OrthographicCamera implements ChunkManager.DrawableChunksProvider {
    /**
     * The relative speed {@link HasVelocity}-Objects should be tracked.
     */
    private static final float TRACKING_SPEED = 3.0f;

    /**
     * Make sure lerping does not result in a value too big because of large deltaTimes.
     */
    private static final float MAX_LERP_PROGRESS = 1.0f;

    /**
     * The share of the screen's width/height which the object should "lag behind" the camera at full velocity. I know,
     * it's an awful name, but i can't think of anything better at the moment.
     */
    private static final float FULL_VELOCITY_LAG_PROPORTION = 0.35f;

    /**
     * The value the a velocity's length^2 gets multiplied with to determine the zoom.
     */
    private static final float ZOOM_COEFFICIENT = 0.00015f;

    /**
     * The speed the camera zooms out to adjust to a velocity.
     */
    private static final float ZOOM_OUT_SPEED = 1f;

    /**
     * The speed the camera zooms in to adjust to a velocity.
     */
    private static final float ZOOM_IN_SPEED = 0.25f;

    /**
     * The most zoomed in the camera can get zooming by velocity.
     */
    private static final float MINIMUM_ZOOM = 1.0f;

    /**
     * The most zoomed out the camera can get when zooming by velocity..
     */
    private static final float MAXIMUM_ZOOM = 1.9f;

    private final float velocityLagX;
    private final float velocityLagY;

    public OsbCamera() {
        setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        velocityLagX = Gdx.graphics.getWidth() * FULL_VELOCITY_LAG_PROPORTION;
        velocityLagY = Gdx.graphics.getHeight() * FULL_VELOCITY_LAG_PROPORTION;
    }

    /**
     * AbstractIngameObjects are tracked by moving the camera to them instantly.
     */
    public void track(AbstractIngameObject object, float deltaTime) {
        moveInstantlyTo(object);
    }

    /**
     * Objects with a velocity get tracked by moving the camare to them smoothly.
     */
    public void track(HasVelocity object, float deltaTime) {
        moveSmoothlyTo(object, deltaTime);
        adjustZoomToVelocity(object.getVelocity(), deltaTime);
    }

    /**
     * Moves smoothly to the given object's position. If the object is moving, the camera moves ahead of the object ,so
     * the player can see what's in front of him (See {@link #FULL_VELOCITY_LAG_PROPORTION}).
     */
    private void moveSmoothlyTo(HasVelocity object, float deltaTime) {
        val objectX = object.getObject().getPosition().x;
        val objectY = object.getObject().getPosition().y;
        val objectVelocity = object.getVelocity();


        val targetX = objectX + objectVelocity.x / object.getMaxSpeed() * velocityLagX;
        val nextX = MathUtils.lerp(position.x, targetX, Math.min(deltaTime * TRACKING_SPEED, MAX_LERP_PROGRESS));

        val targetY = objectY + objectVelocity.y / object.getMaxSpeed() * velocityLagY;
        val nextY = MathUtils.lerp(position.y, targetY, Math.min(deltaTime * TRACKING_SPEED, MAX_LERP_PROGRESS));

        position.set(nextX, nextY, position.z);
    }

    /**
     * Just adjusts the position to the given object's one.
     */
    private void moveInstantlyTo(AbstractIngameObject object) {
        position.set(object.getPosition().x, object.getPosition().y, position.z);
    }

    /**
     * Zooms in/out by the given velocity's len2().
     */
    private void adjustZoomToVelocity(Vector2 velocity, float deltaTime) {
        float targetZoom = 1 + velocity.len2() * ZOOM_COEFFICIENT * deltaTime;
        float nextZoom = this.zoom;

        // Zoom out faster than zooming in
        if (this.zoom < targetZoom)
            nextZoom = MathUtils.lerp(this.zoom, targetZoom, deltaTime * ZOOM_OUT_SPEED);
        else if (this.zoom > targetZoom)
            nextZoom = MathUtils.lerp(this.zoom, targetZoom, deltaTime * ZOOM_IN_SPEED);

        this.zoom = MathUtils.clamp(nextZoom, MINIMUM_ZOOM, MAXIMUM_ZOOM);
    }

    @Override
    public ChunkManager.ChunksIndexes getDrawableChunksIndexes() {
        // Sorry, couldn't think of a better name
        val zoomedHalfWidth = viewportWidth / 2.0f * zoom;
        val zoomedHalfHeight = viewportHeight / 2.0f * zoom;

        val leftEdge = position.x - zoomedHalfWidth;
        val rightEdge = position.x + zoomedHalfWidth;
        val bottomEdge = position.y - zoomedHalfHeight;
        val topEdge = position.y + zoomedHalfHeight;

        val leftChunkIndex = Chunk.getChunkXFor(leftEdge);
        val rightChunkIndex = Chunk.getChunkXFor(rightEdge);
        val bottomChunkIndex = Chunk.getChunkYFor(bottomEdge);
        val topChunkIndex = Chunk.getChunkYFor(topEdge);

        return new ChunkManager.ChunksIndexes(leftChunkIndex, rightChunkIndex, bottomChunkIndex, topChunkIndex);
    }
}
