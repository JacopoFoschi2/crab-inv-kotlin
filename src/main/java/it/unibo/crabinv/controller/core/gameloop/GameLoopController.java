package it.unibo.crabinv.controller.core.gameloop;

import it.unibo.crabinv.model.core.engine.GameEngine;
import it.unibo.crabinv.model.core.snapshot.GameSnapshot;

/**
 * Controls the passing of time and creates the {@link GameSnapshot} to be rendered.
 */
public interface GameLoopController {
    /**
     * @return the duration in milliseconds of a tick.
     */
    long getTickDurationMillis();

    /**
     * @return the currently total accumulated milliseconds, needed to calculate the next step.
     */
    long getAccumulatedMillis();

    /**
     * @return the total of ticks since the start of the engine.
     */
    long getTotalElapsedTicks();

    /**
     * Calculates the game engine next step {@link GameSnapshot} to be passed onto the renderer.
     * Advances the game logic by the calculated step.
     *
     * @param frameElapsedMillis the milliseconds accumulated between two frames, must be positive.
     * @return the calculated {@link GameSnapshot}
     */
    GameSnapshot step(long frameElapsedMillis);

    /**
     * Returns the {@link GameSnapshot} to be rendered, does not advance the game logic.
     *
     * @return the latest {@link GameSnapshot}
     */
    GameSnapshot getLatestSnapshot();

    /**
     * Requests the {@link GameEngine} to pause the game.
     */
    void pause();

    /**
     * Requests the {@link GameEngine} to resume the game.
     */
    void resume();
}
