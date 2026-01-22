package it.unibo.crabinv.Model.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Map;
import java.util.Objects;

public class JavaFXSoundManager implements SoundService {
    private static final JavaFXSoundManager instance = new JavaFXSoundManager();
    private double bgmVolume;
    private double sfxVolume;
    private boolean isBGMMuted;
    private boolean isSFXMuted;
    private MediaPlayer musicPlayer;
    private String currentTrack;
    private Map<String, Media> bgmCache;

    private JavaFXSoundManager() {
        setBGMVolume(1.0);
        setSFXVolume(1.0);
        isBGMMuted = false;
        isSFXMuted = false;
    }

    public static JavaFXSoundManager getInstance() {
        return instance;
    }

    @Override
    public void playBGM(BGMTracks music) {
        String musicName = music.getPath();
        if (musicPlayer != null) {
            if (Objects.equals(currentTrack, musicName)) {
                return;
            }
            musicPlayer.stop();
            musicPlayer.dispose();
            musicPlayer = null;
        }
        if (!bgmCache.containsKey(musicName)) {
            var resource = getClass().getResource(musicName);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + musicName);
            }
            Media media = new Media(resource.toExternalForm());
            bgmCache.put(musicName, media);

        }
        musicPlayer = new MediaPlayer(bgmCache.get(musicName));
        currentTrack = musicName;
        musicPlayer.setVolume(bgmVolume);
        musicPlayer.setMute(isBGMMuted);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

    @Override
    public void startBGM() {
        if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            musicPlayer.play();
        }
    }

    @Override
    public void pauseBGM() {
        if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            musicPlayer.pause();
        }
    }

    @Override
    public void setBGMVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.bgmVolume = volume;
        if (musicPlayer != null) {
            musicPlayer.setVolume(volume);
        }
    }

    @Override
    public double getBGMVolume() {
        return bgmVolume;
    }

    @Override
    public void toggleMuteBGM() {
        isBGMMuted = !isBGMMuted;
        if (musicPlayer != null) {
            musicPlayer.setMute(isBGMMuted);
        }
    }

    @Override
    public boolean isBGMMuted() {
        return isBGMMuted;
    }

    @Override
    public void playSfx(SFXTracks effectName) {

    }

    @Override
    public void setSFXVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
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
