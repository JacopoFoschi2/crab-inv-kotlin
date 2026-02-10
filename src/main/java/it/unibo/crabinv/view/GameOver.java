package it.unibo.crabinv.view;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Provides the method to show the game over screen.
 */
public class GameOver {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final MessageTypes messageType;

    /**
     * Creates a new GameOver screen.
     *
     * @param sceneManager the instance of sceneManager for page change
     * @param loc the instance of loc for string fetch
     * @param audio the instance of audio for sounds
     * @param messageType the message type, either victory or game over
     */
    public GameOver(
            final SceneManager sceneManager,
            final LocalizationController loc,
            final AudioController audio,
            final MessageTypes messageType) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.messageType = messageType;
    }

    /**
     * @return the pane showing the game over screen.
     */
    public Pane getView() {
        final StackPane pane = new StackPane();
        final VBox mainColumn = new VBox(100);
        final Label message = new Label();
        message.getStyleClass().add("menu-title");
        if (messageType == MessageTypes.VICTORY) {
            message.setText(loc.getString(TextKeys.VICTORY));
        } else {
            message.setText(loc.getString(TextKeys.GAME_OVER));
        }
        final Button returnToMenu = new Button(loc.getString(TextKeys.RETURN_TO_MENU));
        returnToMenu.getStyleClass().add("app-button");
        returnToMenu.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        returnToMenu.setOnAction(e -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        mainColumn.getChildren().addAll(message, returnToMenu);
        mainColumn.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(mainColumn);
        return pane;
    }

    /**
     * Provides the types of game overs there are.
     */
    public enum MessageTypes {
        GAME_OVER,
        VICTORY
    }
}
