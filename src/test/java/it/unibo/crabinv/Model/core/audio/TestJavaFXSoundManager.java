package it.unibo.crabinv.Model.core.audio;

import it.unibo.crabinv.Model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.core.audio.SoundService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJavaFXSoundManager {
    private final SoundService soundManager = new JavaFXSoundManager();
    private final double DEFAULT_VOLUME = 1.0;
    private final double TEST_VOLUME = 0.5;

    @Test
    public void testBGMMute() {
        Assertions.assertFalse(soundManager.isBGMMuted());
        soundManager.toggleMuteBGM();
        Assertions.assertTrue(soundManager.isBGMMuted());
    }

    @Test
    public void testSFXMute() {
        Assertions.assertFalse(soundManager.isSFXMuted());
        soundManager.toggleMuteSFX();
        Assertions.assertTrue(soundManager.isSFXMuted());
    }

    @Test
    public void testBGMVolumeChange() {
        Assertions.assertEquals(DEFAULT_VOLUME,  soundManager.getBGMVolume());
        soundManager.setBGMVolume(TEST_VOLUME);
        Assertions.assertEquals(TEST_VOLUME, soundManager.getBGMVolume());
    }

    @Test
    public void testSFXVolumeChange() {
        Assertions.assertEquals(DEFAULT_VOLUME,  soundManager.getSFXVolume());
        soundManager.setSFXVolume(TEST_VOLUME);
        Assertions.assertEquals(TEST_VOLUME, soundManager.getSFXVolume());
    }
}
