package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Model.core.GameSnapshot;

/**
 * Controls the passing of time and creates the snapshot to be passed onto the renderer
 */
public interface GameLoopController {
    /**
     * @return the duration in milliseconds of a tick
     */
    long getTickDurationMillis();

    /**
     * @return the currently total accumulated milliseconds,
     * needed to calculate the next step
     */
    long getAccumulatedMillis();

    /**
     * @return the total of ticks since the start of the engine
     */
    long getTotalElapsedTicks();

    /**
     * Calculates the game engine next step snapshot to be passed onto the renderer.
     * Advances the logic of the game engine of the calculated step.
     * @param frameElapsedMillis the time accumulated between two frames
     * @return the calculated snapshot
     */
    GameSnapshot step(long frameElapsedMillis);

    /**
     * @return the latest snapshot to be rendered, without any step advancing
     */
    GameSnapshot getLatestSnapshot();

}
