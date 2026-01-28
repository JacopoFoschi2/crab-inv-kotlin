package it.unibo.crabinv.View;

import it.unibo.crabinv.App;
import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MainMenu {
    /*
    needs to contain:
    -play
    -shop
    -Run Log
    -settings  -> volume and language settings
    -exit game
     */
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
        VBox mainColumn = new VBox(20);
        mainColumn.setAlignment(Pos.CENTER);

        mainColumn.getChildren().addAll(
                createMenuButton(TextKeys.PLAY, () -> System.out.println("PLAY")),
                createMenuButton(TextKeys.SHOP, () -> System.out.println("PLAY")),
                createMenuButton(TextKeys.RUN_LOG, () -> System.out.println("PLAY")),
                createMenuButton(TextKeys.SETTINGS, sceneManager::showSettings),
                createMenuButton(TextKeys.EXIT_GAME, () -> System.out.println("PLAY"))
        );
        pane.getChildren().add(mainColumn);
        return pane;
    }

    private Button createMenuButton(TextKeys key, Runnable action) {
        Label label = new Label(loc.getString(key));
        label.getStyleClass().add("menu-button-label");


        Image borderImage = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/uiImages/frameMenuButton.png")
        ));

        ImageView background = new ImageView(borderImage);
        background.setPreserveRatio(true);
        background.setFitWidth(300);

        StackPane composition = new StackPane(background,label); //composition of the image with the text

        Button menuButton = new Button();
        menuButton.setGraphic(composition);
        menuButton.getStyleClass().add("menu-button");
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
