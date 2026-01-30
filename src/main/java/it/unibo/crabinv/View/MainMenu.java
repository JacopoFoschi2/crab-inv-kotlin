package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MainMenu {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;

    public MainMenu(SceneManager sceneManager, LocalizationController loc, AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    public Pane getView() {
        Pane pane = new StackPane();
        VBox mainColumn = new VBox(30);
        mainColumn.setAlignment(Pos.CENTER);


        Label title = new Label("Crab Invaders");
        title.getStyleClass().add("menu-title");

        mainColumn.getChildren().addAll(
                title,
                createMenuButton(TextKeys.PLAY, () -> System.out.println("PLAY")),
                createMenuButton(TextKeys.SHOP, () -> System.out.println("SHOP")),
                createMenuButton(TextKeys.RUN_LOG, () -> System.out.println("RUN_LOG")),
                createMenuButton(TextKeys.SETTINGS, sceneManager::showSettings),
                createMenuButton(TextKeys.EXIT_GAME, Platform::exit)
        );
        pane.getChildren().add(mainColumn);
        return pane;
    }

    private Button createMenuButton(TextKeys key, Runnable action) {
        Button menuButton = new Button(loc.getString(key));
        menuButton.getStyleClass().add("menu-button");

        menuButton.setMinWidth(220);

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
