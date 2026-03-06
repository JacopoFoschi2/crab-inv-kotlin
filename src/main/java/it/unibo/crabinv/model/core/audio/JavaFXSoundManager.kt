package it.unibo.crabinv.model.core.audio

import javafx.scene.media.AudioClip
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

/**
 * Provides an implementation of [SoundService] using JavaFX.
 */
class JavaFXSoundManager : SoundService {
    override var bgmVolume: Double = 1.0
        set(value) {
            require(value in 0.0..1.0) { "Volume must be between 0.0 and 1.0" }
            field = value
            musicPlayer?.volume = value
        }

    override var sfxVolume: Double = 1.0
        set(value) {
            require(value in 0.0..1.0) { "Volume must be between 0.0 and 1.0" }
            field = value
        }

    override var isBGMMuted: Boolean = false
        set(value) {
            field = value
            musicPlayer?.isMute = value
        }

    override var isSFXMuted: Boolean = false

    private var musicPlayer: MediaPlayer? = null
    private var currentTrack: String? = null
    private val bgmCache: MutableMap<String, Media> = HashMap()
    private val sfxCache: MutableMap<String, AudioClip> = HashMap()

    override fun playBGM(musicName: BGMTracks) {
        val path = musicName.path
        if (musicPlayer != null) {
            if (currentTrack == path) return
            musicPlayer!!.stop()
            musicPlayer!!.dispose()
            musicPlayer = null
        }
        handleCache(path, bgmCache) { Media(it) }
        musicPlayer =
            MediaPlayer(bgmCache[path]).also {
                it.volume = bgmVolume
                it.isMute = isBGMMuted
                it.cycleCount = MediaPlayer.INDEFINITE
                it.play()
            }
        currentTrack = path
    }

    override fun resumeBGM() {
        if (musicPlayer?.status == MediaPlayer.Status.PAUSED) {
            musicPlayer!!.play()
        }
    }

    override fun pauseBGM() {
        if (musicPlayer?.status == MediaPlayer.Status.PLAYING) {
            musicPlayer!!.pause()
        }
    }

    override fun toggleMuteBGM() {
        isBGMMuted = !isBGMMuted
    }

    override fun playSFX(effectName: SFXTracks) {
        val path = effectName.path
        handleCache(path, sfxCache) { AudioClip(it) }
        if (!isSFXMuted) {
            sfxCache[path]!!.play(sfxVolume)
        }
    }

    override fun toggleMuteSFX() {
        isSFXMuted = !isSFXMuted
    }

    /**
     * Handles the caching of either sound effects or music tracks.
     *
     * @param name the key of the track
     * @param cache a map used to cache all the tracks used at runtime
     * @param create the method used to create the JavaFX sound construct
     */
    private fun <T> handleCache(
        name: String,
        cache: MutableMap<String, T>,
        create: (String) -> T,
    ) {
        if (!cache.containsKey(name)) {
            val resource = javaClass.getResource(name)
            requireNotNull(resource) { "Resource not found: $name" }
            cache[name] = create(resource.toExternalForm())
        }
    }
}
