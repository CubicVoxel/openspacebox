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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Using this class, you can intercept all System.originalOut.printlns and, for instance, display them in a TextArea.
 */
public class ListenerPrintStream extends PrintStream {
    private List<PrintStreamListener> printStreamListeners = new ArrayList<>();

    private Optional<PrintStream> originalOut = Optional.empty();
    private Optional<PrintStream> originalErr = Optional.empty();

    public ListenerPrintStream() {
        super(System.out, true);
    }

    /**
     * Sets {@link System#setOut(PrintStream)} to this. Use {@link #unhook()} if you are done to use the original out
     * again.
     */
    public void hookToOut() {
        originalOut = Optional.of(System.out);
        System.setOut(this);
    }

    /**
     * Sets {@link System#setErr(PrintStream)} to this. Use {@link #unhook()} if you are done to use the original out
     * again.
     */
    public void hookToErr() {
        originalErr = Optional.of(System.err);
        System.setErr(this);
    }

    /**
     * If {@link #hookToOut()} and/or {@link #hookToErr()} was used, the orignal out and err will be restored.
     */
    public void unhook() {
        originalOut.ifPresent(System::setOut);
        originalErr.ifPresent(System::setErr);
    }

    @Override
    public void print(String s) {
        printStreamListeners.forEach(x -> x.onPrint(s));
    }


    @Override
    public void println(String s) {
        print("\n" + s);
    }

    public void addPrintStreamListener(PrintStreamListener listener) {
        printStreamListeners.add(listener);
    }

    public boolean removePrintStreamListener(PrintStreamListener listener) {
        return printStreamListeners.remove(listener);
    }

    public interface PrintStreamListener {
        void onPrint(String s);
    }
}
