package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.save.PlayerMemorial;
import it.unibo.crabinv.Model.save.UserProfile;
import it.unibo.crabinv.SceneManager;

public class MemorialScreen {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final PlayerMemorial playerMemorial;

    public MemorialScreen(SceneManager sceneManager, LocalizationController loc, AudioController audio, PlayerMemorial playerMemorial) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.playerMemorial = playerMemorial;
    }

}
