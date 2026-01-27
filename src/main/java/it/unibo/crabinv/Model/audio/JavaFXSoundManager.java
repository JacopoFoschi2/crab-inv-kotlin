package it.unibo.crabinv.Model.audio;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Provides an implementation of {@link SoundService} using JavaFX.
 */
public class JavaFXSoundManager implements SoundService {
    private double bgmVolume;
    private double sfxVolume;
    private boolean isBGMMuted;
    private boolean isSFXMuted;
    private MediaPlayer musicPlayer;
    private String currentTrack;
    private final Map<String, Media> bgmCache = new HashMap<>();
    private final Map<String, AudioClip> sfxCache = new HashMap<>();

    /**
     * Creates the SoundManager ensuring a coherent initial state.
     */
    public JavaFXSoundManager() {
        setBGMVolume(1.0);
        setSFXVolume(1.0);
        isBGMMuted = false;
        isSFXMuted = false;
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
        handleCache(musicName, bgmCache, Media::new);
        musicPlayer = new MediaPlayer(bgmCache.get(musicName));
        currentTrack = musicName;
        musicPlayer.setVolume(bgmVolume);
        musicPlayer.setMute(isBGMMuted);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }

    @Override
    public void resumeBGM() {
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
    public void playSfx(SFXTracks effect) {
        String effectName = effect.getPath();
        handleCache(effectName, sfxCache, AudioClip::new);
        if(!isSFXMuted) {
            AudioClip sfx = sfxCache.get(effectName);
            sfx.play(sfxVolume);
        }
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

    /**
     * Handles the caching of either sound effects or music tracks
     * @param effectName the key of the track
     * @param cache a map used to cache all the tracks used at runtime
     * @param <T> the type of JavaFx sound construct you wish to create
     * @param create the method used to create the aforementioned construct
     */
    private <T> void handleCache(String effectName, Map<String, T> cache, Function<String, T> create) {
        if (!cache.containsKey(effectName)) {
            var resource = getClass().getResource(effectName);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + effectName);
            }
            T effect = create.apply(resource.toExternalForm());
            cache.put(effectName, effect);
        }
    }
}
