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

import com.badlogic.gdx.utils.Disposable;
import li.yuri.openspacebox.input.OsbInput;

import java.util.ArrayList;
import java.util.Stack;

import static java8.util.stream.StreamSupport.stream;

public class WindowManager implements Disposable, li.yuri.openspacebox.util.widget.OsbWindow.Closer {

    private Stack<li.yuri.openspacebox.util.widget.OsbWindow> windows = new Stack<>();

    private ArrayList<FirstWindowAddedListener> firstWindowAddedListeners = new ArrayList<>();
    private ArrayList<LastWindowClosedListener> lastWindowClosedListeners = new ArrayList<>();
    private ArrayList<WindowCloseListener> windowCloseListeners = new ArrayList<>();

    /**
     * Adds the given window as the top and sets this windowManager as its {@link li.yuri.openspacebox.util.widget.OsbWindow.Closer}.
     */
    public void addWindow(li.yuri.openspacebox.util.widget.OsbWindow window) {
        window.setCloser(this);
        windows.push(window);

        if (windows.size() == 1)
            fireFirstWindowAddedListeners();
    }


    @Override
    public boolean prepareClose(li.yuri.openspacebox.util.widget.OsbWindow sender) {
        return closeAllOnTop(sender);
    }

    /**
     * Closes all windows on top of the given window. Returns false if one of the windows failed to close.
     */
    private boolean closeAllOnTop(li.yuri.openspacebox.util.widget.OsbWindow window) {
        boolean everythingCouldBeClosed = true;
        while (everythingCouldBeClosed && !windows.empty() && !windows.peek().equals(window)) {
            everythingCouldBeClosed = windows.peek().close();
        }
        return everythingCouldBeClosed;
    }

    public synchronized void doClose(li.yuri.openspacebox.util.widget.OsbWindow sender) {
        sender.setVisible(false);
        fireWindowCloseListeners(sender);

        // Don't close if there are still windows on top (should not happen, since prepareClose() should have been
        // called beforehand.
        if (windows.peek().equals(sender))
            windows.pop();

        if (windows.empty())
            fireLastWindowClosedListeners();
    }


    public boolean areWindowsShown() {
        return !windows.empty();
    }

    /**
     * Closes the top-most window (if there are any).
     */
    public void closeTopWindow() {
        if (areWindowsShown())
            windows.peek().close();
    }

    public void addFirstWindowAddedListener(FirstWindowAddedListener firstWindowAddedListener) {
        firstWindowAddedListeners.add(firstWindowAddedListener);
    }

    private void fireFirstWindowAddedListeners() {
        stream(firstWindowAddedListeners).forEach(FirstWindowAddedListener::onFirstWindowAdded);
    }

    public void addLastWindowClosedListener(LastWindowClosedListener lastWindowClosedListener) {
        lastWindowClosedListeners.add(lastWindowClosedListener);
    }

    private void fireLastWindowClosedListeners() {
        stream(lastWindowClosedListeners).forEach(LastWindowClosedListener::onLastWindowClosed);
    }


    public void addWindowCloseListener(WindowCloseListener windowCloseListener) {
        windowCloseListeners.add(windowCloseListener);
    }

    private void fireWindowCloseListeners(li.yuri.openspacebox.util.widget.OsbWindow sender) {
        stream(windowCloseListeners).forEach(x -> x.onWindowClose(sender));
    }


    @Override
    public void dispose() {
        OsbInput.getInputEventBus().unregister(this);
    }


    /**
     * Gets fired when a window has been added which is the first one of the stack.
     */
    public interface FirstWindowAddedListener {
        void onFirstWindowAdded();
    }

    /**
     * Gets fired when every shown window has been closed.
     */
    public interface LastWindowClosedListener {
        void onLastWindowClosed();
    }

    /**
     * Gets fired when a window has been closed.
     */
    public interface WindowCloseListener {
        void onWindowClose(OsbWindow window);
    }


}
