package it.unibo.crabinv.view

import com.sun.javafx.collections.ObservableListWrapper
import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.i18n.SupportedLocales
import it.unibo.crabinv.model.core.i18n.TextKeys
import javafx.beans.Observable
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.HBox
import java.util.function.Consumer
import java.util.function.IntConsumer

/**
 * Provides the methods to create all settings components.
 * @param sceneManager the instance of sceneManager
 * @param loc the instance of localization
 * @param audio the instance of AudioController
 * @param settingsAlignment the alignment that the settings component should have
 */
class SettingsComponentsGenerator(
    private val sceneManager: SceneManager,
    private val loc: LocalizationController,
    private val audio: AudioController,
    private val settingsAlignment: Pos?,
) {
    /**
     * Creates a working volume slider be it of SFX or BGM thanks to the setVolume IntConsumer.
     * @param volume the volume that the slider should be set to
     * @param setVolume the method that sets the volume in the audioController
     * @param onFocused the sound effect the slider should make when focused
     * @param key the textkey related to the slider
     * @return an HBox containing the slider and it's related label
     */
    fun createVolumeSlider(
        volume: Int,
        setVolume: IntConsumer,
        onFocused: Consumer<SFXTracks?>,
        key: TextKeys,
    ): HBox {
        val sliderBox = HBox(ViewParameters.DEFAULT_SPACING.toDouble())
        sliderBox.alignment = settingsAlignment
        val slider = Slider(0.0, 100.0, volume.toDouble())
        slider.blockIncrement = ViewParameters.DEFAULT_SLIDER_INCREMENTS.toDouble()
        slider
            .valueProperty()
            .addListener(
                ChangeListener {
                    _: ObservableValue<out Number>,
                    _: Number,
                    newValue: Number,
                    ->
                    audio.playSFX(SFXTracks.SLIDER)
                    setVolume.accept(newValue.toInt())
                },
            )
        slider
            .focusedProperty()
            .addListener { _: Observable? -> onFocused.accept(SFXTracks.MENU_HOVER) }
        val bgmTitle = Label(loc.getString(key))
        bgmTitle.styleClass.add("label")
        sliderBox.children.addAll(slider, bgmTitle)
        return sliderBox
    }

    /**
     * Creates a working mute checkbox for all the audio implementations.
     * @param key the key to obtain the localization text
     * @param isMute if the checkbox should be created already muted
     * @param toggleMute the method that should be run when the checkbox is toggled
     * @return the ready checkbox object
     */
    fun createMute(
        key: TextKeys,
        isMute: Boolean,
        toggleMute: Runnable,
    ): CheckBox {
        val mute = CheckBox(loc.getString(key))
        mute.styleClass.add("label")
        mute.isSelected = isMute
        mute.selectedProperty().addListener { _: Observable? ->
            toggleMute.run()
            audio.playSFX(SFXTracks.MENU_SELECT)
        }
        return mute
    }

    /**
     * Creates the working language selector.
     * @return the HBox containing both the spinner and its related label
     */
    fun createLanguageSelector(): HBox {
        val box = HBox(ViewParameters.DEFAULT_SPACING.toDouble())
        val languageSpinner = createSpinner()
        val label = Label(loc.getString(TextKeys.LANGUAGE))
        box.children.addAll(languageSpinner, label)
        return box
    }

    /**
     * Creates a spinner of the supported locales.
     * @return the working spinner
     */
    private fun createSpinner(): Spinner<SupportedLocales?> {
        val locales: ObservableList<SupportedLocales?> =
            ObservableListWrapper<SupportedLocales?>(SupportedLocales.entries)
        val factory: SpinnerValueFactory<SupportedLocales?> =
            SpinnerValueFactory.ListSpinnerValueFactory<SupportedLocales?>(locales)
        factory.isWrapAround = true
        val spinner = Spinner<SupportedLocales?>(factory)
        spinner.styleClass.add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL)
        spinner.getValueFactory().value = loc.language
        spinner.getValueFactory().valueProperty().addListener { _: Observable? ->
            loc.language = spinner.getValueFactory().getValue()
            sceneManager.showSettings()
        }
        return spinner
    }
}
