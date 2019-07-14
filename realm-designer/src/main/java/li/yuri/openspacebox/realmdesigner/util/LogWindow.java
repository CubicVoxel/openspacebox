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

import com.badlogic.gdx.utils.StringBuilder;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import li.yuri.workspacefx.style.Defaults;
import li.yuri.workspacefx.style.Styles;
import li.yuri.workspacefx.util.AnchorPanes;

/**
 * A window/{@link Stage} with a {@link TextArea} filling the window. Can be used for showing logs and also acts as a
 * {@link li.yuri.openspacebox.realmdesigner.util.ListenerPrintStream.PrintStreamListener}.
 */
public class LogWindow extends Stage implements ListenerPrintStream.PrintStreamListener {
    private static final int MIN_WIDTH = 500;
    private static final int MIN_HEIGHT = 550;

    private TextArea textArea;

    private StringBuilder stringBuilder = new StringBuilder();

    public LogWindow() {
        AnchorPane layout = new AnchorPane();
        layout.getStyleClass().add(Styles.DARK_BACKGROUND);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        //        layout.widthProperty().addListener((observable, oldValue, newValue) -> textArea.setMaxWidth(newValue.doubleValue()));
        layout.getChildren().add(textArea);
        AnchorPanes.setAllAnchors(textArea, Defaults.getInstance().spacingDefault);

        Scene scene = new Scene(layout, MIN_WIDTH, MIN_HEIGHT);
        //        scene.getStylesheets().add("styles.css");
        setScene(scene);

        setMinWidth(MIN_WIDTH);
        setMinHeight(MIN_HEIGHT);
    }

    public void append(String s) {
        stringBuilder.append(s);
        updateTextArea();
    }

    public void appendLine(String s) {
        append("\n" + s);
    }

    public void clear() {
        stringBuilder = new StringBuilder();
    }

    private void updateTextArea() {
        textArea.setText(stringBuilder.toString());
    }

    @Override
    public void onPrint(String s) {
        append(s);
    }

    public void printException(Throwable t) {
        appendLine(t.getMessage());
        for (StackTraceElement stackTraceElement : t.getStackTrace()) {
            appendLine(("\t" + stackTraceElement.toString()));
        }
        if (t.getCause() != null)
            printException(t.getCause());
    }
}
