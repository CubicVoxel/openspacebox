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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java8.util.Optional;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.definition.type.ShipType;
import li.yuri.openspacebox.ingame.controller.DebugValues;
import li.yuri.openspacebox.ingame.controller.GameEventBus;
import li.yuri.openspacebox.ingame.controller.Sector;
import li.yuri.openspacebox.ingame.ui.hud.IngameHud;
import li.yuri.openspacebox.input.AbstractInputHandler;
import li.yuri.openspacebox.input.OsbInput;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.val;

public class Ship extends CollidableIngameObject<Polygon> implements HasVelocity, TradeEntity {

    /**
     * The cap for the velocity's len2(). Has the purpose of cancelling minor movement of the ship to prevent costly
     * calculations with nearly no effect.
     * <p>
     * TODO: If the cap is too high, and the update's deltaTime too low, initial acceleration is cancelled out and the
     * ship does not move at all. Implement a limit for the UpdateThread (also pretty useful for saving the phone's
     * battery) and then set the proper value for the cap.
     */
    private static final float VELOCITY_CAP_SQUARED = 5.0f;
    // Properties
    private li.yuri.openspacebox.definition.type.ShipType shipType;
    private li.yuri.openspacebox.ingame.object.CargoHold cargoHold;
    // Physics
    private Vector2 velocity = new Vector2(0, 0);
    // Logic
    @Getter @Setter private boolean isPlayer;
    @Getter private Optional<li.yuri.openspacebox.ingame.object.Station> stationHoveringOver = Optional.empty();
    @Getter private Optional<SectorBeacon> sectorBeaconHoveringOver = Optional.empty();

    // Events
    @Getter private EventBus objectHoveringOverChangedEventBus = new EventBus();

    public Ship(GameEventBus gameEventBus, li.yuri.openspacebox.definition.type.ShipType shipType) {
        super(gameEventBus, createSprite(shipType), createShape());
        this.shipType = shipType;
        this.cargoHold = new li.yuri.openspacebox.ingame.object.CargoHold(shipType.getCargoSpace());
        OsbInput.getInputEventBus().register(this);

        addEnteredOtherObjectListener((sender, other) -> handleEnteredOtherObject(other));
        addLeftOtherObjectListener((sender, other) -> handleLeftOtherObject(other));
    }

    @Override
    public li.yuri.openspacebox.ingame.object.Layer getLayer() {
        return Layer.SMALL_SHIPS_AND_PROPS;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        decelerate(delta);
        applyVelocity(delta);
        if (isPlayer()) {
            li.yuri.openspacebox.ingame.controller.DebugValues.getInstance().updateValue("playerPos", getPosition().toString());
            DebugValues.getInstance().updateValue("playerVelo^2", String.valueOf(getVelocity().len2()));
        }

    }

    private void applyVelocity(float delta) {
        move(velocity.cpy().scl(delta));
        rotateTowardsVelocity(delta);
    }

    private void rotateTowardsVelocity(float delta) {
        if (!velocity.isZero()) {
            float angleDelta = ((velocity.angle() - getRotation() + 360 + 180) % 360) - 180;
            val maxDelta = shipType.getPhysics().getRotationSpeed() * delta;
            angleDelta = MathUtils.clamp(angleDelta, -maxDelta, maxDelta);
            setRotation((getRotation() + angleDelta) % 360);
        }
    }

    private void moveTowards(Vector2 direction, float delta) {
        Vector2 movement = new Vector2(direction);
        movement.scl(shipType.getPhysics().getAcceleration() * delta);

        Vector2 totalVelocity = new Vector2(velocity).add(movement);
        totalVelocity.limit(getMaxSpeed());
        velocity = totalVelocity;
    }

    private void decelerate(float delta) {
        velocity.scl(1 - (shipType.getPhysics().getDeceleration() * delta));
        if (!velocity.isZero() && velocity.len2() < VELOCITY_CAP_SQUARED)
            velocity.setZero();
    }

    private void handleEnteredOtherObject(li.yuri.openspacebox.ingame.object.AbstractIngameObject other) {
        if (other instanceof li.yuri.openspacebox.ingame.object.Station)
            setStationHoveringOver(Optional.of((li.yuri.openspacebox.ingame.object.Station) other));
        else if (other instanceof SectorBeacon)
            setSectorBeaconHoveringOver(Optional.of((SectorBeacon) other));
    }

    private void handleLeftOtherObject(li.yuri.openspacebox.ingame.object.AbstractIngameObject other) {
        if (stationHoveringOver.isPresent() && other.equals(stationHoveringOver.get()))
            setStationHoveringOver(Optional.empty());
        else if (sectorBeaconHoveringOver.isPresent() && other.equals(sectorBeaconHoveringOver.get()))
            setSectorBeaconHoveringOver(Optional.empty());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handleMoveInputEvent(AbstractInputHandler.MoveInputEvent event) {
        if (isPlayer())
            moveTowards(event.getInput(), event.getDeltaTime());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handleEnterStationButtonPressedEvent(IngameHud.EnterStationButtonPressedEvent event) {
        if (isPlayer() && stationHoveringOver.isPresent()) {
            ShowStationInspectorRequestEvent stationInspectorRequestEvent = new ShowStationInspectorRequestEvent
                    (stationHoveringOver.get(), this);
            getGameEventBus().post(stationInspectorRequestEvent);
        }
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void handleSwitchSectorButtonPressedEvent(IngameHud.SwitchSectorButtonPressedEvent event) {
        if (isPlayer() && sectorBeaconHoveringOver.isPresent()) {
            ShowSectorBeaconDialogRequestEvent showSectorBeaconDialogRequestEvent = new
                    ShowSectorBeaconDialogRequestEvent(this);
            getGameEventBus().post(showSectorBeaconDialogRequestEvent);
        }
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public float getMaxSpeed() {
        return shipType.getPhysics().getMaxSpeed();
    }

    @Override
    public AbstractIngameObject getObject() {
        return this;
    }

    @Override
    public CargoHold getCargoHold() {
        return cargoHold;
    }

    private void setStationHoveringOver(Optional<li.yuri.openspacebox.ingame.object.Station> stationHoveringOver) {
        this.stationHoveringOver = stationHoveringOver;
        getObjectHoveringOverChangedEventBus().post(new StationHoveringOverChangedEvent(stationHoveringOver));
    }

    private void setSectorBeaconHoveringOver(Optional<SectorBeacon> sectorBeaconHoveringOver) {
        this.sectorBeaconHoveringOver = sectorBeaconHoveringOver;
        getObjectHoveringOverChangedEventBus().post(new SectorBeaconHoveringOverChangedEvent(sectorBeaconHoveringOver));
    }

    public void goToSector(Sector sector) {
        sector.addObject(this);

        setPosition(sector.getSectorBeacon().getPosition().add(sector.getSectorBeacon().getSize().scl(.5f)));
    }

    private static Sprite createSprite(ShipType shipType) {
        Sprite sprite = OpenSpaceBox.createSprite(shipType.getSpriteAtlas());
        // TODO: 25.12.16 Define size in ShipType
        sprite.setOriginCenter();
        return sprite;
    }

    private static Polygon createShape() {
        val shape = new Polygon();
        shape.setVertices(new float[]{0, 50, -50, -50, 0, -30, 50, -50, 0, 50});
        return shape;
    }

    @Value
    public static class StationHoveringOverChangedEvent {
        private Optional<li.yuri.openspacebox.ingame.object.Station> stationHoveringOver;
    }

    @Value
    public static class SectorBeaconHoveringOverChangedEvent {
        private Optional<SectorBeacon> sectorBeaconHoveringOver;
    }

    @Value
    public static class ShowStationInspectorRequestEvent {
        private Station station;
        private Ship ship;
    }

    @Value
    public class ShowSectorBeaconDialogRequestEvent {
        private Ship ship;
    }
}
