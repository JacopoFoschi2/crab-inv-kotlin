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
        final boolean defaultMute = false;
        final double defaultVolume = 1.0;
        Mockito.when(mockSound.isBGMMuted()).thenReturn(defaultMute);
        Mockito.when(mockSound.isSFXMuted()).thenReturn(defaultMute);
        Mockito.when(mockSound.getBGMVolume()).thenReturn(defaultVolume);
        Mockito.when(mockSound.getSFXVolume()).thenReturn(defaultVolume);

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
        final int tryVolume = 150;
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> controller.setBGMVolume(tryVolume)
        );
    }

    @Test
    void testInvalidVolumeDoesNotCallModel() {
        final int tryVolume = -10;
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> controller.setBGMVolume(tryVolume));
        Mockito.verifyNoInteractions(mockSound);
    }

    @Test
    void testPlaySFX() {
        controller.playSFX(SFXTracks.EXPLOSION);
        Mockito.verify(mockSound).playSFX(SFXTracks.EXPLOSION);
    }

    @Test
    void testToggleMuteMultipleTimes() {
        final int wantedInvocations = 2;
        controller.toggleBGMMute();
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound, Mockito.times(wantedInvocations)).toggleMuteBGM();
        Mockito.verify(mockSound, Mockito.times(wantedInvocations)).toggleMuteSFX();
    }

    @Test
    void testVolumeThenMute() {
        final int firstValue = 80;
        final int secondValue = 60;
        final double firstCorrective = (double) firstValue / 100;
        final double secondCorrective = (double) secondValue / 100;
        controller.setBGMVolume(firstValue);
        controller.setSFXVolume(secondValue);
        controller.toggleBGMMute();
        controller.toggleSFXMute();
        Mockito.verify(mockSound).setBGMVolume(firstCorrective);
        Mockito.verify(mockSound).setSFXVolume(secondCorrective);
        Mockito.verify(mockSound).toggleMuteBGM();
        Mockito.verify(mockSound).toggleMuteSFX();
    }
}
