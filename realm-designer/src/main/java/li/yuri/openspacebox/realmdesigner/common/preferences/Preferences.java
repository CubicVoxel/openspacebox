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

package li.yuri.openspacebox.realmdesigner.common.preferences;

import li.yuri.openspacebox.realmdesigner.util.LocalDateTimeXmlConverter;
import li.yuri.openspacebox.realmdesigner.view.texturepacker.TexturePackerProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Root(name = "Preferences")
public class Preferences {

    @ElementList(name = "definitionEditorRecentFiles")
    private List<RecentFile> definitionEditorRecentFiles = new ArrayList<>();

    @Element(name = "texturePacker", required = false)
    private TexturePackerProperties texturePackerProperties = new TexturePackerProperties();

    @Data
    @Root(name = "RecentFile")
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecentFile {
        @Attribute
        private String file;

        @Element
        @Convert(LocalDateTimeXmlConverter.class)
        private LocalDateTime dateTime;
    }


}
