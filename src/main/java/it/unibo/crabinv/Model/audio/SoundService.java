package it.unibo.crabinv.Model.audio;

/**
 * Interface that handles all basic sound related operations
 */
public interface SoundService {
    void playBGM(String musicName);
    void stopBGM();
    void setBGMVolume(int volume);
    double getBGMVolume();
    void toggleMuteBGM();
    boolean isBGMMuted();
    void playSfx(String effectName);
    void setSFXVolume(int volume);
    double getSFXVolume();
    void toggleMuteSFX();
    boolean isSFXMuted();
}
