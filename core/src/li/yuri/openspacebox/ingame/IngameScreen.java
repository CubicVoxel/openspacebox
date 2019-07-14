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

package li.yuri.openspacebox.ingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.eventbus.Subscribe;
import java8.util.Optional;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.UniverseAsset;
import li.yuri.openspacebox.ingame.controller.DebugValues;
import li.yuri.openspacebox.ingame.controller.GameControllers;
import li.yuri.openspacebox.ingame.controller.OsbCamera;
import li.yuri.openspacebox.ingame.controller.Sector;
import li.yuri.openspacebox.ingame.object.Ship;
import li.yuri.openspacebox.ingame.ui.hud.IngameHud;
import li.yuri.openspacebox.ingame.ui.station.SectorBeaconDialog;
import li.yuri.openspacebox.ingame.ui.station.StationInspector;
import li.yuri.openspacebox.input.OsbInput;
import li.yuri.openspacebox.util.OsbScreen;
import lombok.Getter;

import static java8.util.stream.StreamSupport.parallelStream;

public class IngameScreen extends OsbScreen {

    private static final OsbInput INPUT = OsbInput.getInstance();

    @Getter private final li.yuri.openspacebox.ingame.ui.hud.IngameHud ingameHud;
    private final li.yuri.openspacebox.ingame.controller.GameControllers gameControllers;
    private final li.yuri.openspacebox.ingame.controller.OsbCamera camera;

    public IngameScreen() {
        camera = new OsbCamera();

        gameControllers = new GameControllers(camera);
        ingameHud = new IngameHud(gameControllers);

        gameControllers.getGameEventBus().register(this);

        // The sector has to be initialized after the hud, since the hud listens on an event the sector fires.
        gameControllers.load(OpenSpaceBox.getAsset(UniverseAsset.UNIVERSE));

    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        INPUT.handleInput(delta);
        gameControllers.update(delta);
    }

    @Override
    protected void beforeDraw() {
        camera.update();
        gameControllers.getFocusedObjectProvider().getFocusedHasVelocity().ifPresentOrElse(
                x -> camera.track(x, Gdx.graphics.getDeltaTime()),
                () -> camera.track(
                        gameControllers.getFocusedObjectProvider().getFocusedObject(),
                        Gdx.graphics.getDeltaTime()
                )
        );

        camera.track(gameControllers.getFocusedObjectProvider().getFocusedObject(), Gdx.graphics.getDeltaTime());

        li.yuri.openspacebox.ingame.controller.DebugValues.getInstance().updateValue("renderDelta", String.valueOf(Gdx.graphics.getDeltaTime()));
        DebugValues.getInstance().updateValue("fps", String.valueOf(Gdx.graphics.getFramesPerSecond()));

        ingameHud.act(Gdx.graphics.getDeltaTime());
    }


    @Override
    protected void drawObjectBatch(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameControllers.getFocusedObjectProvider().getFocusedObject().getSector().drawOn(batch);
        batch.end();
    }

    @Override
    protected void drawGui() {
        ingameHud.draw();
    }

    @Override
    public void dispose() {
        gameControllers.dispose();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void showStationInspectorOnRequest(li.yuri.openspacebox.ingame.object.Ship.ShowStationInspectorRequestEvent event) {
        li.yuri.openspacebox.ingame.ui.station.StationInspector stationInspector = new StationInspector(ingameHud);
        stationInspector.loadStation(event.getStation(), event.getShip());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void showSectorBeaconDialogOnRequest(Ship.ShowSectorBeaconDialogRequestEvent event) {
        li.yuri.openspacebox.ingame.ui.station.SectorBeaconDialog sectorBeaconDialog = new SectorBeaconDialog(ingameHud, sector -> {
            Optional<li.yuri.openspacebox.ingame.controller.Sector> selectedSector = parallelStream(gameControllers.getSectorManager().getSectors())
                    .filter(x -> x.getSectorProperties()
                            .getSectorDefinition()
                            .getId()
                            .equals(sector.getSectorDefinition().getId()))
                    .findAny();
            event.getShip().goToSector(selectedSector.get());
        });
        sectorBeaconDialog.loadSectors(
                parallelStream(gameControllers.getSectorManager().getSectors())
                        .map(Sector::getSectorProperties)
                        .collect(Collectors.toList()));
    }

}
