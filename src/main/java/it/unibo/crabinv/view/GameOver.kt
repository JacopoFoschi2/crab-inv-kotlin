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
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox

/**
 * Provides the method to show the game over screen.
 * @param sceneManager the instance of sceneManager for page change
 * @param loc the instance of loc for string fetch
 * @param audio the instance of audio for sounds
 * @param messageType the message type, either victory or game over
 */
class GameOver(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
    private val messageType: MessageTypes,
) {
    val view: Pane
        /**
         * @return the pane showing the game over screen.
         */
        get() {
            val pane = StackPane()
            val mainColumn = VBox(100.0)
            val message = Label()
            message.styleClass.add("menu-title")
            if (messageType == MessageTypes.VICTORY) {
                message.text = loc.getString(TextKeys.VICTORY)
            } else {
                message.text = loc.getString(TextKeys.GAME_OVER)
            }
            val returnToMenu =
                Button(loc.getString(TextKeys.RETURN_TO_MENU))
            returnToMenu.styleClass.add("app-button")
            returnToMenu
                .focusedProperty()
                .addListener { `_`: Observable? ->
                    audio.playSFX(SFXTracks.MENU_HOVER)
                }
            returnToMenu.onAction =
                EventHandler { _: ActionEvent? ->
                    sceneManager.showMainMenu()
                    audio.playSFX(SFXTracks.MENU_SELECT)
                }
            mainColumn.children.addAll(message, returnToMenu)
            mainColumn.alignment = Pos.CENTER
            pane.children.addAll(mainColumn)
            return pane
        }

    /**
     * Provides the types of game overs there are.
     */
    enum class MessageTypes {
        GAME_OVER,
        VICTORY,
    }
}
