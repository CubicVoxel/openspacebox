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

package li.yuri.openspacebox.util.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Setter;

import java.util.ArrayList;

import static java8.util.stream.StreamSupport.stream;

/**
 * A window which creates itself with the game's style and centers itself.
 */
public class OsbWindow extends Window {
    private static final float DEFAULT_WIDTH_PERCENTAGE = 0.8f;
    private static final float DEFAULT_HEIGHT_PERCENTAGE = 0.8f;

    @Setter private Closer closer;
    private ArrayList<BeforeCloseListener> beforeCloseListeners = new ArrayList<>();

    private CloseButton closeButton;

    /**
     * Crates the window with pre-defined width and height.
     */
    public OsbWindow(String title, OsbGui stage) {
        this(title, stage, DEFAULT_WIDTH_PERCENTAGE, DEFAULT_HEIGHT_PERCENTAGE);

        closeButton = createCloseButton();
        getTitleTable().add(closeButton);
        getTitleTable().getCell(closeButton).size(closeButton.getWidth(), closeButton.getHeight());
    }

    /**
     * Creates the window with given width and height.
     */
    public OsbWindow(String title, OsbGui gui, float width_percentage, float height_percentage) {
        super(title, new OsbWindowStyle());
        setStage(gui);
        setSize(getStage().getWidth() * width_percentage, getStage().getHeight() * height_percentage);
        setPosition(getStage().getWidth() / 2 - this.getWidth() / 2,
                getStage().getHeight() / 2 - this.getHeight() / 2);
        setMovable(false);
        gui.addActor(this);
    }

    private CloseButton createCloseButton() {
        CloseButton closeButton = new CloseButton();
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
        return closeButton;
    }

    /**
     * Checks canClose() and closes afterwards.
     */
    public boolean close() {
        boolean isReadyForClose = canClose() && closer.prepareClose(this);
        if (isReadyForClose)
            doClose();
        return isReadyForClose;
    }

    /**
     * Override this to allow/disallow closing.
     */
    protected boolean canClose() {
        return true;
    }

    /**
     * Closes this by firing the listeners and telling the closer.
     */
    protected final void doClose() {
        stream(beforeCloseListeners).forEach(x -> x.beforeClose(OsbWindow.this));
        closer.doClose(this);
    }

    public void addBeforeCloseListener(BeforeCloseListener listener) {
        beforeCloseListeners.add(listener);
    }

    /**
     * An object which can prepareClose an OsbWindow.
     */
    public interface Closer {
        /**
         * Prepares the closing of the given sender (i.e. by trying to close every window on top). Return false if
         * preparation failes.
         */
        boolean prepareClose(OsbWindow sender);

        /**
         * Just closes the window.
         */
        void doClose(OsbWindow sender);
    }


    /**
     * Gets triggered before the window is closed.
     */
    public interface BeforeCloseListener {
        void beforeClose(OsbWindow sender);
    }
}
