package it.unibo.crabinv;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.model.core.i18n.Localization;
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
 * Provides the application with the methods it needs to run.
 */
public final class App extends Application {
    private final LocalizationController loc = new LocalizationController(new Localization());
    private final AudioController audio = new AudioController(new JavaFXSoundManager());

    @Override
    public void start(final Stage mainStage) throws IOException {
        //Tweaks the initial config of the stage
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setMaximized(true);
        //The bounds of the screen
        final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        final Scene mainScene;
        final StackPane root = new StackPane();
        mainScene = new Scene(root);
        final SceneManager manager = new SceneManager(root, loc, audio, bounds);
        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm()
        );
        //Attempts to read the settings.json file and handles setting them
        final AppSettings settings = SettingsFileManager.load();
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
        final AppSettings settings = new AppSettings(
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
     * Provides the launcher of the application.
     */
    public static class Main {
        /**
         * Prevents instantiation.
         */
        private Main() { }

        static void main(final String... args) {
            launch(App.class, args);
        }
    }
}
