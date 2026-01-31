package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.input.InputSnapshot;

/**
 * Defines the contract for the game simulation
 */
public interface GameEngine {

    /**
     * Defines the logic of the simulation and advances one tick
     * @param inputSnapshot the state of the calculated tick
     */
    void tick (InputSnapshot inputSnapshot);

    /**
     * @return the snapshot of the current state
     */
    GameSnapshot snapshot();

    void newGame();

}