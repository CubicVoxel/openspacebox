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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Contains all regions the {@link TextureAtlasAsset#SPRITE}-Atlas has.
 */
@AllArgsConstructor
public enum SpriteAtlas implements ManagedAtlas {
    PROP_ASTEROID_METEORBROWN_BIG1("prop/asteroid/meteorBrown_big1"),
    PROP_ASTEROID_METEORBROWN_BIG2("prop/asteroid/meteorBrown_big2"),
    PROP_ASTEROID_METEORBROWN_BIG3("prop/asteroid/meteorBrown_big3"),
    PROP_ASTEROID_METEORBROWN_BIG4("prop/asteroid/meteorBrown_big4"),
    PROP_ASTEROID_METEORBROWN_MED1("prop/asteroid/meteorBrown_med1"),
    PROP_ASTEROID_METEORBROWN_MED3("prop/asteroid/meteorBrown_med3"),
    PROP_ASTEROID_METEORBROWN_SMALl1("prop/asteroid/meteorBrown_small1"),
    PROP_ASTEROID_METEORBROWN_SMALl2("prop/asteroid/meteorBrown_small2"),
    PROP_ASTEROID_METEORBROWN_TINY1("prop/asteroid/meteorBrown_tiny1"),
    PROP_ASTEROID_METEORBROWN_TINY2("prop/asteroid/meteorBrown_tiny2"),
    PROP_ASTEROID_METEORGREY_BIG1("prop/asteroid/meteorGrey_big1"),
    PROP_ASTEROID_METEORGREY_BIG2("prop/asteroid/meteorGrey_big2"),
    PROP_ASTEROID_METEORGREY_BIG3("prop/asteroid/meteorGrey_big3"),
    PROP_ASTEROID_METEORGREY_BIG4("prop/asteroid/meteorGrey_big4"),
    PROP_ASTEROID_METEORGREY_MED1("prop/asteroid/meteorGrey_med1"),
    PROP_ASTEROID_METEORGREY_MED2("prop/asteroid/meteorGrey_med2"),
    PROP_ASTEROID_METEORGREY_SMALL1("prop/asteroid/meteorGrey_small1"),
    PROP_ASTEROID_METEORGREY_SMALL2("prop/asteroid/meteorGrey_small2"),
    PROP_ASTEROID_METEORGREY_TINY1("prop/asteroid/meteorGrey_tiny1"),
    PROP_ASTEROID_METEORGREY_TINY2("prop/asteroid/meteorGrey_tiny2"),
    PROP_ASTEROID_SPACEMETEORS_001("prop/asteroid/spaceMeteors_001"),
    PROP_ASTEROID_SPACEMETEORS_002("prop/asteroid/spaceMeteors_002"),
    PROP_ASTEROID_SPACEMETEORS_003("prop/asteroid/spaceMeteors_003"),
    PROP_ASTEROID_SPACEMETEORS_004("prop/asteroid/spaceMeteors_004"),
    SHIP_BLACK_A("ship/black_a"),
    SHIP_BLACK_B("ship/black_b"),
    SHIP_BLACK_C("ship/black_c"),
    SHIP_BLACK_D("ship/black_d"),
    SHIP_BLACK_E("ship/black_e"),
    SHIP_BLUE_A("ship/blue_a"),
    SHIP_BLUE_B("ship/blue_b"),
    SHIP_BLUE_C("ship/blue_c"),
    SHIP_BLUE_D("ship/blue_d"),
    SHIP_BLUE_E("ship/blue_e"),
    SHIP_BLUE_F("ship/blue_f"),
    SHIP_BLUE_G("ship/blue_g"),
    SHIP_BLUE_H("ship/blue_h"),
    SHIP_BLUE_I("ship/blue_i"),
    SHIP_GREEN_A("ship/green_a"),
    SHIP_GREEN_B("ship/green_b"),
    SHIP_GREEN_C("ship/green_c"),
    SHIP_GREEN_D("ship/green_d"),
    SHIP_GREEN_E("ship/green_e"),
    SHIP_GREEN_F("ship/green_f"),
    SHIP_GREEN_G("ship/green_g"),
    SHIP_GREEN_H("ship/green_h"),
    SHIP_GREEN_I("ship/green_i"),
    SHIP_ORANGE_F("ship/orange_f"),
    SHIP_ORANGE_G("ship/orange_g"),
    SHIP_ORANGE_H("ship/orange_h"),
    SHIP_RED_A("ship/red_a"),
    SHIP_RED_B("ship/red_b"),
    SHIP_RED_C("ship/red_c"),
    SHIP_RED_D("ship/red_d"),
    SHIP_RED_E("ship/red_e"),
    SHIP_RED_F("ship/red_f"),
    SHIP_RED_G("ship/red_g"),
    SHIP_RED_H("ship/red_h"),
    SHIP_RED_I("ship/red_i"),
    SHIP_YELLOW_I("ship/yellow_i"),
    STATION_FACTORY("station/factory"),
    STATION_OFFICE("station/office"),
    STATION_SECTORBEACON("station/sector_beacon"),
    STATION_SOLARPLANT("station/solar_plant");

    @Getter private String name;

    @Override
    public TextureAtlasAsset getAtlas() {
        return TextureAtlasAsset.SPRITE;
    }
}
