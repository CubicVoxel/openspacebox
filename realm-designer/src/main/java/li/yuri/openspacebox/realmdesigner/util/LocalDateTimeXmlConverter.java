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

package li.yuri.openspacebox.realmdesigner.util;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.time.LocalDateTime;

public class LocalDateTimeXmlConverter implements Converter<LocalDateTime> {
    private static final String ATTRIBUTE_YEAR = "year";
    private static final String ATTRIBUTE_MONTHVALUE = "monthValue";
    private static final String ATTRIBUTE_DAYOFMONTH = "dayOfMonth";
    private static final String ATTRIBUTE_HOUR = "hour";
    private static final String ATTRIBUTE_MINUTE = "minute";
    private static final String ATTRIBUTE_SECOND = "second";

    @Override
    public LocalDateTime read(InputNode node) throws Exception {
        return LocalDateTime.of(
                Integer.valueOf(node.getAttribute(ATTRIBUTE_YEAR).getValue()),
                Integer.valueOf(node.getAttribute(ATTRIBUTE_MONTHVALUE).getValue()),
                Integer.valueOf(node.getAttribute(ATTRIBUTE_DAYOFMONTH).getValue()),
                Integer.valueOf(node.getAttribute(ATTRIBUTE_HOUR).getValue()),
                Integer.valueOf(node.getAttribute(ATTRIBUTE_MINUTE).getValue()),
                Integer.valueOf(node.getAttribute(ATTRIBUTE_SECOND).getValue())
        );
    }

    @Override
    public void write(OutputNode node, LocalDateTime value) {
        node.setAttribute(ATTRIBUTE_YEAR, String.valueOf(value.getYear()));
        node.setAttribute(ATTRIBUTE_MONTHVALUE, String.valueOf(value.getMonthValue()));
        node.setAttribute(ATTRIBUTE_DAYOFMONTH, String.valueOf(value.getDayOfMonth()));
        node.setAttribute(ATTRIBUTE_HOUR, String.valueOf(value.getHour()));
        node.setAttribute(ATTRIBUTE_MINUTE, String.valueOf(value.getMinute()));
        node.setAttribute(ATTRIBUTE_SECOND, String.valueOf(value.getSecond()));
    }
}
