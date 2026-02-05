package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.entities.player.Player;

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
     */
    void tick ();

    /**
     * @return the snapshot of the current state
     */
    GameSnapshot snapshot();

    /**
     * @return the current GameEngineState
     */
    GameEngineState getGameState();

    /**
     * Marks the current attempt as game over
     */
    void gameOver();

    /**
     * Pauses the game, blocks the gamelogic to the latest snapshot
     */
    void pauseGame();

    /**
     * Resumes the game, resumes the tick logic
     */
    void resumeGame();

    /**
     * @return the Player in the game engine
     */
    Player getPlayer();

    /**
     * @return the min coordinates of the world
     */
    double getWorldMinX();

    /**
     * @return the max coordinates of the world
     */
    double getWorldMaxX();
}