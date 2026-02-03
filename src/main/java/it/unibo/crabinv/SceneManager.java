package it.unibo.crabinv;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Model.PowerUpsShop.Shop;
import it.unibo.crabinv.Model.audio.BGMTracks;
import it.unibo.crabinv.View.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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
        audio.playBGM(BGMTracks.MENU);
    }

    /**
     * Sets the main menu screen as the shown one
     */
    public void showMainMenu() {
        root.getChildren().setAll(new MainMenu(this,loc,audio).getView());
        audio.playBGM(BGMTracks.MENU);
    }
    /*
    public void showShop(){
        root.getChildren().setAll(root.getChildren().setAll(new ShopMenu(this,loc,audio).getView()));
    }

     */

    public void showSettings() {
        root.getChildren().setAll(new Settings(this,loc,audio).getView());
    }

    /**
     * Sets the Game Screen as the shown one
     */
    public void showGame(){
        Node gameView = new GameScreen(this,loc, audio).getView();
        Pane pauseMenu = new PauseMenu(this, loc, audio).getView();
        pauseMenu.setVisible(false);
        root.getChildren().setAll(gameView,pauseMenu);
        audio.playBGM(BGMTracks.LEVEL);
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
