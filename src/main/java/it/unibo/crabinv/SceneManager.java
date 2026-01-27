package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.View.LanguageSelection;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.StackPane;

/**
 * Provides the apis to orchestrate the changes inside the main stage
 */
public class SceneManager {
    private final StackPane root;
    private final LocalizationController loc;
    private final AudioController audio;
    private final double width;
    private final double height;

    /**
     * Constructs the sceneManager
     * @param root the root stackPane
     * @param loc the global localization controller
     * @param audio the global audio controller
     * @param bounds the bounds of the screen
     */
    public SceneManager(StackPane root, LocalizationController loc, AudioController audio, Rectangle2D bounds) {
        this.root = root;
        this.loc = loc;
        this.audio = audio;
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
    }

    /**
     * Sets the language selection screen as the shown one
     */
    public void showLanguageSelection() {
        root.getChildren().setAll(new LanguageSelection(this, loc, audio).getView());
    }

    /**
     * Sets the main menu screen as the shown one
     */
    public void showMainMenu() {
        root.getChildren().setAll(new StackPane());
    }

    /**
     * @return the width of the screen
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the eight of the screen
     */
    public double getHeight() {
        return height;
    }
}
