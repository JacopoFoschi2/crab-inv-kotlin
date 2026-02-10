package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Provides the method to show the game over screen
 */
public class GameOver {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final MESSAGE_TYPE messageType;

    public GameOver(SceneManager sceneManager, LocalizationController loc, AudioController audio, MESSAGE_TYPE messageType) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.messageType = messageType;
    }

    /**
     * @return the pane showing the game over screen
     */
    public Pane getView() {
        StackPane pane = new StackPane();
        VBox mainColumn = new VBox(100);
        Label message = new Label();
        message.getStyleClass().add("menu-title");
        if (messageType == MESSAGE_TYPE.VICTORY) {
            message.setText(loc.getString(TextKeys.VICTORY));
        } else {
            message.setText(loc.getString(TextKeys.GAME_OVER));
        }
        Button returnToMenu = new Button(loc.getString(TextKeys.RETURN_TO_MENU));
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
     * Provides the types of game overs there are
     */
    public enum MESSAGE_TYPE {
        GAME_OVER,
        VICTORY
    }
}
