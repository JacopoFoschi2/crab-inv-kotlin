package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.View.LanguageSelection;
import it.unibo.crabinv.View.MainMenu;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.StackPane;

public class SceneManager {
    private final StackPane root;
    private final LocalizationController loc;
    private final AudioController audio;
    private final double width;
    private final double height;

    public SceneManager(StackPane root, LocalizationController loc, AudioController audio, Rectangle2D bounds) {
        this.root = root;
        this.loc = loc;
        this.audio = audio;
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
    }

    public void showLanguageSelection() {
        root.getChildren().setAll(new LanguageSelection(this, loc, audio).getView());
    }

    public void showMainMenu() {
        root.getChildren().setAll(new MainMenu(this,loc,audio).getView());
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
