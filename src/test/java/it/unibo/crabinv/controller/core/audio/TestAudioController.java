package it.unibo.crabinv.controller.core.audio;

import it.unibo.crabinv.model.core.audio.BGMTracks;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.audio.SoundService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestAudioController {
    private SoundService mockSound;
    private AudioController controller;

    @BeforeEach
    void init() {
        mockSound = Mockito.mock(SoundService.class);
        controller = new AudioController(mockSound);
    }

    @Test
    void testCorrectSetup() {
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
    void testVolumeChange() {
        final double newModelVolume = 0.5;
        final int newControllerVolume = 50;
        controller.setBGMVolume(newControllerVolume);
        controller.setSFXVolume(newControllerVolume);
        Mockito.verify(mockSound).setBGMVolume(newModelVolume);
        Mockito.verify(mockSound).setSFXVolume(newModelVolume);
    }

    @Test
    void testToggleMute() {
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound).toggleMuteBGM();
        Mockito.verify(mockSound).toggleMuteSFX();
    }

    @Test
    void testPlayBGM() {
        controller.playBGM(BGMTracks.MENU);
        Mockito.verify(mockSound).playBGM(BGMTracks.MENU);
    }

    @Test
    void testPauseBGM() {
        controller.playBGM(BGMTracks.LEVEL);
        controller.pauseBGM();
        Mockito.verify(mockSound).pauseBGM();
    }

    @Test
    void testResumeBGM() {
        controller.playBGM(BGMTracks.LEVEL);
        controller.pauseBGM();
        controller.resumeBGM();
        Mockito.verify(mockSound).resumeBGM();
    }

    @Test
    void testInvalidVolumeThrows() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> controller.setBGMVolume(150)
        );
    }

    @Test
    void testInvalidVolumeDoesNotCallModel() {
        controller.setBGMVolume(-10);
        Mockito.verifyNoInteractions(mockSound);
    }

    @Test
    void testPlaySFX() {
        controller.playSFX(SFXTracks.EXPLOSION);
        Mockito.verify(mockSound).playSFX(SFXTracks.EXPLOSION);
    }

    @Test
    void testToggleMuteMultipleTimes() {
        controller.toggleBGMMute();
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound, Mockito.times(2)).toggleMuteBGM();
        Mockito.verify(mockSound, Mockito.times(2)).toggleMuteSFX();
    }

    @Test
    void testVolumeThenMute() {
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
    void testNullSoundServiceThrows() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> new AudioController(null)
        );
    }
}
