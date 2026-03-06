package it.unibo.crabinv.model.core.audio;

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
public final class JavaFXSoundManager implements SoundService {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void playBGM(final BGMTracks music) {
        final String musicName = music.getPath();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeBGM() {
        if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            musicPlayer.play();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseBGM() {
        if (musicPlayer != null && musicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            musicPlayer.pause();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBGMVolume(final double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.bgmVolume = volume;
        if (musicPlayer != null) {
            musicPlayer.setVolume(volume);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBGMVolume() {
        return bgmVolume;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleMuteBGM() {
        isBGMMuted = !isBGMMuted;
        if (musicPlayer != null) {
            musicPlayer.setMute(isBGMMuted);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBGMMuted() {
        return isBGMMuted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSFX(final SFXTracks effect) {
        final String effectName = effect.getPath();
        handleCache(effectName, sfxCache, AudioClip::new);
        if (!isSFXMuted) {
            final AudioClip sfx = sfxCache.get(effectName);
            sfx.play(sfxVolume);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSFXVolume(final double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.sfxVolume = volume;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSFXVolume() {
        return sfxVolume;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleMuteSFX() {
        isSFXMuted = !isSFXMuted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSFXMuted() {
        return isSFXMuted;
    }

    /**
     * Handles the caching of either sound effects or music tracks.
     *
     * @param effectName the key of the track
     * @param cache a map used to cache all the tracks used at runtime
     * @param <T> the type of JavaFx sound construct you wish to create
     * @param create the method used to create the aforementioned construct
     */
    private <T> void handleCache(final String effectName, final Map<String, T> cache, final Function<String, T> create) {
        if (!cache.containsKey(effectName)) {
            final var resource = getClass().getResource(effectName);
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + effectName);
            }
            final T effect = create.apply(resource.toExternalForm());
            cache.put(effectName, effect);
        }
    }
}
