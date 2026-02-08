package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.core.i18n.TextKeys;
import it.unibo.crabinv.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Provides the method to create the pause menu GUI
 */
public class PauseMenu {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Runnable resumeMethod;
    private final Runnable gameOver;
    private final Pos settingsAlignment = Pos.CENTER_LEFT;
    private final SettingComponentsGenerator components;

    /**
     * @param sceneManager the instance of SceneManager
     * @param loc the instance of LocalizationController
     * @param audio the instance of AudioController
     */
    public PauseMenu(SceneManager sceneManager, LocalizationController loc, AudioController audio, Runnable resumeMethod, Runnable gameOver) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.resumeMethod = resumeMethod;
        this.gameOver = gameOver;
        components = new SettingComponentsGenerator(sceneManager, loc, audio, settingsAlignment);
    }

    /**
     * @return the Stackpane containing a working pauseMenu
     */
    public Pane getView() {
        StackPane pane = new StackPane();
        pane.getStyleClass().add("pause-pane");
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(400);
        Label title = new Label(loc.getString(TextKeys.PAUSE));
        title.getStyleClass().add("title");
        VBox mainColumn = new VBox(15);
        mainColumn.setAlignment(settingsAlignment);
        HBox bgmVolume = components.createVolumeSlider(audio.getBGMVolume(), audio::setBGMVolume, audio::playSFX, TextKeys.BGM_VOLUME);
        HBox sfxVolume = components.createVolumeSlider(audio.getSFXVolume(), audio::setSFXVolume, audio::playSFX, TextKeys.SFX_VOLUME);
        CheckBox bgmMute = components.createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        CheckBox sfxMute = components.createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        mainColumn.getChildren().addAll(bgmVolume, sfxVolume, bgmMute, sfxMute);
        for (var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ ->
                    audio.playSFX(SFXTracks.MENU_HOVER));
        }
        Button resume = createPauseMenuButton(loc.getString(TextKeys.RESUME), resumeMethod, sceneManager::hidePauseMenu);
        Button exit = createPauseMenuButton(loc.getString(TextKeys.ABANDON), gameOver, sceneManager::showMainMenu);
        HBox buttons = new HBox(20, resume, exit);
        buttons.setAlignment(Pos.CENTER);
        content.getChildren().addAll(title, mainColumn, buttons);
        pane.getChildren().add(content);
        return pane;
    }

    /**
     * Creates the buttons for the pause menu
     * @param text the text of the button
     * @param method the method it's linked to
     * @param changeScene the method to change the scene
     * @return the complete button
     */
    private Button createPauseMenuButton(String text, Runnable method, Runnable changeScene) {
        Button button = new Button(text);
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
