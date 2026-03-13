package it.unibo.crabinv.controller.core.gameloop

import it.unibo.crabinv.controller.core.audio.AudioController
import it.unibo.crabinv.controller.core.input.InputController
import it.unibo.crabinv.controller.entities.enemy.EnemyController
import it.unibo.crabinv.controller.entities.player.PlayerController
import it.unibo.crabinv.model.core.engine.GameEngine
import it.unibo.crabinv.model.core.engine.GameEngineState
import it.unibo.crabinv.model.core.input.InputSnapshot
import it.unibo.crabinv.model.core.snapshot.GameSnapshot
import it.unibo.crabinv.model.entities.enemies.Enemy
import it.unibo.crabinv.model.entities.entity.Delta
import java.util.IdentityHashMap
import kotlin.math.min

/**
 * Implementation of [GameLoopController].
 * @param gameEngine the [GameEngine] used by the [GameLoopControllerImpl]
 * @param inputController the [GameEngine] used by the [GameLoopControllerImpl]
 * @param playerController the [GameEngine] used by the [GameLoopControllerImpl]
 * @param audioController the [GameEngine] used by the [GameLoopControllerImpl]
 */
class GameLoopControllerImpl(
    private val gameEngine: GameEngine,
    private val inputController: InputController,
    private val playerController: PlayerController,
    private val audioController: AudioController,
) : GameLoopController {
    override val tickDurationMillis: Long = STANDARD_TICK_MILLIS
    private val maxTicksPerFrame: Int = STANDARD_MAX_TICKS_PER_FRAME
    private val enemyControllerMap: MutableMap<Enemy?, EnemyController?> = IdentityHashMap<Enemy?, EnemyController?>()
    override var accumulatedMillis: Long = 0
        private set
    override var totalElapsedTicks: Long = 0
        private set
    override var latestSnapshot: GameSnapshot?
        private set

    init {
        tickUpdate()
        this.latestSnapshot = this.gameEngine.snapshot()
    }

    override fun step(frameElapsedMillis: Long): GameSnapshot? {
        checkPause()
        checkResume()
        if (this.gameEngine.getGameState() == GameEngineState.GAME_OVER) {
            return latestSnapshot
        }

        if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
            accumulateTime(frameElapsedMillis)
            val nextStepTicks = calculateTicks()
            executeTicks(nextStepTicks)
            if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
                updateSnapshot(nextStepTicks)
            }
        }
        return latestSnapshot
    }

    override fun pause() {
        this.gameEngine.pauseGame()
    }

    override fun resume() {
        this.gameEngine.resumeGame()
    }

    /**
     * Controls if the game is in the correct state to be resumed.
     */
    private fun checkResume() {
        if (this.gameEngine.getGameState() == GameEngineState.PAUSED &&
            inputController.inputState!!.isUnpause()
        ) {
            resume()
        }
    }

    /**
     * Controls if the game is in the correct state to be paused.
     */
    private fun checkPause() {
        if (inputController.inputState!!.isPause() &&
            this.gameEngine.getGameState() == GameEngineState.RUNNING
        ) {
            pause()
        }
    }

    /**
     * Adds the milliseconds of the last frame to the accumulated milliseconds.
     * @param frameElapsedMillis the milliseconds to add.
     */
    private fun accumulateTime(frameElapsedMillis: Long) {
        this.accumulatedMillis += frameElapsedMillis
    }

    private fun calculateTicks(): Int {
        val ticks = this.accumulatedMillis / this.tickDurationMillis
        val cappedTicks = min(ticks, maxTicksPerFrame.toLong())
        return cappedTicks.toInt()
    }

    /**
     * Requests the GameEngine to calculate the game logic for the number of ticks.
     * @param nextStepTicks the ticks the Game Engine must calculate.
     */
    private fun executeTicks(nextStepTicks: Int) {
        for (i in 0..<nextStepTicks) {
            playerUpdate()
            enemyUpdate()
            tickUpdate()
        }
    }

    /**
     * Updates the Player input data.
     */
    private fun playerUpdate() {
        val inputSnapshot: InputSnapshot = inputController.inputState!!
        this.playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta())
    }

    /**
     * Updates the Active enemies' data.
     */
    private fun enemyUpdate() {
        val enemyList = this.gameEngine.getEnemyList()
        for (enemy in enemyList) {
            enemyControllerMap.computeIfAbsent(enemy) { e: Enemy? ->
                EnemyController(
                    e,
                    this.audioController,
                    gameEngine,
                )
            }
        }
        for (enemy in enemyList) {
            val enemyController = enemyControllerMap[enemy]
            enemyController?.update(Delta.INCREASE)
        }
        enemyControllerMap.keys.removeIf { e: Enemy? -> !e!!.isAlive() || !enemyList.contains(e) }
    }

    /**
     * Calls the [GameEngine] to execute the ticks.
     */
    private fun tickUpdate() {
        this.gameEngine.tick()
        totalElapsedTicks++
    }

    /**
     * Calls the [GameEngine] to update its snapshot.
     * @param nextStepTicks the ticks for the next step.
     */
    private fun updateSnapshot(nextStepTicks: Int) {
        this.accumulatedMillis -= nextStepTicks * this.tickDurationMillis
        this.latestSnapshot = this.gameEngine.snapshot()
    }

    companion object {
        private const val STANDARD_TICK_MILLIS: Long = 16
        private const val STANDARD_MAX_TICKS_PER_FRAME = 5
    }
}
