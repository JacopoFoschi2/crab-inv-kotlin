package it.unibo.crabinv.Controller.audio;

import it.unibo.crabinv.Model.audio.BGMTracks;
import it.unibo.crabinv.Model.audio.JavaFXSoundManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAudioController {
    private final AudioController soundService = new AudioController(new JavaFXSoundManager());

    @Test
    public void TestCorrectSetup() {
        Assertions.assertFalse(soundService.isBGMMuted());
        Assertions.assertFalse(soundService.isSFXMuted());
        Assertions.assertEquals(100, soundService.getBGMVolume());
        Assertions.assertEquals(100, soundService.getSFXVolume());
    }

    @Test
    public void TestVolumeChange() {
        int newVolume = 50;
        soundService.setBGMVolume(newVolume);
        soundService.setSFXVolume(newVolume);
        Assertions.assertEquals(newVolume, soundService.getBGMVolume());
        Assertions.assertEquals(newVolume, soundService.getSFXVolume());
    }

    @Test
    public void TestToggleMute() {
        soundService.toggleBGMMute();
        soundService.toggleSFXMute();
        Assertions.assertTrue(soundService.isBGMMuted());
        Assertions.assertTrue(soundService.isSFXMuted());
    }
}
