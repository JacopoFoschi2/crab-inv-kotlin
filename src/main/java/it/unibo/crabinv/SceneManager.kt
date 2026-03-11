package it.unibo.crabinv

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.controller.core.save.SaveControllerImpl
import it.unibo.crabinv.core.config.AppPaths
import it.unibo.crabinv.core.persistence.json.SaveRepositoryGson
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.audio.BGMTracks
import it.unibo.crabinv.model.core.audio.SFXTracks
import it.unibo.crabinv.model.core.save.Save
import it.unibo.crabinv.model.powerups.PowerUpFactory
import it.unibo.crabinv.view.GameOver
import it.unibo.crabinv.view.GameOver.MessageTypes
import it.unibo.crabinv.view.GameScreen
import it.unibo.crabinv.view.LanguageSelection
import it.unibo.crabinv.view.MainMenu
import it.unibo.crabinv.view.MemorialScreen
import it.unibo.crabinv.view.PauseMenu
import it.unibo.crabinv.view.Settings
import it.unibo.crabinv.view.ShopMenu
import javafx.geometry.Rectangle2D
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane

/**
 * Provides the apis to orchestrate the changes inside the main stage.
 * @param root the root stackPane
 * @param loc the global localization controller
 * @param audio the global audio controller
 * @param bounds the bounds of the screen
 */
class SceneManager(
    private val root: StackPane,
    private val loc: LocalizationController,
    private val audio: AudioController,
    bounds: Rectangle2D,
) {
    /**
     * @return the width of the screen
     */
    val width: Double = bounds.width

    /**
     * @return the eight of the screen
     */
    val height: Double = bounds.height
    private val repo: SaveRepository = SaveRepositoryGson(AppPaths.getSaves())
    private val save: Save = SaveControllerImpl(this.repo).saveControlAndLoad()
    private var pauseMenu: Pane? = null

    /**
     * Sets the language selection screen as the shown one.
     */
    fun showLanguageSelection() {
        root.children.setAll(LanguageSelection(this, loc, audio).view)
        audio.playBGM(BGMTracks.MENU)
    }

    /**
     * Sets the main menu screen as the shown one.
     */
    fun showMainMenu() {
        root.children.setAll(MainMenu(this, loc, audio).getView())
        audio.playBGM(BGMTracks.MENU)
    }

    /**
     * Sets the shop screen as the shown one.
     */
    fun showShop() {
        root.children.setAll(
            ShopMenu(
                this,
                loc,
                audio,
                save,
                repo,
                PowerUpFactory.createShopPowerUps(),
            ).getView(),
        )
    }

    /**
     * Sets the memorial screen as the shown one.
     */
    fun showMemorial() {
        root.children.setAll(MemorialScreen(this, loc, audio, save).getView())
    }

    /**
     * Sets the settings screen as the shown one.
     */
    fun showSettings() {
        root.children.setAll(Settings(this, loc, audio).getView())
    }

    /**
     * Sets the gameOver screen as the shown one.
     * @param message the type of message to be displayed, either game over or victory
     */
    fun showGameOver(message: MessageTypes?) {
        root.children.setAll(GameOver(this, loc, audio, message).getView())
    }

    /**
     * Sets the Game Screen as the shown one.
     */
    fun showGame() {
        val gameScreen = GameScreen(this, loc, save, repo)
        val gameView = gameScreen.getView()
        pauseMenu = PauseMenu(this, loc, audio, gameScreen.resume, gameScreen.gameOver).getView()
        pauseMenu!!.isVisible = false
        root.children.setAll(gameView, pauseMenu)
        audio.playBGM(BGMTracks.LEVEL)
    }

    /**
     * Shows pause menu.
     * To be used during game
     */
    fun showPauseMenu() {
        if (pauseMenu != null) {
            pauseMenu!!.isVisible = true
            audio.pauseBGM()
            audio.playSFX(SFXTracks.MENU_SELECT)
        }
    }

    /**
     * Hides pause menu.
     * To be used during game
     */
    fun hidePauseMenu() {
        if (pauseMenu != null) {
            pauseMenu!!.isVisible = false
            audio.resumeBGM()
            audio.playSFX(SFXTracks.MENU_SELECT)
        }
    }
}
