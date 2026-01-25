package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.i18n.Localization;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.initStyle(StageStyle.UNDECORATED); // rimuove barra e bordi
        mainStage.setMaximized(true);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene mainScene;
        StackPane root = new StackPane();
        mainScene = new Scene(root);
        LocalizationController loc = new LocalizationController(new Localization());
        AudioController audio = new AudioController(new JavaFXSoundManager());
        SceneManager manager = new SceneManager(root, loc, audio, bounds);

        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm()
        );
        manager.showLanguageSelection();
        mainScene.setCursor(Cursor.NONE);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Crab Invaders");
        mainStage.setResizable(false);
        mainStage.show();
    }

    public static class Main {
        static void main(String... args) {
            Application.launch(App.class, args);
        }
    }
}
