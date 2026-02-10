package it.unibo.crabinv.model.core.audio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestJavaFXSoundManager {
    private final SoundService soundManager = new JavaFXSoundManager();
    private final double defaultVolume = 1.0;
    private final double testVolume = 0.5;

    @Test
    void testBGMMute() {
        Assertions.assertFalse(soundManager.isBGMMuted());
        soundManager.toggleMuteBGM();
        Assertions.assertTrue(soundManager.isBGMMuted());
    }

    @Test
    void testSFXMute() {
        Assertions.assertFalse(soundManager.isSFXMuted());
        soundManager.toggleMuteSFX();
        Assertions.assertTrue(soundManager.isSFXMuted());
    }

    @Test
    void testBGMVolumeChange() {
        Assertions.assertEquals(defaultVolume,  soundManager.getBGMVolume());
        soundManager.setBGMVolume(testVolume);
        Assertions.assertEquals(testVolume, soundManager.getBGMVolume());
    }

    @Test
    void testSFXVolumeChange() {
        Assertions.assertEquals(defaultVolume,  soundManager.getSFXVolume());
        soundManager.setSFXVolume(testVolume);
        Assertions.assertEquals(testVolume, soundManager.getSFXVolume());
    }
}
