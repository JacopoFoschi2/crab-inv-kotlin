package it.unibo.crabinv.View;

import com.sun.javafx.collections.ObservableListWrapper;
import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.SFXTracks;
import it.unibo.crabinv.Model.i18n.SupportedLocales;
import it.unibo.crabinv.Model.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
    private final SettingComponentsGenerator components;

    public Settings(SceneManager sceneManager, LocalizationController loc, AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        components = new SettingComponentsGenerator(sceneManager, loc, audio, settingsAlignment);
    }

    public Pane getView() {
        StackPane pane = new StackPane();
        Label title = new Label(loc.getString(TextKeys.SETTINGS));
        title.getStyleClass().add("title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(20,0,0,0));
        pane.getChildren().add(title);
        VBox mainColumn = new VBox(25);
        mainColumn.setMinWidth(350);
        GridPane grid = new GridPane();
        grid.addColumn(0);
        grid.addColumn(1, mainColumn);
        grid.addColumn(2);
        grid.setAlignment(Pos.CENTER);
        mainColumn.setAlignment(settingsAlignment);
        HBox languageSpinner = components.createLanguageSelector();
        HBox bgmVolume = components.createVolumeSlider(audio.getBGMVolume(), audio::setBGMVolume, audio::playSFX, TextKeys.BGM_VOLUME);
        HBox sfxVolume = components.createVolumeSlider(audio.getSFXVolume(), audio::setSFXVolume, audio::playSFX, TextKeys.SFX_VOLUME);
        CheckBox bgmMute = components.createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        CheckBox sfxMute = components.createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        Button aReturn = new Button(loc.getString(TextKeys.RETURN));
        aReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        aReturn.getStyleClass().add("app-button");
        StackPane.setAlignment(aReturn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(aReturn, new Insets(0,0,60,0));
        mainColumn.getChildren().addAll(languageSpinner, bgmVolume, sfxVolume, bgmMute, sfxMute);
        for (var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        }
        pane.getChildren().addAll(grid, aReturn);
        return pane;
    }
}
