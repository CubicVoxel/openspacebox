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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.eventbus.Subscribe;
import li.yuri.openspacebox.input.AbstractInputHandler;
import li.yuri.openspacebox.input.OsbInput;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;

import static java8.util.stream.StreamSupport.stream;

/**
 * Superclass for all stages which are used to display widgets.
 */
public abstract class OsbGui extends Stage implements Disposable {

    @Getter(AccessLevel.PROTECTED) private li.yuri.openspacebox.util.widget.WindowManager windowManager;
    @Getter(AccessLevel.PROTECTED) private ArrayList<Actor> widgets = new ArrayList<>();

    public OsbGui() {
        OsbInput.getInstance().addProcessor(this);
        OsbInput.getInputEventBus().register(this);

        windowManager = new WindowManager();
        windowManager.addWindowCloseListener(window -> getActors().removeValue(window, true));
    }

    public void addActor(OsbWindow window) {
        super.addActor(window);
        windowManager.addWindow(window);
    }

    /**
     * Adds the given actor and adds it to the widgets list, enabling it for hideAllWidgets() and unhideAllWidgets().
     */
    protected void addWidget(Actor widget) {
        addActor(widget);
        widgets.add(widget);
    }

    @Subscribe
    @SuppressWarnings("unused")
    private void handleBackInputEvent(AbstractInputHandler.BackInputEvent event) {
        windowManager.closeTopWindow();
    }

    protected void hideAllWidgets() {
        // To preserve the widget's original visibility, it just gets moved out of the visible area. Therefore, this
        // method should not be called multiple times or call unhide each every hide.
        stream(widgets).forEach(widget -> {
            widget.setX(widget.getX() + 3000);
        });
    }

    protected void unhideAllWidgets() {
        stream(widgets).forEach(widget -> {
            widget.setX(widget.getX() - 3000);
        });
    }


}
