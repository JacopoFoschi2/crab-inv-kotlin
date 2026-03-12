package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.TextKeys
import javafx.beans.Observable
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox

/**
 * Provides the method to create the pause menu GUI.
 * @param loc the instance of LocalizationController
 * @param audio the instance of AudioController
 * @param resumeMethod the method that calls the resume of the game engine
 * @param gameOver the method that calls the gameOver of the game engine
 */
class PauseMenu(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
    private val resumeMethod: Runnable,
    private val gameOver: Runnable,
) {
    private val components: SettingsComponentsGenerator =
        SettingsComponentsGenerator(sceneManager, loc, audio, ViewParameters.SETTINGS_ALIGMENT)

    val view: Pane
        /**
         * @return the Stackpane containing a working pauseMenu
         */
        get() {
            val pane = StackPane()
            pane.styleClass.add("pause-pane")
            val content = VBox(ViewParameters.DEFAULT_SPACING.toDouble())
            content.alignment = Pos.CENTER
            content.maxWidth = ViewParameters.DEFAULT_PAUSE_WIDTH.toDouble()
            val title = Label(loc.getString(TextKeys.PAUSE))
            title.styleClass.add("title")
            val mainColumn =
                VBox(ViewParameters.DEFAULT_SPACING.toDouble())
            mainColumn.alignment = ViewParameters.SETTINGS_ALIGMENT
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
            mainColumn.children.addAll(bgmVolume, sfxVolume, bgmMute, sfxMute)
            for (child in mainColumn.children) {
                child
                    .focusedProperty()
                    .addListener { `_`: Observable? ->
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
            }
            val resume =
                createPauseMenuButton(
                    loc.getString(TextKeys.RESUME),
                    resumeMethod,
                ) { sceneManager.hidePauseMenu() }
            val exit =
                createPauseMenuButton(
                    loc.getString(TextKeys.ABANDON),
                    gameOver,
                ) { sceneManager.showMainMenu() }
            val buttons =
                HBox(ViewParameters.DEFAULT_SPACING.toDouble(), resume, exit)
            buttons.alignment = Pos.CENTER
            content.children.addAll(title, mainColumn, buttons)
            pane.children.add(content)
            return pane
        }

    /**
     * Creates the buttons for the pause menu.
     * @param text the text of the button
     * @param method the method it's linked to
     * @param changeScene the method to change the scene
     * @return the complete button
     */
    private fun createPauseMenuButton(
        text: String?,
        method: Runnable,
        changeScene: Runnable,
    ): Button {
        val button = Button(text)
        button.styleClass.add("app-button")
        button.onAction =
            EventHandler { `_`: ActionEvent? ->
                method.run()
                changeScene.run()
                audio.playSFX(SFXTracks.MENU_SELECT)
            }
        button
            .focusedProperty()
            .addListener { `_`: Observable? -> audio.playSFX(SFXTracks.MENU_HOVER) }
        return button
    }
}
