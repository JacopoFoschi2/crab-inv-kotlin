package it.unibo.crabinv.Controller.core.audio;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Model.core.audio.BGMTracks;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.audio.SoundService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestAudioController {
    private SoundService mockSound;
    private AudioController controller;

    @BeforeEach
    void init() {
        mockSound = Mockito.mock(SoundService.class);
        controller = new AudioController(mockSound);
    }

    @Test
    public void TestCorrectSetup() {
        Mockito.when(mockSound.isBGMMuted()).thenReturn(false);
        Mockito.when(mockSound.isSFXMuted()).thenReturn(false);
        Mockito.when(mockSound.getBGMVolume()).thenReturn(1.0);
        Mockito.when(mockSound.getSFXVolume()).thenReturn(1.0);

        Assertions.assertFalse(controller.isBGMMuted());
        Assertions.assertFalse(controller.isSFXMuted());
        Assertions.assertEquals(100, controller.getBGMVolume());
        Assertions.assertEquals(100, controller.getSFXVolume());
    }

    @Test
    public void TestVolumeChange() {
        double newModelVolume = 0.5;
        int newControllerVolume = 50;
        controller.setBGMVolume(newControllerVolume);
        controller.setSFXVolume(newControllerVolume);
        Mockito.verify(mockSound).setBGMVolume(newModelVolume);
        Mockito.verify(mockSound).setSFXVolume(newModelVolume);
    }

    @Test
    public void TestToggleMute() {
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound).toggleMuteBGM();
        Mockito.verify(mockSound).toggleMuteSFX();
    }

    @Test
    public void testPlayBGM() {
        controller.playBGM(BGMTracks.MENU);
        Mockito.verify(mockSound).playBGM(BGMTracks.MENU);
    }

    @Test
    public void testPauseBGM() {
        controller.playBGM(BGMTracks.LEVEL);
        controller.pauseBGM();
        Mockito.verify(mockSound).pauseBGM();
    }

    @Test
    public void testResumeBGM() {
        controller.playBGM(BGMTracks.LEVEL);
        controller.pauseBGM();
        controller.resumeBGM();
        Mockito.verify(mockSound).resumeBGM();
    }

    @Test
    public void testInvalidVolumeThrows() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> controller.setBGMVolume(150)
        );
    }

    @Test
    public void testInvalidVolumeDoesNotCallModel() {
        try {
            controller.setBGMVolume(-10);
        } catch (Exception ignored) {}

        Mockito.verifyNoInteractions(mockSound);
    }

    @Test
    public void testPlaySFX() {
        controller.playSFX(SFXTracks.EXPLOSION);
        Mockito.verify(mockSound).playSFX(SFXTracks.EXPLOSION);
    }

    @Test
    public void testToggleMuteMultipleTimes() {
        controller.toggleBGMMute();
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound, Mockito.times(2)).toggleMuteBGM();
        Mockito.verify(mockSound, Mockito.times(2)).toggleMuteSFX();
    }

    @Test
    public void testVolumeThenMute() {
        controller.setBGMVolume(80);
        controller.setSFXVolume(60);
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound).setBGMVolume(0.8);
        Mockito.verify(mockSound).setSFXVolume(0.6);
        Mockito.verify(mockSound).toggleMuteBGM();
        Mockito.verify(mockSound).toggleMuteSFX();
    }

    @Test
    public void testNullSoundServiceThrows() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new AudioController(null)
        );
    }
}
