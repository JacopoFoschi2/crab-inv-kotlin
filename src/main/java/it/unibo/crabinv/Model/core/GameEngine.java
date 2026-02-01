package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.input.InputSnapshot;

/**
 * Defines the contract for the game simulation
 */
public interface GameEngine {

    /**
     * Creates a new instance of the game
     */
    void newGame();

    /**
     * Defines the logic of the simulation and advances one tick
     * @param inputSnapshot the state of the calculated tick
     */
    void tick (InputSnapshot inputSnapshot);

    /**
     * @return the snapshot of the current state
     */
    GameSnapshot snapshot();

    /**
     * Defines the logic of the game when the game ends
     */
    void gameOver();


}