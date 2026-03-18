package it.unibo.crabinv

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.core.config.AppSettings
import it.unibo.crabinv.core.config.SettingsFileManager
import it.unibo.crabinv.model.core.audio.JavaFXSoundManager
import it.unibo.crabinv.model.core.i18n.Localization
import javafx.application.Application
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.IOException
import java.util.Objects

/**
 * Provides the application with the methods it needs to run.
 */
class App : Application() {
    private val loc = LocalizationController(Localization())
    private val audio = AudioController(JavaFXSoundManager())

    @Throws(IOException::class)
    override fun start(mainStage: Stage) {
        // The bounds of the screen
        val bounds = Screen.getPrimary().visualBounds
        mainStage.apply {
            initStyle(StageStyle.UNDECORATED)
            x = bounds.minX
            y = bounds.minY
            width = bounds.width
            height = bounds.height
        }
        val mainScene: Scene
        val root = StackPane()
        mainScene = Scene(root)
        val manager = SceneManager(root, loc, audio, bounds)
        mainScene.stylesheets.add(
            Objects.requireNonNull(javaClass.getResource("/style/style.css")).toExternalForm(),
        )
        // Attempts to read the settings.json file and handles setting them
        val settings = SettingsFileManager.load()
        if (settings != null) {
            loc.language = settings.locales
            audio.bgmVolume = settings.bgmVolume
            audio.sfxVolume = settings.sfxVolume
            if (settings.isBGMMuted) {
                audio.toggleBGMMute()
            }
            if (settings.isSFXMuted) {
                audio.toggleSFXMute()
            }
        }
        mainScene.cursor = Cursor.NONE
        mainStage.scene = mainScene
        mainStage.title = "Crab Invaders"
        mainStage.isResizable = false
        if (loc.language == null) {
            manager.showLanguageSelection()
        } else {
            manager.showMainMenu()
        }
        mainStage.show()
    }

    @Throws(Exception::class)
    override fun stop() {
        // used for saving the state of the settings before closing the app
        val settings =
            AppSettings(
                audio.bgmVolume,
                audio.sfxVolume,
                audio.isBGMMuted,
                audio.isSFXMuted,
                loc.language,
            )
        SettingsFileManager.save(settings)
        super.stop()
    }

    /**
     * Provides the launcher of the application.
     */
    object Main {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(App::class.java, *args)
        }
    }
}
