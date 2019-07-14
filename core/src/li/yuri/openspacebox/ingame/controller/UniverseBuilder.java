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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.util.DefinitionFinder;
import li.yuri.openspacebox.definition.template.AsteroidRegion;
import li.yuri.openspacebox.definition.template.SectorBeaconTemplate;
import li.yuri.openspacebox.definition.template.ShipTemplate;
import li.yuri.openspacebox.definition.template.StationTemplate;
import li.yuri.openspacebox.definition.type.ShipType;
import li.yuri.openspacebox.definition.type.StationType;
import li.yuri.openspacebox.definition.universe.FactionDefinition;
import li.yuri.openspacebox.definition.universe.SectorDefinition;
import li.yuri.openspacebox.ingame.faction.CommonFactions;
import li.yuri.openspacebox.ingame.faction.Faction;
import li.yuri.openspacebox.ingame.faction.GodWallet;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

import static java8.util.stream.StreamSupport.stream;

/**
 * Builds IngameObjects and the background for a given template.
 */
public final class UniverseBuilder {
    private final DefinitionFinder definitionFinder = li.yuri.openspacebox.OpenSpaceBox.createDefinitionFinder();
    private final li.yuri.openspacebox.ingame.controller.GameEventBus gameEventBus;
    private final li.yuri.openspacebox.ingame.controller.FactionManager factionManager;
    private final li.yuri.openspacebox.ingame.controller.SectorManager sectorManager;
    private final li.yuri.openspacebox.ingame.controller.ChunkManager.HighPriorityChunksProvider highPriorityChunksProvider;
    private final li.yuri.openspacebox.ingame.controller.IngameObjectRegistry ingameObjectRegistry;
    private final li.yuri.openspacebox.ingame.controller.ChunkManager.DrawableChunksProvider drawableChunksProvider;

    private final GodWallet sourceWallet = new GodWallet();

    public UniverseBuilder(
            GameEventBus gameEventBus,
            FactionManager factionManager,
            SectorManager sectorManager,
            li.yuri.openspacebox.ingame.controller.ChunkManager.HighPriorityChunksProvider highPriorityChunksProvider,
            IngameObjectRegistry ingameObjectRegistry,
            ChunkManager.DrawableChunksProvider drawableChunksProvider
    ) {
        this.gameEventBus = gameEventBus;
        this.factionManager = factionManager;
        this.sectorManager = sectorManager;
        this.highPriorityChunksProvider = highPriorityChunksProvider;
        this.ingameObjectRegistry = ingameObjectRegistry;
        this.drawableChunksProvider = drawableChunksProvider;
    }

    /**
     * Builds all the sectors the universe contains.
     */
    public void buildUniverse(li.yuri.openspacebox.definition.universe.UniverseDefinition universeDefinition) {
        List<Faction> factions = stream(universeDefinition.getFactionDefinitions())
                .map(this::buildFaction)
                .collect(Collectors.toList());
        factionManager.registerFactions(factions);

        stream(universeDefinition.getSectors())
                .map(sectorDefinition -> buildSector(sectorDefinition, universeDefinition.getSectorConnections()))
                .peek(ingameObjectRegistry::registerAllIn)
                .forEach(sectorManager::addSector);
    }

    private Faction buildFaction(FactionDefinition factionDefinition) {
        Faction faction = Faction.create(factionDefinition.getId());
        faction.setName(factionDefinition.getName());
        sourceWallet.transferTo(faction.getWallet(), factionDefinition.getWalletBalance());
        return faction;
    }

    private li.yuri.openspacebox.ingame.controller.Sector buildSector(li.yuri.openspacebox.definition.universe.SectorDefinition definition, List<li.yuri.openspacebox.definition.universe.UniverseDefinition.SectorConnection> allSectorConnections) {
        // Find the template
        val template = definitionFinder.findSectorTemplate(definition.getTemplate()).get();

        // Create all ingameObjects
        ArrayList<AbstractIngameObject<?>> ingameObjects = new ArrayList<>();
        ingameObjects.addAll(stream(template.getShips()).map(this::buildShip).collect(Collectors.toList()));
        ingameObjects.addAll(stream(template.getStations()).map(this::buildStation).collect(Collectors.toList()));
        ingameObjects.addAll(stream(template.getAsteroidRegions()).map(this::buildAsteroidRegion).flatMap
                (StreamSupport::stream).collect(Collectors.toList()));
        ingameObjects.addAll(stream(template.getSectorBeacons()).map(this::buildSectorBeacon)
                .collect(Collectors.toList()));

        // Create the background
        Texture backgroundTexture = OpenSpaceBox.getAsset(template.getBackground().getTextureAsset());
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureRegion background = new TextureRegion(backgroundTexture);
        background.setRegion(0, 0, 25600, 25600);

        // Determine sectorConnections
        ArrayList<String> connectedSectorIds = determineConnectedSectorIds(definition, allSectorConnections);

        // Create the sector
        li.yuri.openspacebox.ingame.controller.Sector sector = new li.yuri.openspacebox.ingame.controller.Sector(
                highPriorityChunksProvider,
                drawableChunksProvider,
                new Sector.SectorProperties(definition, connectedSectorIds)
        );
        sector.setBackground(background);
        sector.addObjects(ingameObjects);

        return sector;
    }


    private li.yuri.openspacebox.ingame.object.Station buildStation(StationTemplate stationTemplate) {
        StationType type = definitionFinder.findStationType(stationTemplate.getStationType()).get();
        li.yuri.openspacebox.ingame.object.Station station = new li.yuri.openspacebox.ingame.object.Station(gameEventBus, type);
        station.setPosition(new Vector2(stationTemplate.getX(), stationTemplate.getY()));
        station.setRotation(stationTemplate.getRotation());
        stream(stationTemplate.getItemsInCargo()).forEach(item -> station.getCargoHold().addItem(
                definitionFinder.findItem(item.getItem()).get(), item.getAmount()));

        factionManager.findById(stationTemplate.getFaction())
                .ifPresent(faction -> faction.getBelongings().add(station));

        return station;
    }

    private li.yuri.openspacebox.ingame.object.Ship buildShip(ShipTemplate shipTemplate) {
        ShipType type = definitionFinder.findShipType(shipTemplate.getShipType()).get();
        li.yuri.openspacebox.ingame.object.Ship ship = new li.yuri.openspacebox.ingame.object.Ship(gameEventBus, type);
        ship.setPosition(new Vector2(shipTemplate.getX(), shipTemplate.getY()));
        ship.setPlayer(shipTemplate.isPlayer());
        stream(shipTemplate.getItemsInCargo()).forEach(item -> ship.getCargoHold().addItem(
                definitionFinder.findItem(item.getItem()).get(), item.getAmount()));

        factionManager.findById(shipTemplate.getFaction())
                .ifPresent(faction -> faction.getBelongings().add(ship));
        return ship;
    }

    private ArrayList<li.yuri.openspacebox.ingame.object.Asteroid> buildAsteroidRegion(AsteroidRegion asteroidRegion) {
        final int count =
                Math.round(MathUtils.random(asteroidRegion.getCount().getMin(), asteroidRegion.getCount().getMax()));
        ArrayList<li.yuri.openspacebox.ingame.object.Asteroid> asteroids = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            li.yuri.openspacebox.ingame.object.Asteroid.AsteroidProperties properties =
                    li.yuri.openspacebox.ingame.object.Asteroid.AsteroidProperties.fromRandom(asteroidRegion.getSpriteMatcher(),
                            asteroidRegion.getRotationSpeed(), asteroidRegion.getSize()
                    );
            li.yuri.openspacebox.ingame.object.Asteroid asteroid = new li.yuri.openspacebox.ingame.object.Asteroid(gameEventBus, properties);

            final float x = MathUtils.random(
                    asteroidRegion.getDimensions().getXMin(),
                    asteroidRegion.getDimensions().getXMax()
            );
            final float y = MathUtils.random(
                    asteroidRegion.getDimensions().getYMin(),
                    asteroidRegion.getDimensions().getYMax()
            );
            asteroid.setPosition(new Vector2(x, y));
            asteroid.setRotation(MathUtils.random(360));

            asteroids.add(asteroid);

            CommonFactions.STATIC_OBJECT_FACTION.getBelongings().add(asteroid);
        }

        return asteroids;
    }

    private li.yuri.openspacebox.ingame.object.SectorBeacon buildSectorBeacon(SectorBeaconTemplate sectorBeaconTemplate) {
        li.yuri.openspacebox.ingame.object.SectorBeacon sectorBeacon = new li.yuri.openspacebox.ingame.object.SectorBeacon(gameEventBus);
        sectorBeacon.setPosition(new Vector2(sectorBeaconTemplate.getX(), sectorBeaconTemplate.getY()));
        CommonFactions.STATIC_OBJECT_FACTION.getBelongings().add(sectorBeacon);
        return sectorBeacon;
    }

    private ArrayList<String> determineConnectedSectorIds(
            SectorDefinition definition,
            List<li.yuri.openspacebox.definition.universe.UniverseDefinition.SectorConnection> allSectorConnections
    ) {
        ArrayList<String> connectedSectorIds = new ArrayList<>();
        connectedSectorIds.addAll(stream(allSectorConnections).filter(x -> x.getA().equals(definition.getId()))
                .map(li.yuri.openspacebox.definition.universe.UniverseDefinition.SectorConnection::getB)
                .collect(Collectors.toList()));
        connectedSectorIds.addAll(stream(allSectorConnections).filter(x -> x.getB().equals(definition.getId()))
                .map(li.yuri.openspacebox.definition.universe.UniverseDefinition.SectorConnection::getA)
                .collect(Collectors.toList()));
        return connectedSectorIds;
    }

}
