package it.unibo.crabinv.Model.audio;


public class JavaFXSoundManager implements SoundService {
    private static final JavaFXSoundManager instance = new JavaFXSoundManager();

    private JavaFXSoundManager() {}

    public static JavaFXSoundManager getInstance() {
        return instance;
    }

    @Override
    public void playBGM(String musicName) {

    }

    @Override
    public void stopBGM() {

    }

    @Override
    public void setBGMVolume(int volume) {

    }

    @Override
    public double getBGMVolume() {
        return 0;
    }

    @Override
    public void toggleMuteBGM() {

    }

    @Override
    public boolean isBGMMuted() {
        return false;
    }

    @Override
    public void playSfx(String effectName) {

    }

    @Override
    public void setSFXVolume(int volume) {

    }

    @Override
    public double getSFXVolume() {
        return 0;
    }

    @Override
    public void toggleMuteSFX() {

    }

    @Override
    public boolean isSFXMuted() {
        return false;
    }
}
