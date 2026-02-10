package it.unibo.crabinv.view;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static it.unibo.crabinv.view.ViewParameters.DEFAULT_INSETS;
import static it.unibo.crabinv.view.ViewParameters.DEFAULT_PAUSE_WIDTH;
import static it.unibo.crabinv.view.ViewParameters.DEFAULT_SPACING;

/**
 * Provides the settings interface of the application.
 */
public class Settings {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Pos settingsAlignment = Pos.CENTER_LEFT;
    private final SettingComponentsGenerator components;

    /**
     * @param sceneManager the instance of sceneManager
     * @param loc the instance of localization
     * @param audio the instance of AudioController
     */
    public Settings(final SceneManager sceneManager, final LocalizationController loc, final AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        components = new SettingComponentsGenerator(sceneManager, loc, audio, settingsAlignment);
    }

    /**
     * @return the stackpane to be shown in the GUI
     */
    public Pane getView() {
        final StackPane pane = new StackPane();
        final Label title = new Label(loc.getString(TextKeys.SETTINGS));
        title.getStyleClass().add("title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(DEFAULT_INSETS, 0, 0, 0));
        pane.getChildren().add(title);
        final VBox mainColumn = new VBox(DEFAULT_SPACING);
        mainColumn.setMinWidth(DEFAULT_PAUSE_WIDTH);
        final GridPane grid = new GridPane();
        grid.addColumn(0);
        grid.addColumn(1, mainColumn);
        grid.addColumn(2);
        grid.setAlignment(Pos.CENTER);
        mainColumn.setAlignment(settingsAlignment);
        final HBox languageSpinner = components.createLanguageSelector();
        final HBox bgmVolume = components.createVolumeSlider(
                audio.getBGMVolume(),
                audio::setBGMVolume,
                audio::playSFX,
                TextKeys.BGM_VOLUME);
        final HBox sfxVolume = components.createVolumeSlider(
                audio.getSFXVolume(),
                audio::setSFXVolume,
                audio::playSFX,
                TextKeys.SFX_VOLUME);
        final CheckBox bgmMute = components.createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        final CheckBox sfxMute = components.createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        final Button aReturn = new Button(loc.getString(TextKeys.RETURN));
        aReturn.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        aReturn.getStyleClass().add("app-button");
        StackPane.setAlignment(aReturn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(aReturn, new Insets(0, 0, DEFAULT_INSETS, 0));
        mainColumn.getChildren().addAll(languageSpinner, bgmVolume, sfxVolume, bgmMute, sfxMute);
        for (final var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        }
        pane.getChildren().addAll(grid, aReturn);
        return pane;
    }
}
