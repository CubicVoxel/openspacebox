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

package li.yuri.workspacefx.style;

import li.yuri.workspacefx.application.WorkspaceFxApplication;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The stylesheets WorkspaceFX comes with.
 */
public enum StyleSheets {
    /**
     * The "dark" theme of WorkspaceFX. Just adding it to your scenes enables the theme.
     */
    THEME("/li/yuri/workspacefx/theme.css"),

    /**
     * Some hopefully useful utility styles. Classes can be referenced via {@link Styles}.
     */
    STYLES("/li/yuri/workspacefx/styles.css"),

    /**
     * Styles the {@link WorkspaceFxApplication} uses.
     */
    WFX("/li/yuri/workspacefx/wfx.css");


    private String uri;

    StyleSheets(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public static Stream<String> getAllUris() {
        return Arrays.stream(values())
                .map(StyleSheets::getUri);
    }

}
