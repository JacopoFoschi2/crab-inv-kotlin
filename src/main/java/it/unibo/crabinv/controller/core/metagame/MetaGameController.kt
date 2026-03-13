package it.unibo.crabinv.controller.core.metagame

import it.unibo.crabinv.controller.core.gameloop.GameLoopController
import it.unibo.crabinv.controller.core.input.InputController
import it.unibo.crabinv.model.core.engine.GameEngineState
import it.unibo.crabinv.model.core.snapshot.GameSnapshot
import java.io.IOException

/**
 * Orchestrates [SessionController], [GameLoopController], [GameEngine].
 */
interface MetaGameController {
    /**
     * Manages and instructs the components when a new [GameSession] must be created.
     */
    fun startGame()

    /**
     * Controls the status of the components, calls a save update when the game ends.
     * @param frameElapsedMillis the milliseconds elapsed of the frame
     * @return the GameSnapshot to check and save when needed
     * @throws IOException if an IO error occurs during the save update
     */
    @Throws(IOException::class)
    fun stepCheck(frameElapsedMillis: Long): GameSnapshot?

    /**
     * Exposes the [InputController] to make it usable.
     * @return the [InputController]
     */
    val inputController: InputController?

    /**
     * Exposes the [GameLoopController].
     * @return the [GameLoopController]
     */
    val gameLoopController: GameLoopController?

    /**
     * Exposes the [GameEngineState] of the underlying [GameEngine].
     * @return a [GameEngineState]
     */
    val gameEngineState: GameEngineState?

    /**
     * Closes the game, triggers a Game Over, to be used by the pause menu.
     */
    fun endGame()

    /**
     * Updates the [Save].
     * @throws IOException if an IO error occurs during the save update
     */
    @Throws(IOException::class)
    fun updateSave()
}
