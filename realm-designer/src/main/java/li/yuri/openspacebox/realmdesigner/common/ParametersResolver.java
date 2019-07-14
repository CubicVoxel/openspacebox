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

package li.yuri.openspacebox.realmdesigner.common;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ParametersResolver {
    public static final String PARAMETER_ASSETS_DIR = "assetsDir";

    private final List<String> rawParameters;

    public ParametersResolver(List<String> rawParameters) {
        this.rawParameters = rawParameters;
    }

    public Map<String, String> resolveParameters() {
        Map<String, String> parameters = new HashMap<>();
        rawParameters.parallelStream()
                .map(ParametersResolver::resolveParameter)
                .forEach(pair -> parameters.put(pair.getKey(), pair.getValue()));

        return parameters;
    }

    private static Pair<String, String> resolveParameter(String s) {
        String[] split = s.split(Pattern.quote("="));
        if (split.length == 2) {
            String escapedValue = split[1].replace("\"", "");
            return new Pair<>(split[0], escapedValue);
        } else {
            throw new IllegalArgumentException("All rawParameters must equal the pattern \"name=value\". The parameter "
                    + s + "will be ignored.");
        }
    }

}
