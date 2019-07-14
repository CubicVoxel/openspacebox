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

package li.yuri.openspacebox.realmdesigner.view.definitioneditor.content.common;

import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.assetmanagement.asset.SpriteAtlas;
import li.yuri.openspacebox.assetmanagement.asset.TextureAtlasAsset;
import li.yuri.workspacefx.data.IsField;
import li.yuri.workspacefx.style.Defaults;
import lombok.SneakyThrows;

import java.util.Collection;

public class SpriteField extends VBox implements IsField<SpriteAtlas> {

    private static final int FIT_HEIGHT = 200;
    private static final double FIT_WIDTH = 200;

    private TextureAtlas textureAtlas;

    private ComboBox<SpriteAtlas> spriteAtlasComboBox;

    public SpriteField() {
        textureAtlas = OpenSpaceBox.getAsset(TextureAtlasAsset.SPRITE);
        setSpacing(Defaults.getInstance().spacingDefault);

        spriteAtlasComboBox = new ComboBox<>();
        spriteAtlasComboBox.getItems().addAll(SpriteAtlas.values());
        spriteAtlasComboBox.setOnAction(event -> displaySelectedSprite());
        getChildren().add(spriteAtlasComboBox);
    }

    private void displaySelectedSprite() {
        displaySprite(spriteAtlasComboBox.getValue());
    }

    @SneakyThrows
    private void displaySprite(SpriteAtlas spriteAtlas) {
        TextureData textureData = textureAtlas
                .findRegion(spriteAtlas.getName())
                .getTexture()
                .getTextureData();

        // TODO: Display sprite in OpenSpaceBox-instance
    }

    public SpriteAtlas getValue() {
        return spriteAtlasComboBox.getValue();
    }

    public void setValue(SpriteAtlas spriteAtlas) {
        spriteAtlasComboBox.setValue(spriteAtlas);
    }

    @Override
    public void showInvalid(Collection<String> message) {
        // TODO
    }

    @Override
    public void resetInvalid() {
        // TODO
    }
}
