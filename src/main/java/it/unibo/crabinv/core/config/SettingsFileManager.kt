package it.unibo.crabinv.core.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import it.unibo.crabinv.core.config.AppPaths.root
import it.unibo.crabinv.core.config.AppPaths.settings
import java.io.IOException
import java.nio.file.Files
import java.util.logging.Logger

/**
 * Provides the apis to load or save the settings.json file ensuring state permanence.
 */
object SettingsFileManager {
    private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
    private val LOGGER: Logger = Logger.getLogger(SettingsFileManager::class.java.getName())

    /**
     * <h3>To be used upon app launch.</h3>
     * Attempts to load the app settings
     * @return null if the file doesn't exist yet, or the previously set settings
     */
    fun load(): AppSettings? {
        if (!Files.exists(settings)) {
            return null
        }

        try {
            Files.newBufferedReader(settings).use { reader ->
                return GSON.fromJson(reader, AppSettings::class.java)
            }
        } catch (_: IOException) {
            return null
        }
    }

    /**
     * <h3>To be used upon app closing.</h3>
     * Saves the current settings into settings.json file
     * @param settings the [AppSettings] record that stores all the current settings
     */
    fun save(settings: AppSettings?) {
        try {
            Files.createDirectories(root)
            Files.newBufferedWriter(AppPaths.settings).use { writer ->
                GSON.toJson(settings, AppSettings::class.java, writer)
            }
        } catch (e: IOException) {
            // not important.
            // inability to save settings will just let you restart with a clean profile
            LOGGER.warning("Unable to save settings: " + e.message)
        }
    }
}
