package it.unibo.crabinv.view;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.SceneManager;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * This is the view of the Main Menu.
 * Part of this was adapted from LLM by Mosè Barbieri
 */
public final class MainMenu {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;

    /**
     * It's the contstructor of the main menu.
     *
     * @param sceneManager it's the manager of the scenes, it moves them when needed
     * @param loc the needed manager for the translation
     * @param audio the audio manager needed for the sounds of the buttons
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public MainMenu(final SceneManager sceneManager,
                    final LocalizationController loc,
                    final AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    /**
     * it's the getter of the view of the menu.
     *
     * @return the view of the menu
     */
    public Pane getView() {
        final Pane pane = new StackPane();
        final VBox mainColumn = new VBox(ViewParameters.DEFAULT_SPACING_MAINMENU);
        mainColumn.setAlignment(Pos.CENTER);

        final Label title = new Label("Crab Invaders");
        title.getStyleClass().add("menu-title");

        mainColumn.getChildren().addAll(
                title,
                createMenuButton(TextKeys.PLAY, sceneManager::showGame),
                createMenuButton(TextKeys.SHOP, sceneManager::showShop),
                createMenuButton(TextKeys.RUN_LOG, sceneManager::showMemorial),
                createMenuButton(TextKeys.SETTINGS, sceneManager::showSettings),
                createMenuButton(TextKeys.EXIT_GAME, Platform::exit)
        );
        pane.getChildren().add(mainColumn);
        return pane;
    }

    private Button createMenuButton(final TextKeys key, final Runnable action) {
        final Button menuButton = new Button(loc.getString(key));
        menuButton.getStyleClass().add("app-button");

        menuButton.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                audio.playSFX(SFXTracks.MENU_HOVER);
            }
        });

        menuButton.setOnAction(_ -> {
            audio.playSFX(SFXTracks.MENU_SELECT);
            action.run();
        });

        return menuButton;
    }

}
