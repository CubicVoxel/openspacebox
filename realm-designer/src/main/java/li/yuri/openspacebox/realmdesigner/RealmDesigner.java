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

package li.yuri.openspacebox.realmdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import javafx.stage.Stage;
import li.yuri.openspacebox.OpenSpaceBox;
import li.yuri.openspacebox.context.RealmDesignerContext;
import li.yuri.openspacebox.realmdesigner.common.ParametersResolver;
import li.yuri.openspacebox.realmdesigner.common.preferences.PreferencesManager;
import li.yuri.openspacebox.realmdesigner.util.ConfigurableFiles;
import li.yuri.openspacebox.realmdesigner.view.RealmDesignerViewType;
import li.yuri.workspacefx.application.ViewType;
import li.yuri.workspacefx.application.WorkspaceFxApplication;
import lombok.Getter;

import java.util.Map;

public class RealmDesigner extends WorkspaceFxApplication {

    private Map<String, String> parameters;
    @Getter private LwjglApplication backend;
    @Getter private RealmDesignerContext osbContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void beforeStart() {
        super.beforeStart();
        initParameters();
    }

    @Override
    protected void beforeShow() {
        PreferencesManager.getInstance().updatePreferences();
        initBackend();
        PreferencesManager.getInstance().persistPreferences();
    }

    @Override
    protected ViewType[] getViewTypes() {
        return RealmDesignerViewType.values();
    }

    private void initBackend() {
        osbContext = new RealmDesignerContext();
        backend = new LwjglApplication(new OpenSpaceBox(osbContext));
        Gdx.files = new ConfigurableFiles(parameters.get(ParametersResolver.PARAMETER_ASSETS_DIR));
    }

    private void initParameters() {
        ParametersResolver parametersResolver = new ParametersResolver(getParameters().getRaw());
        parameters = parametersResolver.resolveParameters();
    }

    @Override
    protected String[] getStylesheets() {
        return new String[]{"styles.css"};
    }

    @Override
    protected void configurePrimaryStage(Stage primaryStage) {
        primaryStage.setTitle("OpenSpaceBox RealmDesigner");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        PreferencesManager.getInstance().persistPreferences();
        backend.exit();
    }

}
