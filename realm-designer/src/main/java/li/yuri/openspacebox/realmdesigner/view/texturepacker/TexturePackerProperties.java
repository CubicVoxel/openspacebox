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

package li.yuri.openspacebox.realmdesigner.view.texturepacker;

import lombok.Data;

import java.io.File;

@Data
public class TexturePackerProperties {
    private File inputDirectory;
    private File outputDirectory;
    private String packFile = "";
    private Boolean combineSubdirectories = Boolean.TRUE;
    private DimensionsField.Dimensions minSize = new DimensionsField.Dimensions(16, 16);
    private DimensionsField.Dimensions maxSize = new DimensionsField.Dimensions(4096, 4096);

}
