package it.unibo.crabinv.core.config;

import java.nio.file.Path;

/**
 * Provides precompiled, OS independent ready to use paths.
 * These paths are used to access the game folder in the user home
 */
public final class AppPaths {
    private static final String GAME_DIR = ".crabinvaders";
    private static final String SAVES_DIR = "Saves";
    private static final String SETTINGS_FILE = "settings.json";
    private static final Path ROOT = Path.of(System.getProperty("user.home")).resolve(GAME_DIR);
    private static final Path SAVES = ROOT.resolve(SAVES_DIR);
    private static final Path SETTINGS = ROOT.resolve(SETTINGS_FILE);

    /**
     * Private constructor to ensure correct initialization.
     */
    private AppPaths() { }

    /**
     * Returns the path of the game root folder in user home.
     *
     * @return the path to root
     */
    public static Path getRoot() {
        return ROOT;
    }

    /**
     * Returns the path of the Saves folder.
     *
     * @return the path to the saves folder
     */
    public static Path getSaves() {
        return SAVES;
    }

    /**
     * Returns the path of settings.json file.
     *
     * @return the path to the settings.json file
     */
    public static Path getSettings() {
        return SETTINGS;
    }
}
