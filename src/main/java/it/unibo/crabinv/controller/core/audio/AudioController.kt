package it.unibo.crabinv.controller.core.audio

import it.unibo.crabinv.model.core.audio.BGMTracks
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.audio.SoundService

/**
 * Provides a controller facade that lets you utilize any implementation of [SoundService] regardless of the library used.
 *
 * @param soundManager the implementation of SoundManager you want to use
 */
class AudioController(
    private val soundManager: SoundService,
) {
    /** Plays the music. @param bgm the wanted track to play */
    fun playBGM(bgm: BGMTracks) = soundManager.playBGM(bgm)

    /**
     * Pauses the currently playing track for a later resume.
     * Use it in instances such as the pause menu to pause the main music.
     */
    fun pauseBGM() = soundManager.pauseBGM()

    /**
     * Resumes the currently playing track that was previously paused.
     * Use it in instances such as when you resume the game after it was paused.
     */
    fun resumeBGM() = soundManager.resumeBGM()

    /**
     * BGM volume as an integer between 0 and 100.
     *
     * @throws IllegalArgumentException if the value isn't between 0 and 100
     */
    var bgmVolume: Int
        get() = (soundManager.bgmVolume * 100).toInt()
        set(value) {
            require(value in 0..100) { "Volume must be between 0 and 100" }
            soundManager.bgmVolume = value.toDouble() / 100
        }

    /** Whether BGM is currently muted. */
    val isBGMMuted: Boolean
        get() = soundManager.isBGMMuted

    /** Toggles if the music should be muted or not. */
    fun toggleBGMMute() = soundManager.toggleMuteBGM()

    /** Plays sound effects. @param sfx the track you want to play */
    fun playSFX(sfx: SFXTracks) = soundManager.playSFX(sfx)

    /**
     * SFX volume as an integer between 0 and 100.
     *
     * @throws IllegalArgumentException if the value isn't between 0 and 100
     */
    var sfxVolume: Int
        get() = (soundManager.sfxVolume * 100).toInt()
        set(value) {
            require(value in 0..100) { "Volume must be between 0 and 100" }
            soundManager.sfxVolume = value.toDouble() / 100
        }

    /** Whether SFX are currently muted. */
    val isSFXMuted: Boolean
        get() = soundManager.isSFXMuted

    /** Toggles if the sound effects should be muted or not. */
    fun toggleSFXMute() = soundManager.toggleMuteSFX()
}
