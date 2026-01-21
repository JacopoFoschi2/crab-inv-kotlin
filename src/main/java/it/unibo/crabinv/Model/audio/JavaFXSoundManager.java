package it.unibo.crabinv.Model.audio;


public class JavaFXSoundManager implements SoundService {
    private static final JavaFXSoundManager instance = new JavaFXSoundManager();
    private int bgmVolume;
    private int sfxVolume;
    private boolean isBGMuted;
    private boolean isSFXMuted;

    private JavaFXSoundManager() {
        setBGMVolume(100);
        setSFXVolume(100);
        isBGMuted = false;
        isSFXMuted = false;
    }

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
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.bgmVolume = volume;
    }

    @Override
    public double getBGMVolume() {
        return bgmVolume;
    }

    @Override
    public void toggleMuteBGM() {
        isBGMuted = !isBGMuted;
    }

    @Override
    public boolean isBGMMuted() {
        return isBGMuted;
    }

    @Override
    public void playSfx(String effectName) {

    }

    @Override
    public void setSFXVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.sfxVolume = volume;
    }

    @Override
    public double getSFXVolume() {
        return sfxVolume;
    }

    @Override
    public void toggleMuteSFX() {
        isSFXMuted = !isSFXMuted;
    }

    @Override
    public boolean isSFXMuted() {
        return isSFXMuted;
    }
}
