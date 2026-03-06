package it.unibo.crabinv.model.core.audio

/**
 * Provides all the basic sound apis.
 */
interface SoundService {
    /**
     * Disposes of currently playing music if there's any and starts playing the new track,
     * does nothing if the new track is the same as the previous one.
     *
     * @param musicName the track you wish to play from [BGMTracks]
     */
    fun playBGM(musicName: BGMTracks)

    /** Starts playing the song if it was stopped. */
    fun resumeBGM()

    /** Pauses the current song. */
    fun pauseBGM()

    /**
     * Volume of current and future playing music, between 0.0 and 1.0.
     *
     * @throws IllegalArgumentException if the value isn't between 0.0 and 1.0
     */
    var bgmVolume: Double

    /** Toggles if BGM is muted. */
    fun toggleMuteBGM()

    /** Whether BGM is muted. */
    var isBGMMuted: Boolean

    /**
     * Plays the selected sound effect.
     *
     * @param effectName the sound effect you wish to play from [SFXTracks]
     */
    fun playSFX(effectName: SFXTracks)

    /**
     * Volume of sound effects, between 0.0 and 1.0.
     *
     * @throws IllegalArgumentException if the value isn't between 0.0 and 1.0
     */
    var sfxVolume: Double

    /** Toggles if SFX are muted. */
    fun toggleMuteSFX()

    /** Whether SFX are muted. */
    var isSFXMuted: Boolean
}
