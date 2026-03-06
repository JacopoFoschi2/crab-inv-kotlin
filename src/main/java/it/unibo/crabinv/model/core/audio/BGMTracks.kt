package it.unibo.crabinv.model.core.audio

/**
 * Provides a set list of present BGM Tracks and their relative path in resources.
 */
enum class BGMTracks(
    /**
     * @return the resource path of the selected BGM
     */
    val path: String,
) {
    LEVEL("/audio/BGM/main_bgm.mp3"),
    MENU("/audio/BGM/menu_bgm.mp3"),
}
