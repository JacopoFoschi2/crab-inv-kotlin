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
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Settings {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Pos settingsAlignment = Pos.CENTER_LEFT;

    public Settings(SceneManager sceneManager, LocalizationController loc, AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    public Pane getView() {
        Pane pane = new StackPane();
        Label title = new Label(loc.getString(TextKeys.SETTINGS));
        title.getStyleClass().add("title");
        title.setAlignment(Pos.TOP_CENTER);
        VBox mainColumn = new VBox(25);
        GridPane grid = new GridPane();
        grid.addColumn(0);
        grid.addColumn(1, title, mainColumn);
        grid.addColumn(2);
        grid.setAlignment(Pos.CENTER);
        mainColumn.setAlignment(settingsAlignment);
        HBox languageSpinner = createLanguageSelector();
        HBox bgmVolume = createVolumeSlider(audio.getBGMVolume(), audio::setBGMVolume, audio::playSFX, TextKeys.BGM_VOLUME);
        HBox sfxVolume = createVolumeSlider(audio.getSFXVolume(), audio::setSFXVolume, audio::playSFX, TextKeys.SFX_VOLUME);
        CheckBox bgmMute = createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        CheckBox sfxMute = createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        Button aReturn = new Button(loc.getString(TextKeys.RETURN));
        aReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        aReturn.getStyleClass().add("app-button");
        mainColumn.getChildren().addAll(languageSpinner, bgmVolume, sfxVolume, bgmMute, sfxMute, aReturn);
        for (var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        }
        pane.getChildren().add(grid);
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
        bgmTitle.getStyleClass().add("label");
        sliderBox.getChildren().addAll(slider, bgmTitle);
        return sliderBox;
    }

    private CheckBox createMute(TextKeys key, boolean isMute, Runnable toggleMute) {
        CheckBox mute = new CheckBox(loc.getString(key));
        mute.getStyleClass().add("label");
        mute.setSelected(isMute);
        mute.selectedProperty().addListener(_ -> {
            toggleMute.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        return mute;
    }

    private Spinner<SupportedLocales> createSpinner() {
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

    private HBox createLanguageSelector() {
        HBox box = new HBox(20);
        Spinner<SupportedLocales> languageSpinner = createSpinner();
        Label label = new Label(loc.getString(TextKeys.LANGUAGE));
        box.getChildren().addAll(languageSpinner, label);
        return box;
    }
}
