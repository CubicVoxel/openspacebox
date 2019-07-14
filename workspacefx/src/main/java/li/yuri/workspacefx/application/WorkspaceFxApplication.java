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

package li.yuri.workspacefx.application;

import javafx.application.Application;
import javafx.stage.Stage;
import li.yuri.workspacefx.style.StyleSheets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Using a {@link WorkspaceFxApplication} instead of an {@link Application} provides you with a view-management for your
 * {@link ViewType}s, including a {@link li.yuri.workspacefx.application.Menu}. It is set up on a WorkspaceFX-{@link
 * Scene}, resulting in a dark theme and stylesheets you defined via {@link #getStylesheets()} get added automatically
 * to all {@link Scene}s.
 * <p>
 * If you just want to make use of the other components of WorkspaceFX, feel free to ignore this class.
 * <p>
 * Example:
 * <pre>
 * <code>
 *     public class RealmDesigner extends WorkspaceFxApplication {
 *
 *          public static void main(String[] args) {
 *              launch(args);
 *          }
 *
 *         {@literal @}Override
 *          protected void beforeStart() {
 *              super.beforeStart();
 *              initParameters();
 *              initBackend();
 *          }
 *
 *         {@literal @}Override
 *          protected ViewType[] getViewTypes() {
 *              return RealmDesignerViewType.values();
 *          }
 *
 *         {@literal @}Override
 *          protected void configurePrimaryStage(Stage primaryStage) {
 *              primaryStage.setTitle("OpenSpaceBox RealmDesigner");
 *              primaryStage.setMinHeight(500);
 *              primaryStage.setMinWidth(800);
 *          }
 *
 *         {@literal @}Override
 *          protected String[] getStylesheets() {
 *              return new String[]{"styles.css"};
 *          }
 *
 *     }
 * </code>
 * </pre>
 */
public abstract class WorkspaceFxApplication extends Application {

    private static WorkspaceFxApplication instance;
    private RootLayout rootLayout;

    public static Optional<WorkspaceFxApplication> getInstance() {
        return Optional.ofNullable(instance);
    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        instance = this;
        beforeStart();

        rootLayout = new RootLayout(getViewTypes());

        Scene scene = new Scene(rootLayout);

        primaryStage.setScene(scene);
        configurePrimaryStage(primaryStage);

        beforeShow();
        primaryStage.show();
    }

    /**
     * Gets called right before the scene is shown, but after the {@link #rootLayout} is built up. Override it to do
     * stuff before the application starts.
     */
    protected void beforeShow() {

    }

    /**
     * Gets called right before the scene is built up. Override it to do stuff before the application starts.
     */
    protected void beforeStart() {
        // Optional hook
    }

    /**
     * Override this to get your stylesheets added to all {@link Scene}s automatically
     *
     * @return resource paths of stylesheets
     */
    protected String[] getStylesheets() {
        return new String[]{};
    }

    /**
     * Returns preconfigured WorkspaceFX-Stylesheets and all application-specific ones.
     */
    public static Collection<String> getAllStylesheets() {

        Stream<String> wfxStyleSheets = StyleSheets.getAllUris()
                .map(x -> WorkspaceFxApplication.class.getResource(x)
                        .toExternalForm()
                );
        Stream<String> userStyleSheets = WorkspaceFxApplication.getInstance()
                .map(wfx -> Arrays.stream(wfx.getStylesheets()))
                .orElse(Stream.empty()
                );
        return Stream.concat(wfxStyleSheets, userStyleSheets).collect(Collectors.toList());
    }

    /**
     * Return any views you application my have. If you use a {@link WorkspaceFxApplication}, you have to obey to the
     * view-structure.
     * <p>
     * Of course, you can collect your views in an enum like this:
     * <pre>
     * <code>
     * {@literal @}AllArgsConstructor
     *  public enum RealmDesignerViewType implements ViewType {
     *      DEFINITION_EDITOR("Edit Definitions", DefinitionEditorView::new),
     *      TEXTURE_PACKER("Pack Textures", TexturePackerView::new);
     *
     *     {@literal @}Getter private final String title;
     *     {@literal @}Getter private final Supplier&lt;View&gt; instanceSupplier;
     *  }
     * </code>
     * </pre>
     * <p>
     * ... so an implementation of this method would look like this:
     * <pre>
     * <code>
     *     {@literal @}Override
     *      protected ViewType[] getViewTypes() {
     *          return RealmDesignerViewType.values();
     *      }
     * </code>
     * </pre>
     *
     * @return viewTypes
     */
    protected abstract ViewType[] getViewTypes();

    /**
     * Configure the primaryStage to your needs.
     *
     * @param primaryStage the primary stage
     */
    protected abstract void configurePrimaryStage(Stage primaryStage);

    public RootLayout getRootLayout() {
        return rootLayout;
    }

    public void exit() {
        System.exit(0);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        rootLayout.onStop();
    }
}
