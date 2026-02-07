package it.unibo.crabinv;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.core.i18n.Localization;
import it.unibo.crabinv.core.config.AppSettings;
import it.unibo.crabinv.core.config.SettingsFileManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Provides the application with the methods it needs to run
 */
public class App extends Application {
    private final LocalizationController loc = new LocalizationController(new Localization());
    private final AudioController audio = new AudioController(new JavaFXSoundManager());

    @Override
    public void start(Stage mainStage) throws IOException {
        //Tweaks the initial config of the stage
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setMaximized(true);
        //The bounds of the screen
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene mainScene;
        StackPane root = new StackPane();
        mainScene = new Scene(root);
        SceneManager manager = new SceneManager(root, loc, audio, bounds);
        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm()
        );
        //Attempts to read the settings.json file and handles setting them
        AppSettings settings = SettingsFileManager.load();
        if (settings != null) {
            loc.setLanguage(settings.locales());
            audio.setBGMVolume(settings.bgmVolume());
            audio.setSFXVolume(settings.sfxVolume());
            if (settings.isBGMMuted()) {
                audio.toggleBGMMute();
            }
            if (settings.isSFXMuted()) {
                audio.toggleSFXMute();
            }
        }
        mainScene.setCursor(Cursor.NONE);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Crab Invaders");
        mainStage.setResizable(false);
        if (loc.getLanguage() == null) {
            manager.showLanguageSelection();
        } else {
            manager.showMainMenu();
        }
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        //used for saving the state of the settings before closing the app
        AppSettings settings = new AppSettings(
                audio.getBGMVolume(),
                audio.getSFXVolume(),
                audio.isBGMMuted(),
                audio.isSFXMuted(),
                loc.getLanguage()
        );
        SettingsFileManager.save(settings);
        super.stop();
    }

    /**
     * Provides the launcher of the application
     */
    public static class Main {
        static void main(String... args) {
            Application.launch(App.class, args);
        }
    }
}
