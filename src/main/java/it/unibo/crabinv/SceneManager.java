package it.unibo.crabinv;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.i18n.LocalizationController;
import it.unibo.crabinv.controller.save.SaveControllerImpl;
import it.unibo.crabinv.model.core.audio.BGMTracks;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.powerups.PowerUpFactory;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.view.GameOver;
import it.unibo.crabinv.view.GameScreen;
import it.unibo.crabinv.view.LanguageSelection;
import it.unibo.crabinv.view.MainMenu;
import it.unibo.crabinv.view.MemorialScreen;
import it.unibo.crabinv.view.PauseMenu;
import it.unibo.crabinv.view.Settings;
import it.unibo.crabinv.view.ShopMenu;
import it.unibo.crabinv.core.config.AppPaths;
import it.unibo.crabinv.persistence.json.SaveRepositoryGson;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Provides the apis to orchestrate the changes inside the main stage.
 */
public class SceneManager {
    private final StackPane root;
    private final LocalizationController loc;
    private final AudioController audio;
    private final double width;
    private final double height;
    private final SaveRepository repo;
    private final Save save;
    private Pane pauseMenu;

    /**
     * Constructs the sceneManager.
     *
     * @param root the root stackPane
     * @param loc the global localization controller
     * @param audio the global audio controller
     * @param bounds the bounds of the screen
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public SceneManager(
            final StackPane root,
            final LocalizationController loc,
            final AudioController audio,
            final Rectangle2D bounds) throws IOException {
        this.root = root;
        this.loc = loc;
        this.audio = audio;
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
        this.repo = new SaveRepositoryGson(AppPaths.getSaves());
        this.save = new SaveControllerImpl(this.repo).saveControlAndLoad();
    }

    /**
     * Sets the language selection screen as the shown one.
     */
    public void showLanguageSelection() {
        root.getChildren().setAll(new LanguageSelection(this, loc, audio).getView());
        audio.playBGM(BGMTracks.MENU);
    }

    /**
     * Sets the main menu screen as the shown one.
     */
    public void showMainMenu() {
        root.getChildren().setAll(new MainMenu(this, loc, audio).getView());
        audio.playBGM(BGMTracks.MENU);
    }

    /**
     * Sets the shop screen as the shown one.
     */
    public void showShop() {
        root.getChildren().setAll(new ShopMenu(this, loc, audio, save, repo,
                PowerUpFactory.createShopPowerUps()).getView());
    }

    /**
     * Sets the memorial screen as the shown one.
     */
    public void showMemorial() {
        root.getChildren().setAll(new MemorialScreen(this, loc, audio, save).getView());
    }

    /**
     * Sets the settings screen as the shown one.
     */
    public void showSettings() {
        root.getChildren().setAll(new Settings(this, loc, audio).getView());
    }

    /**
     * Sets the gameOver screen as the shown one.
     *
     * @param message the type of message to be displayed, either game over or victory
     */
    public void showGameOver(final GameOver.MessageTypes message) {
        root.getChildren().setAll(new GameOver(this, loc, audio, message).getView());
    }

    /**
     * Sets the Game Screen as the shown one.
     */
    public void showGame() {
        final GameScreen gameScreen = new GameScreen(this, save, repo);
        final Node gameView = gameScreen.getView();
        pauseMenu = new PauseMenu(this, loc, audio, gameScreen.getResume(), gameScreen.getGameOver()).getView();
        pauseMenu.setVisible(false);
        root.getChildren().setAll(gameView, pauseMenu);
        audio.playBGM(BGMTracks.LEVEL);
    }

    /**
     * Shows pause menu.
     * To be used during game
     */
    public void showPauseMenu() {
        if (pauseMenu != null) {
            pauseMenu.setVisible(true);
            audio.pauseBGM();
            audio.playSFX(SFXTracks.MENU_SELECT);
        }
    }

    /**
     * Hides pause menu.
     * To be used during game
     */
    public void hidePauseMenu() {
        if (pauseMenu != null) {
            pauseMenu.setVisible(false);
            audio.resumeBGM();
            audio.playSFX(SFXTracks.MENU_SELECT);
        }
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
