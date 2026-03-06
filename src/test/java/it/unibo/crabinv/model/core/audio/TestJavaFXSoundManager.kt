package it.unibo.crabinv.model.core.audio

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class TestJavaFXSoundManager {
    private val soundManager: SoundService = JavaFXSoundManager()

    @Test
    fun testBGMMute() {
        Assertions.assertFalse(soundManager.isBGMMuted)
        soundManager.toggleMuteBGM()
        Assertions.assertTrue(soundManager.isBGMMuted)
    }

    @Test
    fun testSFXMute() {
        Assertions.assertFalse(soundManager.isSFXMuted)
        soundManager.toggleMuteSFX()
        Assertions.assertTrue(soundManager.isSFXMuted)
    }

    @Test
    fun testBGMVolumeChange() {
        Assertions.assertEquals(DEFAULT_VOLUME, soundManager.bgmVolume)
        soundManager.bgmVolume = TEST_VOLUME
        Assertions.assertEquals(TEST_VOLUME, soundManager.bgmVolume)
    }

    @Test
    fun testSFXVolumeChange() {
        Assertions.assertEquals(DEFAULT_VOLUME, soundManager.sfxVolume)
        soundManager.sfxVolume = TEST_VOLUME
        Assertions.assertEquals(TEST_VOLUME, soundManager.sfxVolume)
    }

    companion object {
        private const val DEFAULT_VOLUME = 1.0
        private const val TEST_VOLUME = 0.5
    }
}
