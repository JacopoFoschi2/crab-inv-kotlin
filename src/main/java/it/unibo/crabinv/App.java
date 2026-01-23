package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Model.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.i18n.Localization;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Localization loc = new Localization();
        AudioController audio = new AudioController(new JavaFXSoundManager());
    }

    static void main() {
        launch();
    }
}
