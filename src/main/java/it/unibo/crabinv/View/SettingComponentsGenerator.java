package it.unibo.crabinv.View;

import com.sun.javafx.collections.ObservableListWrapper;
import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.SupportedLocales;
import it.unibo.crabinv.Model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static it.unibo.crabinv.View.ViewParameters.DEFAULT_SLIDER_INCREMENTS;
import static it.unibo.crabinv.View.ViewParameters.DEFAULT_SPACING;

/**
 * Provides the methods to create all settings components.
 */
public class SettingComponentsGenerator {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Pos settingsAlignment;

    /**
     * @param sceneManager the instance of sceneManager
     * @param loc the instance of localization
     * @param audio the instance of AudioController
     * @param settingsAlignment the alignment that the settings component should have
     */
    public SettingComponentsGenerator(
            final SceneManager sceneManager,
            final LocalizationController loc,
            final AudioController audio,
            final Pos settingsAlignment) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.settingsAlignment = settingsAlignment;
    }

    /**
     * Creates a working volume slider be it of SFX or BGM thanks to the setVolume IntConsumer.
     *
     * @param volume the volume that the slider should be set to
     * @param setVolume the method that sets the volume in the audioController
     * @param onFocused the sound effect the slider should make when focused
     * @param key the textkey related to the slider
     * @return an HBox containing the slider and it's related label
     */
    public HBox createVolumeSlider(
            final int volume,
            final IntConsumer setVolume,
            final Consumer<SFXTracks> onFocused,
            final TextKeys key) {
        final HBox sliderBox = new HBox(DEFAULT_SPACING);
        sliderBox.setAlignment(settingsAlignment);
        final Slider slider = new Slider(0, 100, volume);
        slider.setBlockIncrement(DEFAULT_SLIDER_INCREMENTS);
        slider.valueProperty().addListener((_, _, newValue) -> {
            audio.playSFX(SFXTracks.SLIDER);
            setVolume.accept(newValue.intValue());
        });
        slider.focusedProperty().addListener(_ -> onFocused.accept(SFXTracks.MENU_HOVER));
        final Label bgmTitle = new Label(loc.getString(key));
        bgmTitle.getStyleClass().add("label");
        sliderBox.getChildren().addAll(slider, bgmTitle);
        return sliderBox;
    }

    /**
     * Creates a working mute checkbox for all the audio implementations.
     *
     * @param key the key to obtain the localization text
     * @param isMute if the checkbox should be created already muted
     * @param toggleMute the method that should be run when the checkbox is toggled
     * @return the ready checkbox object
     */
    public CheckBox createMute(final TextKeys key, final boolean isMute, final Runnable toggleMute) {
        final CheckBox mute = new CheckBox(loc.getString(key));
        mute.getStyleClass().add("label");
        mute.setSelected(isMute);
        mute.selectedProperty().addListener(_ -> {
            toggleMute.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        return mute;
    }

    /**
     * Creates the working language selector.
     *
     * @return the HBox containing both the spinner and its related label
     */
    public HBox createLanguageSelector() {
        final HBox box = new HBox(DEFAULT_SPACING);
        final Spinner<SupportedLocales> languageSpinner = createSpinner();
        final Label label = new Label(loc.getString(TextKeys.LANGUAGE));
        box.getChildren().addAll(languageSpinner, label);
        return box;
    }

    /**
     * Creates a spinner of the supported locales.
     *
     * @return the working spinner
     */
    private Spinner<SupportedLocales> createSpinner() {
        final ObservableList<SupportedLocales> locales = new ObservableListWrapper<>(List.of(SupportedLocales.values()));
        final SpinnerValueFactory<SupportedLocales> factory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(locales);
        factory.setWrapAround(true);
        final Spinner<SupportedLocales> spinner = new Spinner<>(factory);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.getValueFactory().setValue(loc.getLanguage());
        spinner.getValueFactory().valueProperty().addListener(_ -> {
            loc.setLanguage(spinner.getValueFactory().getValue());
            sceneManager.showSettings();
        });
        return spinner;
    }
}
