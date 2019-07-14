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

package li.yuri.openspacebox.realmdesigner.common.preferences;

import javafx.scene.control.ButtonType;
import li.yuri.workspacefx.application.Alert;
import li.yuri.workspacefx.application.WorkspaceFxApplication;
import lombok.Getter;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PreferencesManager {
    private static final Strategy SERIALIZATION_STRATEGY = new AnnotationStrategy();

    @Getter private static PreferencesManager instance = new PreferencesManager();

    private final Path filePath;
    private Preferences preferences;

    private PreferencesManager() {
        filePath = Paths.get(System.getProperty("user.home"), ".realmdesigner");
    }

    public void updatePreferences() {
        if (!Files.exists(filePath)) {
            preferences = new Preferences();
        } else {
            try {
                preferences = new Persister(SERIALIZATION_STRATEGY).read(
                        new Preferences(),
                        Files.newInputStream(filePath, StandardOpenOption.READ)
                );
            } catch (IOException e) {
                new Alert(
                        Alert.AlertType.WARNING,
                        "Unable to obtain read-access for the preferences file " + filePath.toString()
                                + ". Do you want to continue launching RealmDesigner?",
                        ButtonType.CANCEL, ButtonType.OK
                )
                        .showAndWait()
                        .filter(answer -> answer.equals(ButtonType.CANCEL))
                        .ifPresent(answer -> WorkspaceFxApplication.getInstance().get().exit());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void persistPreferences() {
        try {
            if (!Files.exists(filePath)) Files.createFile(filePath);
            new Persister(SERIALIZATION_STRATEGY).write(
                    preferences,
                    Files.newOutputStream(filePath, StandardOpenOption.WRITE)
            );
        } catch (Exception e) {
            new Alert(
                    javafx.scene.control.Alert.AlertType.WARNING,
                    "Unable to write the preferences file " + filePath.toString() + ".",
                    ButtonType.OK
            )
                    .showAndWait();
            e.printStackTrace();
        }
    }
}
