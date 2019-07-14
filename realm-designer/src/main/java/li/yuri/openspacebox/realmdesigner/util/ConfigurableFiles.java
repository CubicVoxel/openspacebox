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

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.headless.HeadlessFileHandle;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.files.FileHandle;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Implementation of {@link Files} for {@link com.badlogic.gdx.backends.headless.HeadlessFileHandle}s.
 * <p>
 * Overrides {@link HeadlessFiles#localPath} with the given localPath and returns internal files as local files,
 * resulting in the ability to configure the location of the assets.
 */
public class ConfigurableFiles implements Files {

    public ConfigurableFiles(String localPath) {
        fuckThatLocalPath(localPath);
    }

    @Override
    public FileHandle getFileHandle(String fileName, FileType type) {
        return new HeadlessFileHandle(fileName, type);
    }

    @Override
    public FileHandle classpath(String path) {
        return new HeadlessFileHandle(path, FileType.Classpath);
    }

    @Override
    public FileHandle internal(String path) {
        return new HeadlessFileHandle(path, FileType.Local);
    }

    @Override
    public FileHandle external(String path) {
        return new HeadlessFileHandle(path, FileType.External);
    }

    @Override
    public FileHandle absolute(String path) {
        return new HeadlessFileHandle(path, FileType.Absolute);
    }

    @Override
    public FileHandle local(String path) {
        return new HeadlessFileHandle(path, FileType.Local);
    }

    @Override
    public String getExternalStoragePath() {
        return HeadlessFiles.externalPath;
    }

    @Override
    public boolean isExternalStorageAvailable() {
        return true;
    }

    @Override
    public String getLocalStoragePath() {
        return HeadlessFiles.localPath;
    }

    @Override
    public boolean isLocalStorageAvailable() {
        return true;
    }

    /**
     * Wow, why doesn't libGdx just give me an ability to change to localPath or to instantiate an implementation of
     * Files by passing a path?
     * <p>
     * Why do i have to do something like this for a really simple thing? *sigh*.
     * <p>
     * Btw, this method overrides {@link HeadlessFiles#localPath} with the given localPath.
     */
    @SneakyThrows
    private void fuckThatLocalPath(String localPath) {
        Field localPathField = HeadlessFiles.class.getDeclaredField("localPath");
        localPathField.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(localPathField, localPathField.getModifiers() & ~Modifier.FINAL);

        localPathField.set(null, localPath);
    }
}
