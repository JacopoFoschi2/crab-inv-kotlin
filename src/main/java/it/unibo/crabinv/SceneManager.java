package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.View.LanguageSelection;
import javafx.scene.layout.StackPane;

public class SceneManager {
    private final StackPane root;
    private final LocalizationController loc;
    private final AudioController audio;

    public SceneManager(StackPane root, LocalizationController loc, AudioController audio) {
        this.root = root;
        this.loc = loc;
        this.audio = audio;
    }

    public void showLanguageSelection() {
        root.getChildren().setAll(new LanguageSelection(loc,this).getView());
    }

    public void showMainMenu() {
        root.getChildren().setAll(new StackPane());
    }
}
