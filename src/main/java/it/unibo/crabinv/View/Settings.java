package it.unibo.crabinv.View;

import com.sun.javafx.collections.ImmutableObservableList;
import com.sun.javafx.collections.ObservableListWrapper;
import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.SupportedLocales;
import it.unibo.crabinv.Model.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Settings {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Pos settingsAlignment = Pos.CENTER;

    public Settings(SceneManager sceneManager, LocalizationController loc, AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    public Pane getView() {
        Pane pane = new StackPane();
        VBox mainColumn = new VBox(20);
        mainColumn.setAlignment(settingsAlignment);
        Label title = new Label(loc.getString(TextKeys.SETTINGS));
        HBox bgmVolume = createVolumeSlider(audio.getBGMVolume(), audio::setBGMVolume, audio::playSFX, TextKeys.BGM_VOLUME);
        HBox sfxVolume = createVolumeSlider(audio.getSFXVolume(), audio::setSFXVolume, audio::playSFX, TextKeys.SFX_VOLUME);
        CheckBox bgmMute = createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        CheckBox sfxMute = createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        Button aReturn = new Button(loc.getString(TextKeys.RETURN));
        Spinner<SupportedLocales> languageSpinner = createSpinner(loc);
        aReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        mainColumn.getChildren().addAll(title, bgmVolume, sfxVolume, bgmMute, sfxMute, languageSpinner, aReturn);
        for (var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        }
        pane.getChildren().add(mainColumn);
        return pane;
    }

    private HBox createVolumeSlider(int volume, IntConsumer setVolume, Consumer<SFXTracks> onFocused, TextKeys key) {
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
        sliderBox.getChildren().addAll(bgmTitle, slider);
        return sliderBox;
    }

    private CheckBox createMute(TextKeys key, boolean isMute, Runnable toggleMute) {
        CheckBox mute = new CheckBox(loc.getString(key));
        mute.setSelected(isMute);
        mute.selectedProperty().addListener(_ -> {
            toggleMute.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        return mute;
    }

    private Spinner<SupportedLocales> createSpinner(LocalizationController loc) {
        ObservableList<SupportedLocales> locales = new ObservableListWrapper<>(List.of(SupportedLocales.values()));
        SpinnerValueFactory<SupportedLocales> factory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(locales);
        factory.setWrapAround(true);
        Spinner<SupportedLocales> spinner = new Spinner<>(factory);
        spinner.getValueFactory().setValue(loc.getLanguage());
        return spinner;
    }
}
