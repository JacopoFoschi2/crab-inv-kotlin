package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.TextKeys
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox

/**
 * This is the view of the Main Menu.
 * Part of this was adapted from LLM by Mosè Barbieri
 * @param sceneManager it's the manager of the scenes, it moves them when needed
 * @param loc the needed manager for the translation
 * @param audio the audio manager needed for the sounds of the buttons
 */
class MainMenu(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
) {
    val view: Pane
        /**
         * it's the getter of the view of the menu.
         * @return the view of the menu
         */
        get() {
            val pane: Pane = StackPane()
            val mainColumn =
                VBox(ViewParameters.DEFAULT_SPACING_MAINMENU.toDouble())
            mainColumn.alignment = Pos.CENTER

            val title = Label("Crab Invaders")
            title.styleClass.add("menu-title")

            mainColumn.children.addAll(
                title,
                createMenuButton(TextKeys.PLAY) { sceneManager.showGame() },
                createMenuButton(TextKeys.SHOP) { sceneManager.showShop() },
                createMenuButton(TextKeys.RUN_LOG) { sceneManager.showMemorial() },
                createMenuButton(TextKeys.SETTINGS) { sceneManager.showSettings() },
                createMenuButton(TextKeys.EXIT_GAME) { Platform.exit() },
            )
            pane.children.add(mainColumn)
            return pane
        }

    private fun createMenuButton(
        key: TextKeys,
        action: Runnable,
    ): Button {
        val menuButton = Button(loc.getString(key))
        menuButton.styleClass.add("app-button")

        menuButton
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean?>?, _: Boolean?, newValue: Boolean? ->
                    if (newValue == true) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
                },
            )
        menuButton
            .onAction =
            EventHandler { _: ActionEvent? ->
                audio.playSFX(SFXTracks.MENU_SELECT)
                action.run()
            }

        return menuButton
    }
}
