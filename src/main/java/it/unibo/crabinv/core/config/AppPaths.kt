package it.unibo.crabinv.core.config

import java.nio.file.Path

/**
 * Provides precompiled, OS independent ready to use paths.
 * These paths are used to access the game folder in the user home
 */
object AppPaths {
    private const val GAME_DIR = ".crabinvaders"
    private const val SAVES_DIR = "Saves"
    private const val SETTINGS_FILE = "settings.json"

    /**
     * @return the path to root
     */
    val root: Path = Path.of(System.getProperty("user.home")).resolve(GAME_DIR)

    /**
     * @return the path to the saves folder
     */
    val saves: Path = root.resolve(SAVES_DIR)

    /**
     * @return the path to the settings.json file
     */
    val settings: Path = root.resolve(SETTINGS_FILE)
}
