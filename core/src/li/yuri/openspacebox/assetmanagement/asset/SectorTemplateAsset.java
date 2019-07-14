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

package li.yuri.openspacebox.assetmanagement.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.definition.template.SectorTemplate;
import lombok.Getter;

import java.util.List;

/**
 * Collection of all SectorTemplates which get loaded when launching the game.
 * <p>
 * Use this for getting a SectorTemplate from the AssetManager.
 */
public enum SectorTemplateAsset implements li.yuri.openspacebox.assetmanagement.asset.ManagedAsset<SectorTemplate> {
    TEST_SECTOR(new AssetDescriptor<>("definition/sectortemplate/TestSector.xml", SectorTemplate.class)),
    ANOTHER_TEST_SECTOR(new AssetDescriptor<>("definition/sectortemplate/AnotherTestSector.xml", SectorTemplate.class)),
    THIRD_TEST_SECTOR(new AssetDescriptor<>("definition/sectortemplate/ThirdTestSector.xml", SectorTemplate.class));

    @Getter private final AssetDescriptor<SectorTemplate> assetDescriptor;

    SectorTemplateAsset(AssetDescriptor<SectorTemplate> assetDescriptor) {
        this.assetDescriptor = assetDescriptor;
    }

    public static List<AssetDescriptor> valuesAsAssets() {
        return J8Arrays.stream(values()).map(ManagedAsset::getAssetDescriptor).collect(Collectors.toList());
    }
}
