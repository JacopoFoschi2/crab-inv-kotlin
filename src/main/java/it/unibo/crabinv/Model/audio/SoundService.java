package it.unibo.crabinv.Model.audio;

/**
 * Interface that handles all basic sound related operations
 */
public interface SoundService {
    void playBGM(BGMTracks musicName);

    /**
     * Starts playing the song if it was stopped
     */
    void startBGM();

    /**
     * Stops the current song
     */
    void pauseBGM();
    void setBGMVolume(double volume);
    double getBGMVolume();
    void toggleMuteBGM();
    boolean isBGMMuted();
    void playSfx(SFXTracks effectName);
    void setSFXVolume(double volume);
    double getSFXVolume();
    void toggleMuteSFX();
    boolean isSFXMuted();
}
