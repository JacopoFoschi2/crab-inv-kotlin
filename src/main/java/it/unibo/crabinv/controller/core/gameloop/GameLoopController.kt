package it.unibo.crabinv.controller.core.gameloop

import it.unibo.crabinv.model.core.snapshot.GameSnapshot

/**
 * Controls the passing of time and creates the [GameSnapshot] to be rendered.
 */
interface GameLoopController {
    /**
     * @return the duration in milliseconds of a tick.
     */
    val tickDurationMillis: Long

    /**
     * @return the currently total accumulated milliseconds, needed to calculate the next step.
     */
    val accumulatedMillis: Long

    /**
     * @return the total of ticks since the start of the engine.
     */
    val totalElapsedTicks: Long

    /**
     * Calculates the game engine next step [GameSnapshot] to be passed onto the renderer.
     * Advances the game logic by the calculated step.
     * @param frameElapsedMillis the milliseconds accumulated between two frames, must be positive.
     * @return the calculated [GameSnapshot]
     */
    fun step(frameElapsedMillis: Long): GameSnapshot?

    /**
     * @return the latest [GameSnapshot]
     */
    val latestSnapshot: GameSnapshot?

    /**
     * Requests the [it.unibo.crabinv.model.core.engine.GameEngine] to pause the game.
     */
    fun pause()

    /**
     * Requests the [it.unibo.crabinv.model.core.engine.GameEngine] to resume the game.
     */
    fun resume()
}
