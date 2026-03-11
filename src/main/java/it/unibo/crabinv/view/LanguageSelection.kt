package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.SupportedLocales
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import java.util.Objects

/**
 * Provides the languageSelection GUI.
 * @param sceneManager the sceneManager used to change scenes
 * @param loc the localizationController used to fetch strings
 * @param audio the audioController used to play sounds
 */
class LanguageSelection(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
) {
    val view: Pane
        /**
         * @return the view ready to be set inside a stage
         */
        get() {
            val pane = StackPane()
            val mainColumn =
                VBox(ViewParameters.DEFAULT_SPACING.toDouble())
            val title = Label("SELECT LANGUAGE")
            title.styleClass.add("title")
            val languageSelection =
                HBox(ViewParameters.DEFAULT_SPACING.toDouble())
            val widthOfButton = sceneManager.width / (SupportedLocales.entries.size + 1)
            for (supportedLocale in SupportedLocales.entries) {
                languageSelection.children.add(generateLanguageButton(widthOfButton, supportedLocale))
            }
            languageSelection.alignment = Pos.CENTER
            mainColumn.children.addAll(title, languageSelection)
            mainColumn.alignment = Pos.TOP_CENTER
            pane.children.add(mainColumn)
            return pane
        }

    /**
     * @param width the width of the flag
     * @param locale the locale the button should represent
     * @return a button that shows the flag it represents and it's localised name
     */
    private fun generateLanguageButton(
        width: Double,
        locale: SupportedLocales,
    ): Button {
        val path =
            Objects.requireNonNull(LanguageSelection::class.java.getResourceAsStream(locale.imagePath))
        val flag = Image(path)
        val flagImg = ImageView(flag)
        flagImg.fitWidth = width
        flagImg.fitHeight = width / 3 * 2
        val language = Label(locale.localizedName)
        language.styleClass.add("label")
        val composition = VBox(flagImg, language)
        composition.alignment = Pos.CENTER
        val languageButton = Button()
        languageButton.graphic = composition
        languageButton
            .focusedProperty()
            .addListener(
                ChangeListener { _: ObservableValue<out Boolean?>?, _: Boolean?, newValue: Boolean? ->
                    if (newValue == true) {
                        audio.playSFX(SFXTracks.MENU_HOVER)
                    }
                },
            )
        languageButton.onAction =
            EventHandler { `_`: ActionEvent? ->
                audio.playSFX(SFXTracks.MENU_SELECT)
                loc.language = locale
                sceneManager.showMainMenu()
            }
        languageButton.styleClass.add("language-button")
        return languageButton
    }
}
