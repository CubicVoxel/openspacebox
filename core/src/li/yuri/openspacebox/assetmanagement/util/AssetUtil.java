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

package li.yuri.openspacebox.assetmanagement.util;

import com.badlogic.gdx.math.MathUtils;
import java8.util.J8Arrays;
import java8.util.stream.Collectors;
import li.yuri.openspacebox.assetmanagement.asset.ManagedAsset;
import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.regex.Pattern;

/**
 * Util for dealing with {@link ManagedAsset}s.
 */
@UtilityClass
public class AssetUtil {
    public SpriteAtlas randomSpriteAtlasFromRegExp(String regexp) {
        val pattern = Pattern.compile(regexp);

        val matchingAtlasses = J8Arrays.stream(SpriteAtlas.values())
                .filter(x -> pattern.matcher(x.name()).matches())
                .collect(Collectors.toList());

        val index = MathUtils.random(0, matchingAtlasses.size() - 1);
        return matchingAtlasses.get(index);
    }
}
