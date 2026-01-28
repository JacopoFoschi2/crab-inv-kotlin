package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.SceneManager;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Settings {
    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;

    public Settings(SceneManager sceneManager, LocalizationController loc, AudioController audio) {
        this.sceneManager = sceneManager;
        this.loc = loc;
        this.audio = audio;
    }

    public Pane getView() {
        Pane pane = new StackPane();
        VBox mainColumn = new VBox(20);
        Label title = new Label("SETTINGS");
        HBox bgmVolume = new HBox(20);
        Slider bgmVolumeSlider = new Slider(0, 100, audio.getBGMVolume());
        Label bgmTitle = new Label("BGM Volume");
        bgmVolume.getChildren().addAll(bgmVolumeSlider, bgmTitle);
        HBox sfxVolume = new HBox(20);
        Slider sfxVolumeSlider = new Slider(0, 100, audio.getSFXVolume());
        Label sfxTitle = new Label("SFX Volume");
        sfxVolume.getChildren().addAll(sfxVolumeSlider, sfxTitle);
        CheckBox bgmMute = new CheckBox("Mute BGM");
        CheckBox sfxMute = new CheckBox("Mute SFX");
        mainColumn.getChildren().addAll(title, bgmVolume, sfxVolume, bgmMute, sfxMute);
        pane.getChildren().addAll(mainColumn);
        return pane;
    }
}
