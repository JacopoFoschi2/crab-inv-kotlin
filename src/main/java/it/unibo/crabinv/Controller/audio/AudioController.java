package it.unibo.crabinv.Controller.audio;

import it.unibo.crabinv.Model.audio.BGMTracks;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.audio.SoundService;

/**
 * Controls all music execution
 */
public class AudioController {
    private final SoundService soundManager;

    /**
     * Creates the instance of AudioController
     * @param soundManager the implementation of SoundManager you want to use
     */
    public AudioController(final SoundService soundManager) {
        this.soundManager = soundManager;
    }

    /**
     * Plays the music
     */
    public void playBGM(BGMTracks bgm) {
        soundManager.playBGM(bgm);
    }

    /**
     * Pauses the currently playing track for a later resume.
     * Use it in instances such as the pause menu to pause the main music
     */
    public void pauseMusic() {
        soundManager.pauseBGM();
    }

    /**
     * Resumes the currently playing track that was previously paused.
     * Use it in instances such as when you resume the game after it was paused.
     */
    public void resumeMusic() {
        soundManager.startBGM();
    }

    /**
     * Sets the music volume to the desired value
     * @param volume spans from 0 to 100
     */
    public void setMusicVolume(int volume) {
        double realVolume = (double) volume / 100;
        soundManager.setBGMVolume(realVolume);
    }

    /**
     * Toggles if the music should be mute or not
     */
    public void toggleMusicMute() {
        soundManager.toggleMuteBGM();
    }

    /**
     * @return if the music is currently muted or not
     */
    public boolean isMusicMuted() {
        return soundManager.isBGMMuted();
    }

    /**
     * Plays sound effects
     */
    public void playSFX(SFXTracks sfx) {
        soundManager.playSfx(sfx);
    }

    /**
     * Sets the sound effects volume to the desired value
     * @param volume spans from 0 to 100
     */
    public void setSFXVolume(int volume) {
        double realVolume = (double) volume / 100;
        soundManager.setSFXVolume(realVolume);
    }

    /**
     * Toggles if the sound effects should be muted or not
     */
    public void toggleSFXMute() {
        soundManager.toggleMuteSFX();
    }

    /**
     * @return if sound effects are currently muted
     */
    public boolean isSFXMuted() {
        return soundManager.isSFXMuted();
    }
}
