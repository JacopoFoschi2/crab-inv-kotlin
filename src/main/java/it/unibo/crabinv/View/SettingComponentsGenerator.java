package it.unibo.crabinv.View;

import com.sun.javafx.collections.ObservableListWrapper;
import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.SupportedLocales;
import it.unibo.crabinv.Model.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class SettingComponentsGenerator {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Pos settingsAlignment;

    public SettingComponentsGenerator (SceneManager sceneManager, LocalizationController loc, AudioController audio, Pos settingsAlignment) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.settingsAlignment = settingsAlignment;
    }

    public HBox createVolumeSlider(int volume, IntConsumer setVolume, Consumer<SFXTracks> onFocused, TextKeys key) {
        HBox sliderBox = new HBox(20);
        sliderBox.setAlignment(settingsAlignment);
        Slider slider = new Slider(0, 100, volume);
        slider.setBlockIncrement(5);
        slider.valueProperty().addListener((_,_,newValue) -> {
            audio.playSFX(SFXTracks.SLIDER);
            setVolume.accept(newValue.intValue());
        });
        slider.focusedProperty().addListener(_ -> onFocused.accept(SFXTracks.MENU_HOVER));
        Label bgmTitle = new Label(loc.getString(key));
        bgmTitle.getStyleClass().add("label");
        sliderBox.getChildren().addAll(slider, bgmTitle);
        return sliderBox;
    }

    public CheckBox createMute(TextKeys key, boolean isMute, Runnable toggleMute) {
        CheckBox mute = new CheckBox(loc.getString(key));
        mute.getStyleClass().add("label");
        mute.setSelected(isMute);
        mute.selectedProperty().addListener(_ -> {
            toggleMute.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        return mute;
    }

    public Spinner<SupportedLocales> createSpinner() {
        ObservableList<SupportedLocales> locales = new ObservableListWrapper<>(List.of(SupportedLocales.values()));
        SpinnerValueFactory<SupportedLocales> factory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(locales);
        factory.setWrapAround(true);
        Spinner<SupportedLocales> spinner = new Spinner<>(factory);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.getValueFactory().setValue(loc.getLanguage());
        spinner.getValueFactory().valueProperty().addListener(_->{
            loc.setLanguage(spinner.getValueFactory().getValue());
            sceneManager.showSettings();
        });
        return spinner;
    }

    public HBox createLanguageSelector() {
        HBox box = new HBox(20);
        Spinner<SupportedLocales> languageSpinner = createSpinner();
        Label label = new Label(loc.getString(TextKeys.LANGUAGE));
        box.getChildren().addAll(languageSpinner, label);
        return box;
    }
}
