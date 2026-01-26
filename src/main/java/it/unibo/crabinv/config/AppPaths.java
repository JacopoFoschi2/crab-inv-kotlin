package it.unibo.crabinv.config;

import java.nio.file.Path;

/**
 * Handles all the paths of the app centralizing path access
 */
public final class AppPaths {
    private static final String GAME_DIR = ".crabinvaders";
    private static final String SAVES_DIR = "Saves";
    private static final String SETTINGS_FILE = "settings.json";
    private static final Path ROOT = Path.of(System.getProperty("user.home")).resolve(GAME_DIR);
    private static final Path SAVES = ROOT.resolve(SAVES_DIR);
    private static final Path SETTINGS = ROOT.resolve(SETTINGS_FILE);

    /**
     * Returns the path of the game root folder in user home
     */
    public static Path getRoot() {
        return ROOT;
    }

    /**
     * Returns the path of the Saves folder
     */
    public static Path getSaves() {
        return SAVES;
    }

    /**
     * Returns the path of settings.json file
     */
    public static Path getSettings() {
        return SETTINGS;
    }
}
