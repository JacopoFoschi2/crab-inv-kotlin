package it.unibo.crabinv.controller.core.metagame

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.collision.CollisionController
import it.unibo.crabinv.controller.core.gameloop.GameLoopController
import it.unibo.crabinv.controller.core.gameloop.GameLoopControllerImpl
import it.unibo.crabinv.controller.core.input.InputController
import it.unibo.crabinv.controller.core.input.InputControllerPlayer
import it.unibo.crabinv.controller.core.input.InputMapperImpl
import it.unibo.crabinv.controller.core.save.SaveControllerImpl
import it.unibo.crabinv.controller.core.save.SessionController
import it.unibo.crabinv.controller.entities.player.PlayerController
import it.unibo.crabinv.core.persistence.repository.SaveRepository
import it.unibo.crabinv.model.core.audio.JavaFXSoundManager
import it.unibo.crabinv.model.core.engine.GameEngine
import it.unibo.crabinv.model.core.engine.GameEngineImpl
import it.unibo.crabinv.model.core.engine.GameEngineState
import it.unibo.crabinv.model.core.save.GameSession
import it.unibo.crabinv.model.core.snapshot.GameSnapshot
import it.unibo.crabinv.model.entities.enemies.BaseEnemyFactoryLogic
import it.unibo.crabinv.model.entities.enemies.rewardservice.EnemyRewardService
import it.unibo.crabinv.model.levels.LevelFactoryImpl
import java.io.IOException
import java.util.Objects

/**
 * Implementation of [MetaGameController].
 * @param sessionController the [SessionController] used by the [MetaGameControllerImpl]
 * @param saveRepository    the [SaveRepository] used by the [MetaGameControllerImpl]
 */
class MetaGameControllerImpl(
    sessionController: SessionController?,
    saveRepository: SaveRepository?,
) : MetaGameController {
    private val sessionController: SessionController =
        Objects
            .requireNonNull<SessionController>(sessionController, "SessionController cannot be null")
    private val saveRepository: SaveRepository =
        Objects
            .requireNonNull<SaveRepository>(saveRepository, "SaveRepository cannot be null")
    private val gameEngine: GameEngine = GameEngineImpl()
    override var inputController: InputController? = null
    override var gameLoopController: GameLoopController? = null

    override fun startGame() {
        val gameSession =
            Objects.requireNonNull<GameSession>(
                this.sessionController.newGameSession(),
                "GameSession cannot be null",
            )
        val sharedAudio = AudioController(JavaFXSoundManager())
        this.gameEngine.init(
            gameSession,
            LevelFactoryImpl(),
            BaseEnemyFactoryLogic(),
            EnemyRewardService(gameSession),
            CollisionController(sharedAudio),
        )
        this.inputController = InputControllerPlayer(InputMapperImpl())
        this.gameLoopController =
            GameLoopControllerImpl(
                gameEngine,
                this.inputController!!,
                PlayerController(
                    gameEngine.getPlayer(),
                    sharedAudio,
                    this.gameEngine,
                ),
                sharedAudio,
            )
    }

    @Throws(IOException::class)
    override fun stepCheck(frameElapsedMillis: Long): GameSnapshot? {
        checkNotNull(gameLoopController) { "No Game is currently active" }
        val gameSnapshot = this.gameLoopController!!.step(frameElapsedMillis)
        val gameSession = this.sessionController.gameSession ?: return gameSnapshot
        checkAndManageGameEnd(gameSession)
        return gameSnapshot
    }

    override val gameEngineState: GameEngineState?
        get() = this.gameEngine.getGameState()

    override fun endGame() {
        this.gameEngine.gameOver()
    }

    @Throws(IOException::class)
    override fun updateSave() {
        SaveControllerImpl(saveRepository).updateSave(this.sessionController.save())
    }

    /**
     * Checks if the [GameSession] has a Game over or win status.
     * @param gameSession the [GameSession] to check
     * @throws IOException if an IO error occurs
     */
    @Throws(IOException::class)
    private fun checkAndManageGameEnd(gameSession: GameSession) {
        if (gameSession.isGameOver() || gameSession.isGameWon()) {
            this.sessionController.gameOverGameSession()
            this.gameLoopController = null
            this.inputController = null
            updateSave()
        }
    }
}
