package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.SceneManager;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class PauseMenu {
    private final LocalizationController loc;
    private final AudioController audio;

    public PauseMenu(LocalizationController loc, AudioController audio) {
        this.loc = loc;
        this.audio = audio;
    }

    public Pane getView() {
        StackPane pane = new StackPane();
        return pane;
    }
}
