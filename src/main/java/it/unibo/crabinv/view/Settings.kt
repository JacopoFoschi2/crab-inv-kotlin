package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.TextKeys
import javafx.beans.Observable
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox

/**
 * Provides the settings interface of the application.
 * @param sceneManager the instance of sceneManager
 * @param loc the instance of localization
 * @param audio the instance of AudioController
 */
class Settings(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
) {
    private val components: SettingsComponentsGenerator =
        SettingsComponentsGenerator(sceneManager, loc, audio, ViewParameters.SETTINGS_ALIGMENT)

    val view: Pane
        /**
         * @return the stackpane to be shown in the GUI
         */
        get() {
            val pane = StackPane()
            val title = Label(loc.getString(TextKeys.SETTINGS))
            title.styleClass.add("title")
            StackPane.setAlignment(title, Pos.TOP_CENTER)
            StackPane.setMargin(
                title,
                Insets(ViewParameters.DEFAULT_INSETS.toDouble(), 0.0, 0.0, 0.0),
            )
            pane.children.add(title)
            val mainColumn =
                VBox(ViewParameters.DEFAULT_SPACING.toDouble())
            mainColumn.minWidth = ViewParameters.DEFAULT_PAUSE_WIDTH.toDouble()
            val grid = GridPane()
            grid.addColumn(0)
            grid.addColumn(1, mainColumn)
            grid.addColumn(2)
            grid.alignment = Pos.CENTER
            mainColumn.alignment = ViewParameters.SETTINGS_ALIGMENT
            val languageSpinner = components.createLanguageSelector()
            val bgmVolume =
                components.createVolumeSlider(
                    audio.bgmVolume,
                    { volume: Int -> audio.bgmVolume = volume },
                    { sfx: SFXTracks? -> audio.playSFX(sfx!!) },
                    TextKeys.BGM_VOLUME,
                )
            val sfxVolume =
                components.createVolumeSlider(
                    audio.sfxVolume,
                    { volume: Int -> audio.sfxVolume = volume },
                    { sfx: SFXTracks? -> audio.playSFX(sfx!!) },
                    TextKeys.SFX_VOLUME,
                )
            val bgmMute =
                components.createMute(
                    TextKeys.BGM_MUTE,
                    audio.isBGMMuted,
                ) { audio.toggleBGMMute() }
            val sfxMute =
                components.createMute(
                    TextKeys.SFX_MUTE,
                    audio.isSFXMuted,
                ) { audio.toggleSFXMute() }
            val aReturn = Button(loc.getString(TextKeys.RETURN))
            aReturn.onAction =
                EventHandler { `_`: ActionEvent? ->
                    sceneManager.showMainMenu()
                    audio.playSFX(SFXTracks.MENU_SELECT)
                }
            aReturn.styleClass.add("app-button")
            StackPane.setAlignment(aReturn, Pos.BOTTOM_CENTER)
            StackPane.setMargin(
                aReturn,
                Insets(0.0, 0.0, ViewParameters.DEFAULT_INSETS.toDouble(), 0.0),
            )
            mainColumn.children.addAll(languageSpinner, bgmVolume, sfxVolume, bgmMute, sfxMute)
            for (child in mainColumn.children) {
                child
                    .focusedProperty()
                    .addListener { `_`: Observable? ->
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
            }
            pane.children.addAll(grid, aReturn)
            return pane
        }
}
