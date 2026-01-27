package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.SceneManager;
import javafx.scene.layout.Pane;

public class MainMenu {
    /*
    needs to contain:
    -new game
    -continue
    -shop? (actually here?)
    -settings
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
    
}
