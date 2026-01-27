package it.unibo.crabinv;

import com.sun.scenario.Settings;
import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.i18n.Localization;
import it.unibo.crabinv.config.AppSettings;
import it.unibo.crabinv.config.SettingsFileManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class App extends Application {
    private final LocalizationController loc = new LocalizationController(new Localization());
    private final AudioController audio = new AudioController(new JavaFXSoundManager());

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.initStyle(StageStyle.UNDECORATED); // rimuove barra e bordi
        mainStage.setMaximized(true);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene mainScene;
        StackPane root = new StackPane();
        mainScene = new Scene(root);
        SceneManager manager = new SceneManager(root, loc, audio, bounds);

        mainScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/style/style.css")).toExternalForm()
        );
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

    public static class Main {
        static void main(String... args) {
            Application.launch(App.class, args);
        }
    }
}
