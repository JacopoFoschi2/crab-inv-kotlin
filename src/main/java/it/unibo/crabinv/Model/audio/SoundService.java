package it.unibo.crabinv.Model.audio;

/**
 * Interface that handles all basic sound related operations
 */
public interface SoundService {
    void playBGM(String musicName);
    void stopBGM();
    void setBGMVolume(double volume);
    double getBGMVolume();
    void toggleMuteBGM();
    boolean isBGMMuted();
    void playSfx(String effectName);
    void setSFXVolume(double volume);
    double getSFXVolume();
    void toggleMuteSFX();
    boolean isSFXMuted();
}
