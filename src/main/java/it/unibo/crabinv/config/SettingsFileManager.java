package it.unibo.crabinv.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

/**
 * Handles the file handling for the settings.json file inside the game folder
 */
public final class SettingsFileManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Ensures the class doesn't get instantiated in a wrong state
     */
    private SettingsFileManager() {}

    /**
     * <h3>To be used upon app launch</h3>
     * Attempts to load the app settings
     * @return null if the file doesn't exist yet, or the previously set settings
     */
    public static AppSettings load() {
        if (!Files.exists(AppPaths.getSettings())) {
            return null;
        }

        try (Reader reader = Files.newBufferedReader(AppPaths.getSettings())) {
            return gson.fromJson(reader, AppSettings.class);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load settings", e);
        }
    }

    /**
     * <h3>To be used upon app closing</h3>
     * Saves the current settings into settings.json file
     * @param settings the {@link AppSettings} record that stores all the current settings
     */
    public static void save(AppSettings settings) {
        try {
            Files.createDirectories(AppPaths.getRoot());
            try (Writer writer = Files.newBufferedWriter(AppPaths.getSettings())){
                gson.toJson(settings, AppSettings.class, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
