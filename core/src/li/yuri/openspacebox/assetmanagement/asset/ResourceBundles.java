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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import lombok.Getter;

import java.util.Locale;

import static com.badlogic.gdx.utils.I18NBundle.createBundle;

public final class ResourceBundles {
    @Getter private static ResourceBundles instance;

    @Getter private I18NBundle ingameHud;
    @Getter private I18NBundle loadingGui;

    private ResourceBundles(Locale locale) {
        ingameHud = createBundle(Gdx.files.internal("i18n/IngameHud"), locale);
        loadingGui = createBundle(Gdx.files.internal("i18n/LoadingGui"), locale);
    }

    public static void initialize(Locale locale) {
        instance = new ResourceBundles(locale);
    }
}
