package it.unibo.crabinv.Model.audio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJavaFXSoundManager {
    private final JavaFXSoundManager soundManager = JavaFXSoundManager.getInstance();

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
        Assertions.assertEquals(100,  soundManager.getBGMVolume());
        soundManager.setBGMVolume(50);
        Assertions.assertEquals(50, soundManager.getBGMVolume());
    }

    @Test
    public void testSFXVolumeChange() {
        Assertions.assertEquals(100,  soundManager.getSFXVolume());
        soundManager.setSFXVolume(50);
        Assertions.assertEquals(50, soundManager.getSFXVolume());
    }
}
