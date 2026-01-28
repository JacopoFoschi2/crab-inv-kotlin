package it.unibo.crabinv.Model.audio;

/**
 * Provides all the basic sound apis
 */
public interface SoundService {

    /**
     * Disposes of currently playing music if there's any and starts playing the new track,
     * does nothing if the new track is the same as the previous one
     * @param musicName the track you wish to play from {@link BGMTracks}
     */
    void playBGM(BGMTracks musicName);

    /**
     * Starts playing the song if it was stopped
     */
    void resumeBGM();

    /**
     * Pauses the current song
     */
    void pauseBGM();

    /**
     * sets the volume of current and future playing music
     * @param volume a double value between 0.0 and 1.0
     * @throws IllegalArgumentException if the value inputted isn't between 0.0 and 1.0
     */
    void setBGMVolume(double volume);

    /**
     * @return The currently set BGM volume
     */
    double getBGMVolume();

    /**
     * Toggles if BGM is mute
     */
    void toggleMuteBGM();

    /**
     * @return if BGM is mute
     */
    boolean isBGMMuted();

    /**
     * Plays the selected sound effect
     * @param effectName the sound effect you wish to play from {@link SFXTracks}
     */
    void playSFX(SFXTracks effectName);

    /**
     * sets the volume of sound effects
     * @param volume a double value between 0.0 and 1.0
     * @throws IllegalArgumentException if the value inputted isn't between 0.0 and 1.0
     */
    void setSFXVolume(double volume);

    /**
     * @return The currently set SFX volume
     */
    double getSFXVolume();

    /**
     * Toggles if SFX are muted
     */
    void toggleMuteSFX();

    /**
     * @return if SFX are muted
     */
    boolean isSFXMuted();
}
