package it.unibo.crabinv.view;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static it.unibo.crabinv.view.ViewParameters.DEFAULT_PAUSE_WIDTH;
import static it.unibo.crabinv.view.ViewParameters.DEFAULT_SPACING;
import static it.unibo.crabinv.view.ViewParameters.SETTINGS_ALIGMENT;

/**
 * Provides the method to create the pause menu GUI.
 */
public class PauseMenu {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Runnable resumeMethod;
    private final Runnable gameOver;
    private final SettingComponentsGenerator components;

    /**
     * @param sceneManager the instance of SceneManager
     * @param loc the instance of LocalizationController
     * @param audio the instance of AudioController
     * @param resumeMethod the method that calls the resume of the game engine
     * @param gameOver the method that calls the gameOver of the game engine
     */
    public PauseMenu(
            final SceneManager sceneManager,
            final LocalizationController loc,
            final AudioController audio,
            final Runnable resumeMethod,
            final Runnable gameOver) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.resumeMethod = resumeMethod;
        this.gameOver = gameOver;
        components = new SettingComponentsGenerator(sceneManager, loc, audio, SETTINGS_ALIGMENT);
    }

    /**
     * @return the Stackpane containing a working pauseMenu
     */
    public Pane getView() {
        final StackPane pane = new StackPane();
        pane.getStyleClass().add("pause-pane");
        final VBox content = new VBox(DEFAULT_SPACING);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(DEFAULT_PAUSE_WIDTH);
        final Label title = new Label(loc.getString(TextKeys.PAUSE));
        title.getStyleClass().add("title");
        final VBox mainColumn = new VBox(DEFAULT_SPACING);
        mainColumn.setAlignment(SETTINGS_ALIGMENT);
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
        mainColumn.getChildren().addAll(bgmVolume, sfxVolume, bgmMute, sfxMute);
        for (final var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ ->
                    audio.playSFX(SFXTracks.MENU_HOVER));
        }
        final Button resume = createPauseMenuButton(loc.getString(TextKeys.RESUME), resumeMethod, sceneManager::hidePauseMenu);
        final Button exit = createPauseMenuButton(loc.getString(TextKeys.ABANDON), gameOver, sceneManager::showMainMenu);
        final HBox buttons = new HBox(DEFAULT_SPACING, resume, exit);
        buttons.setAlignment(Pos.CENTER);
        content.getChildren().addAll(title, mainColumn, buttons);
        pane.getChildren().add(content);
        return pane;
    }

    /**
     * Creates the buttons for the pause menu.
     *
     * @param text the text of the button
     * @param method the method it's linked to
     * @param changeScene the method to change the scene
     * @return the complete button
     */
    private Button createPauseMenuButton(final String text, final Runnable method, final Runnable changeScene) {
        final Button button = new Button(text);
        button.getStyleClass().add("app-button");
        button.setOnAction(_ -> {
            method.run();
            changeScene.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        button.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        return button;
    }
}
