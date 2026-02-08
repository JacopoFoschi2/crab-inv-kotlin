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
    private final Pos settingsAlignment = Pos.CENTER_LEFT;
    private final SettingComponentsGenerator components;

    /**
     * @param sceneManager the instance of SceneManager
     * @param loc the instance of LocalizationController
     * @param audio the instance of AudioController
     */
    public PauseMenu(SceneManager sceneManager, LocalizationController loc, AudioController audio, Runnable resumeMethod) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
        this.resumeMethod = resumeMethod;
        components = new SettingComponentsGenerator(sceneManager, loc, audio, settingsAlignment);
    }

    /**
     * @return the Stackpane containing a working pauseMenu
     */
    public Pane getView() {
        StackPane pane = new StackPane();
        Label title = new Label(loc.getString(TextKeys.PAUSE));
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
        HBox bgmVolume = components.createVolumeSlider(audio.getBGMVolume(), audio::setBGMVolume, audio::playSFX, TextKeys.BGM_VOLUME);
        HBox sfxVolume = components.createVolumeSlider(audio.getSFXVolume(), audio::setSFXVolume, audio::playSFX, TextKeys.SFX_VOLUME);
        CheckBox bgmMute = components.createMute(TextKeys.BGM_MUTE, audio.isBGMMuted(), audio::toggleBGMMute);
        CheckBox sfxMute = components.createMute(TextKeys.SFX_MUTE, audio.isSFXMuted(), audio::toggleSFXMute);
        Button exit = new Button(loc.getString(TextKeys.EXIT_GAME));
        exit.setOnAction(_ -> {
            sceneManager.showMainMenu();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        exit.getStyleClass().add("app-button");
        StackPane.setAlignment(exit, Pos.BOTTOM_CENTER);
        Button resume = new Button(loc.getString(TextKeys.RESUME));
        resume.setOnAction(_ -> {
            sceneManager.hidePauseMenu();
            resumeMethod.run();
            audio.playSFX(SFXTracks.MENU_SELECT);
        });
        StackPane.setMargin(exit, new Insets(0,0,60,0));
        mainColumn.getChildren().addAll(bgmVolume, sfxVolume, bgmMute, sfxMute);
        for (var child : mainColumn.getChildren()) {
            child.focusedProperty().addListener(_ -> audio.playSFX(SFXTracks.MENU_HOVER));
        }
        pane.getChildren().addAll(grid, exit, resume);
        return pane;
    }
}
