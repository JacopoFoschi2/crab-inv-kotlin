package it.unibo.crabinv.Model.audio;

public interface SoundService {
    void playBGM();
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
