package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.i18n.Localization;
import it.unibo.crabinv.View.LanguageSelection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        Scene mainScene;
        LocalizationController loc = new LocalizationController(new Localization());
        AudioController audio = new AudioController(new JavaFXSoundManager());

        StackPane root = new StackPane();
        mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm()
        );
        mainStage.setScene(mainScene);
        mainStage.setTitle("Crab Invaders");
        LanguageSelection l = new LanguageSelection();
        root.getChildren().add(l.getView(loc));
        mainStage.show();
    }

    public static class Main {
        public static void main(String... args) {
            Application.launch(App.class, args);
        }
    }
}
