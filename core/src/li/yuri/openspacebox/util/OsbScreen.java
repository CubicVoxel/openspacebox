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

package li.yuri.openspacebox.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import li.yuri.openspacebox.OpenSpaceBox;

public abstract class OsbScreen implements Screen {

    @Override
    public final void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        beforeDraw();
        drawObjectBatch(OpenSpaceBox.getInstance().getObjectBatch());
        drawGui();
    }

    protected void update(float delta) {
        // Override if necessary
    }

    /**
     * Gets called before the draw-methods are called.
     */
    protected void beforeDraw() {
        // Do nothing by default
    }

    protected abstract void drawObjectBatch(SpriteBatch batch);

    protected abstract void drawGui();


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
