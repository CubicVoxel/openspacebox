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

package li.yuri.openspacebox.assetmanagement.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import java8.util.Optional;
import lombok.SneakyThrows;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * AssetLoader for files a {@link Serializer} can read.
 */
public class SerializedXmlLoader<T> extends AsynchronousAssetLoader<T, SerializedXmlLoader.SerializedXmlParameter<T>> {

    private final Class<? extends T> clazz;
    private Optional<T> serializedObject = Optional.empty();

    public SerializedXmlLoader(Class<? extends T> clazz, FileHandleResolver resolver) {
        super(resolver);
        this.clazz = clazz;
    }

    @Override
    @SneakyThrows
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SerializedXmlParameter<T> parameter) {
        Serializer serializer = new Persister();
        serializedObject = Optional.of(serializer.read(clazz, file.read()));

    }

    @Override
    public T loadSync(AssetManager manager, String fileName, FileHandle file, SerializedXmlParameter<T> parameter) {
        if (!serializedObject.isPresent())
            loadAsync(manager, fileName, file, parameter);
        return serializedObject.get();
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SerializedXmlParameter<T> parameter) {
        return new Array<>();
    }

    public static class SerializedXmlParameter<T> extends AssetLoaderParameters<T> {

    }
}
