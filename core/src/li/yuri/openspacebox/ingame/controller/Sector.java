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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import java8.util.stream.Stream;
import li.yuri.openspacebox.definition.universe.SectorDefinition;
import li.yuri.openspacebox.ingame.object.AbstractIngameObject;
import li.yuri.openspacebox.ingame.object.SectorBeacon;
import li.yuri.openspacebox.input.OsbInput;
import li.yuri.openspacebox.util.Updatable;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java8.util.stream.StreamSupport.stream;

public final class Sector implements Disposable, Updatable {

    @Getter private final SectorProperties sectorProperties;
    private final li.yuri.openspacebox.ingame.controller.ChunkManager chunkManager;

    @Getter private SectorBeacon sectorBeacon;

    @Setter private TextureRegion background;
    @Getter private ArrayList<AbstractIngameObject<?>> ingameObjects = new ArrayList<>(); // Use addObject()


    public Sector(
            li.yuri.openspacebox.ingame.controller.ChunkManager.HighPriorityChunksProvider highPriorityChunksProvider,
            li.yuri.openspacebox.ingame.controller.ChunkManager.DrawableChunksProvider drawableChunksProvider,
            SectorProperties sectorProperties
    ) {
        this.sectorProperties = sectorProperties;

        chunkManager = new ChunkManager(highPriorityChunksProvider, drawableChunksProvider, this);

        OsbInput.getInstance().getInputHandler().getInputEventBus().register(this);
    }

    public void addObjects(Collection<AbstractIngameObject<?>> objects) {
        stream(objects).forEach(this::addObject);
    }


    @Override
    public void update(float deltaTime) {
        chunkManager.update(deltaTime);
    }

    public void drawOn(SpriteBatch batch) {
        batch.draw(background, background.getRegionWidth() / -2, background.getRegionHeight() / -2,
                background.getRegionWidth(), background.getRegionHeight()
        );
        chunkManager.drawOn(batch);
    }

    public void addObject(AbstractIngameObject<?> object) {
        ingameObjects.add(object);
        object.setSector(this);

        if (object instanceof SectorBeacon)
            this.sectorBeacon = (SectorBeacon) object;
    }


    @SuppressWarnings("unchecked")
    public <T extends AbstractIngameObject> Stream<T> streamIngameObjectsOf(Class<T> clazz) {
        return stream(ingameObjects).filter(clazz::isInstance).map(obj -> (T) obj);
    }


    public li.yuri.openspacebox.ingame.controller.Chunk getChunkForObject(AbstractIngameObject abstractIngameObject) {
        return chunkManager.getChunkForObject(abstractIngameObject);
    }

    public Chunk getChunkForIndex(Integer x, Integer y) {
        return chunkManager.getChunkForIndex(x, y);

    }

    @Override
    public void dispose() {
        stream(ingameObjects).forEach(AbstractIngameObject::dispose);
    }

    @Value
    public static class SectorProperties {
        private SectorDefinition sectorDefinition;
        private List<String> connectedSectorIds;
    }
}
