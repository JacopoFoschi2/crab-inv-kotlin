package it.unibo.crabinv.view

import it.unibo.crabinv.SceneManager
import it.unibo.crabinv.controller.core.i18n.LocalizationController
import it.unibo.crabinv.controller.core.metagame.MetaGameController
import it.unibo.crabinv.controller.core.metagame.MetaGameControllerImpl
import it.unibo.crabinv.controller.core.save.SessionController
import it.unibo.crabinv.controller.core.save.SessionControllerImpl
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.engine.GameEngineState
import it.unibo.crabinv.model.core.i18n.TextKeys
import it.unibo.crabinv.model.core.save.Save
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import java.io.IOException
import java.io.UncheckedIOException
import java.util.Objects
import kotlin.math.max

/**
 * View `` Class to show the game when running.
 */
class GameScreen(
    sceneManager: SceneManager?,
    loc: LocalizationController,
    save: Save?,
    repo: SaveRepository?,
) {
    private val sceneManager: SceneManager = Objects.requireNonNull<SceneManager>(sceneManager, "sceneManager must not be null")
    private val save: Save = Objects.requireNonNull<Save>(save, "save must not be null")
    private val repo: SaveRepository = Objects.requireNonNull<SaveRepository>(repo, "SaveRepository must not be null")
    private var metaGameController: MetaGameController? = null
    private var timer: AnimationTimer? = null
    private var gameRenderer: GameRenderer? = null
    private var lastEngineState: GameEngineState? = null
    private val health: String = loc.getString(TextKeys.HP)
    private val currency: String = loc.getString(TextKeys.CURRENCY)

    val view: Node
        /**
         * The Node of the [GameScreen] view.
         * @return the Node of the [GameScreen] view
         */
        get() {
            val root = StackPane()
            val width = sceneManager.width
            val height = sceneManager.height
            val canvas = Canvas(width, height)
            val hp = Label()
            val money = Label()
            val hud =
                VBox(ViewParameters.DEFAULT_HUD_MARGIN.toDouble(), hp, money)
            StackPane.setAlignment(hud, Pos.TOP_LEFT)
            StackPane.setMargin(
                hud,
                Insets(ViewParameters.DEFAULT_INSETS_PANE.toDouble()),
            )
            root.children.addAll(canvas, hud)
            val sessionController: SessionController = SessionControllerImpl(this.save)
            metaGameController = MetaGameControllerImpl(sessionController, repo)
            this.gameRenderer = GameRenderer(canvas.graphicsContext2D)
            metaGameController!!.startGame()
            metaGameController!!.getInputController()
            this.lastEngineState = metaGameController!!.getGameEngineState()
            canvas.isFocusTraversable = true
            canvas.onKeyPressed =
                EventHandler { e: KeyEvent? ->
                    metaGameController!!.getInputController().onKeyPressed(e!!.code.getCode())
                    if (e.code == KeyCode.ESCAPE) {
                        sceneManager.showPauseMenu()
                        metaGameController!!.getGameLoopController().resume()
                    }
                }
            canvas.onKeyReleased =
                EventHandler { e: KeyEvent? ->
                    metaGameController!!.getInputController().onKeyReleased(e!!.code.getCode())
                }
            this.timer =
                object : AnimationTimer() {
                    private var lastNow: Long = 0

                    override fun handle(now: Long) {
                        if (lastNow == 0L) {
                            lastNow = now
                            return
                        }
                        hp.text = health + ": " + sessionController.getGameSession().getPlayerHealth()
                        money.text = currency + ": " + sessionController.getGameSession().getCurrency()
                        val frameElapsedMillis = max(0L, (now - lastNow) / 1000000L)
                        try {
                            gameRenderer!!.render(metaGameController!!.stepCheck(frameElapsedMillis))
                        } catch (error: IOException) {
                            throw UncheckedIOException(error)
                        }
                        val currentEngineState = metaGameController!!.getGameEngineState()
                        if (currentEngineState != lastEngineState) {
                            lastEngineState = currentEngineState
                            engineStatusTrigger(currentEngineState)
                        }
                        lastNow = now
                    }
                }
            timer!!.start()
            Platform.runLater { canvas.requestFocus() }
            return root
        }

    /**
     * Checks the Win/Game Over Conditions of the [it.unibo.crabinv.model.core.engine.GameEngine] and triggers the relative screens.
     * @param state the [GameEngineState] of the [it.unibo.crabinv.model.core.engine.GameEngine]
     */
    private fun engineStatusTrigger(state: GameEngineState?) {
        if (state == GameEngineState.GAME_OVER || state == GameEngineState.WIN) {
            closeEngineStep1()
            closeEngineStep2()
            when (state) {
                GameEngineState.GAME_OVER -> {
                    sceneManager.showGameOver(GameOver.MessageTypes.GAME_OVER)
                }

                GameEngineState.WIN -> {
                    sceneManager.showGameOver(GameOver.MessageTypes.VICTORY)
                }
            }
        }
    }

    val resume: Runnable
        /**
         * Exposes the resume method of the gameLoop to be used by the resume menu.
         * @return the runnable of the resume method
         */
        get() = Runnable { metaGameController!!.getGameLoopController().resume() }

    /**
     * Executes the first part of closing procedures for the [it.unibo.crabinv.model.core.engine.GameEngine].
     */
    private fun closeEngineStep1() {
        timer!!.stop()
    }

    /**
     * Executes the second part of closing procedures for the [it.unibo.crabinv.model.core.engine.GameEngine].
     */
    private fun closeEngineStep2() {
        timer = null
        gameRenderer = null
        metaGameController = null
    }

    val gameOver: Runnable
        /**
         * Exposes the gameOver method of the gameLoop to be used by the resume menu.
         * @return the runnable of the gameOver method
         */
        get() =
            Runnable {
                closeEngineStep1()
                metaGameController!!.endGame()
                closeEngineStep2()
            }
}
