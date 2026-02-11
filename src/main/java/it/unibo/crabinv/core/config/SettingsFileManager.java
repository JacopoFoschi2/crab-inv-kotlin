package it.unibo.crabinv.core.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

/**
 * Provides the apis to load or save the settings.json file ensuring state permanence.
 */
public final class SettingsFileManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Ensures the class doesn't get instantiated.
     */
    private SettingsFileManager() { }

    /**
     * <h3>To be used upon app launch.</h3>
     * Attempts to load the app settings
     *
     * @return null if the file doesn't exist yet, or the previously set settings
     */
    public static AppSettings load() {
        if (!Files.exists(AppPaths.getSettings())) {
            return null;
        }

        try (Reader reader = Files.newBufferedReader(AppPaths.getSettings())) {
            return GSON.fromJson(reader, AppSettings.class);
        } catch (final IOException e) {
            return null;
        }
    }

    /**
     * <h3>To be used upon app closing.</h3>
     * Saves the current settings into settings.json file
     *
     * @param settings the {@link AppSettings} record that stores all the current settings
     */
    public static void save(final AppSettings settings) {
        try {
            Files.createDirectories(AppPaths.getRoot());
            try (Writer writer = Files.newBufferedWriter(AppPaths.getSettings())) {
                GSON.toJson(settings, AppSettings.class, writer);
            }
        } catch (final IOException e) {
            //not important.
            //inability to save settings will just let you restart with a clean profile
            e.printStackTrace();
        }
    }
}
