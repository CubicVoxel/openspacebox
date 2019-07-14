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

package li.yuri.workspacefx.layout;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import li.yuri.workspacefx.style.Defaults;

import java.util.ArrayList;
import java.util.List;

public class Expandable extends VBox {
    private static final double INDICATOR_WIDTH = 20;

    private List<ExpandedListener> expandedListeners = new ArrayList<>();
    private boolean expanded = false;
    private Node content;

    private Label collapsedIndicator = new Label();
    private Label caption = new Label();

    public Expandable() {
        setSpacing(li.yuri.workspacefx.style.Defaults.getInstance().spacingDefault);

        collapsedIndicator.setPrefWidth(INDICATOR_WIDTH);

        HBox indicatorAndCaptionLayout = new HBox();
        indicatorAndCaptionLayout.setSpacing(Defaults.getInstance().spacingDefault);
        indicatorAndCaptionLayout.getChildren().setAll(collapsedIndicator, caption);
        HBox.setHgrow(collapsedIndicator, Priority.NEVER);
        HBox.setHgrow(caption, Priority.ALWAYS);

        indicatorAndCaptionLayout.setCursor(Cursor.HAND);
        indicatorAndCaptionLayout.setOnMouseClicked(event -> toggleExpanded());

        getChildren().add(indicatorAndCaptionLayout);

        updateIndicatorAndContentVisibility();
    }

    public Expandable(String text, Node content) {
        this();
        setCaption(text);
        setContent(content);
    }


    public void setCaption(String text) {
        caption.setText(text);
    }

    public void toggleExpanded() {
        setExpanded(!expanded);
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        updateIndicatorAndContentVisibility();
    }

    public void setContent(Node content) {
        this.content = content;
        VBox.setVgrow(content, Priority.ALWAYS);
        updateIndicatorAndContentVisibility();
    }

    private void updateIndicatorAndContentVisibility() {
        collapsedIndicator.setText(expanded ? "-" : "+");

        if (!expanded) {
            getChildren().remove(content);
        } else if (!getChildren().contains(content)) {
            getChildren().add(content);
        }

        expandedListeners.forEach(x -> x.onExpanded(expanded));
    }

    public void addExpandedListener(ExpandedListener listener) {
        expandedListeners.add(listener);
    }

    public interface ExpandedListener {
        void onExpanded(boolean expanded);
    }
}
