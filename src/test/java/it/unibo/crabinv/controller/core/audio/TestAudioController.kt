package it.unibo.crabinv.controller.core.audio

import it.unibo.crabinv.model.core.audio.BGMTracks
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.audio.SoundService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class TestAudioController {
    private var mockSound: SoundService? = null
    private var controller: AudioController? = null

    @BeforeEach
    fun init() {
        mockSound = Mockito.mock(SoundService::class.java)
        controller = AudioController(mockSound)
    }

    @Test
    fun testCorrectSetup() {
        val defaultMute = false
        val defaultVolume = 1.0
        Mockito.`when`(mockSound!!.isBGMMuted()).thenReturn(defaultMute)
        Mockito.`when`(mockSound!!.isSFXMuted()).thenReturn(defaultMute)
        Mockito.`when`(mockSound!!.getBGMVolume()).thenReturn(defaultVolume)
        Mockito.`when`(mockSound!!.getSFXVolume()).thenReturn(defaultVolume)

        Assertions.assertFalse(controller!!.isBGMMuted)
        Assertions.assertFalse(controller!!.isSFXMuted)
        Assertions.assertEquals(100, controller!!.getBGMVolume())
        Assertions.assertEquals(100, controller!!.getSFXVolume())
    }

    @Test
    fun testVolumeChange() {
        val newModelVolume = 0.5
        val newControllerVolume = 50
        controller!!.setBGMVolume(newControllerVolume)
        controller!!.setSFXVolume(newControllerVolume)
        Mockito.verify<SoundService>(mockSound).setBGMVolume(newModelVolume)
        Mockito.verify<SoundService>(mockSound).setSFXVolume(newModelVolume)
    }

    @Test
    fun testToggleMute() {
        controller!!.toggleBGMMute()
        controller!!.toggleSFXMute()
        Mockito.verify<SoundService>(mockSound).toggleMuteBGM()
        Mockito.verify<SoundService>(mockSound).toggleMuteSFX()
    }

    @Test
    fun testPlayBGM() {
        controller!!.playBGM(BGMTracks.MENU)
        Mockito.verify<SoundService>(mockSound).playBGM(BGMTracks.MENU)
    }

    @Test
    fun testPauseBGM() {
        controller!!.playBGM(BGMTracks.LEVEL)
        controller!!.pauseBGM()
        Mockito.verify<SoundService>(mockSound).pauseBGM()
    }

    @Test
    fun testResumeBGM() {
        controller!!.playBGM(BGMTracks.LEVEL)
        controller!!.pauseBGM()
        controller!!.resumeBGM()
        Mockito.verify<SoundService>(mockSound).resumeBGM()
    }

    @Test
    fun testInvalidVolumeThrows() {
        val tryVolume = 150
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
        ) { controller!!.setBGMVolume(tryVolume) }
    }

    @Test
    fun testInvalidVolumeDoesNotCallModel() {
        val tryVolume = -10
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
        ) { controller!!.setBGMVolume(tryVolume) }
        Mockito.verifyNoInteractions(mockSound)
    }

    @Test
    fun testPlaySFX() {
        controller!!.playSFX(SFXTracks.EXPLOSION)
        Mockito.verify<SoundService>(mockSound).playSFX(SFXTracks.EXPLOSION)
    }

    @Test
    fun testToggleMuteMultipleTimes() {
        val wantedInvocations = 2
        controller!!.toggleBGMMute()
        controller!!.toggleBGMMute()
        controller!!.toggleSFXMute()
        controller!!.toggleSFXMute()
        Mockito.verify<SoundService>(mockSound, Mockito.times(wantedInvocations)).toggleMuteBGM()
        Mockito.verify<SoundService>(mockSound, Mockito.times(wantedInvocations)).toggleMuteSFX()
    }

    @Test
    fun testVolumeThenMute() {
        val firstValue = 80
        val secondValue = 60
        val firstCorrective = firstValue.toDouble() / 100
        val secondCorrective = secondValue.toDouble() / 100
        controller!!.setBGMVolume(firstValue)
        controller!!.setSFXVolume(secondValue)
        controller!!.toggleBGMMute()
        controller!!.toggleSFXMute()
        Mockito.verify<SoundService>(mockSound).setBGMVolume(firstCorrective)
        Mockito.verify<SoundService>(mockSound).setSFXVolume(secondCorrective)
        Mockito.verify<SoundService>(mockSound).toggleMuteBGM()
        Mockito.verify<SoundService>(mockSound).toggleMuteSFX()
    }
}
