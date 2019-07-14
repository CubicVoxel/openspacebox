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

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import li.yuri.openspacebox.realmdesigner.util.ListenerPrintStream;
import li.yuri.openspacebox.realmdesigner.util.LogWindow;
import lombok.Getter;
import lombok.Setter;

/**
 * Executes the texturePacker by showing its output in a {@link LogWindow}.
 */
public final class TexturePackerExecutor {
    @Getter @Setter private TexturePacker.Settings settings = new TexturePacker.Settings();

    @Getter @Setter private String inputDir;
    @Getter @Setter private String outputDir;
    @Getter @Setter private String packFileName;

    public void setMinWidth(int minWidth) {
        settings.minWidth = minWidth;
    }

    public void setMinHeight(int minHeight) {
        settings.minHeight = minHeight;
    }

    public void setMaxWidth(int maxWidth) {
        settings.maxWidth = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        settings.maxHeight = maxHeight;
    }

    public void setCombineSubdirectories(boolean combineSubdirectories) {
        settings.combineSubdirectories = combineSubdirectories;
    }

    public void execute() {
        ListenerPrintStream listenerPrintStream = new ListenerPrintStream();
        listenerPrintStream.hookToOut();
        listenerPrintStream.hookToErr();

        LogWindow logWindow = new LogWindow();
        logWindow.setTitle("Packing textures...");
        listenerPrintStream.addPrintStreamListener(logWindow);
        logWindow.show();

        try {
            TexturePacker.process(settings, inputDir, outputDir, packFileName);
        } catch (Exception e) {
            logWindow.printException(e);
        }

        listenerPrintStream.unhook();
    }
}
