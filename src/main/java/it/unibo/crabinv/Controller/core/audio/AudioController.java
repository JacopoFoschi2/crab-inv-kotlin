package it.unibo.crabinv.Controller.core.audio;

import it.unibo.crabinv.Model.core.audio.BGMTracks;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.audio.SoundService;

/**
 * Provides a controller facade that lets you utilize any implementation of {@link SoundService} regardless of the library used.
 */
public class AudioController {
    private final SoundService soundManager;

    /**
     * Creates the instance of AudioController.
     *
     * @param soundManager the implementation of SoundManager you want to use
     * @throws NullPointerException if you feed it a null value
     */
    public AudioController(final SoundService soundManager) {
        if (soundManager == null) {
            throw new NullPointerException("soundManager == null");
        }
        this.soundManager = soundManager;
    }

    /**
     * Plays the music.
     *
     * @param bgm the wanted track to play
     */
    public void playBGM(final BGMTracks bgm) {
        soundManager.playBGM(bgm);
    }

    /**
     * Pauses the currently playing track for a later resume.
     * Use it in instances such as the pause menu to pause the main music
     */
    public void pauseBGM() {
        soundManager.pauseBGM();
    }

    /**
     * Resumes the currently playing track that was previously paused.
     * Use it in instances such as when you resume the game after it was paused.
     */
    public void resumeBGM() {
        soundManager.resumeBGM();
    }

    /**
     * Sets the music volume to the desired value.
     *
     * @param volume spans from 0 to 100
     * @throws IllegalArgumentException if the volume inputted is incorrect
     */
    public void setBGMVolume(final int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        final double realVolume = (double) volume / 100;
        soundManager.setBGMVolume(realVolume);
    }

    /**
     * @return the current BGM volume
     */
    public int getBGMVolume() {
        double volume = soundManager.getBGMVolume();
        return (int) (volume * 100);
    }

    /**
     * Toggles if the music should be mute or not.
     */
    public void toggleBGMMute() {
        soundManager.toggleMuteBGM();
    }

    /**
     * @return if the music is currently muted or not
     */
    public boolean isBGMMuted() {
        return soundManager.isBGMMuted();
    }

    /**
     * Plays sound effects.
     *
     * @param sfx the track you want to play
     */
    public void playSFX(final SFXTracks sfx) {
        soundManager.playSFX(sfx);
    }

    /**
     * Sets the sound effects volume to the desired value.
     *
     * @param volume spans from 0 to 100
     * @throws IllegalArgumentException if the volume inputted is incorrect
     */
    public void setSFXVolume(final int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        double realVolume = (double) volume / 100;
        soundManager.setSFXVolume(realVolume);
    }

    /**
     * @return the current SFX volume
     */
    public int getSFXVolume() {
        double volume = soundManager.getSFXVolume();
        return (int) (volume * 100);
    }

    /**
     * Toggles if the sound effects should be muted or not.
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
