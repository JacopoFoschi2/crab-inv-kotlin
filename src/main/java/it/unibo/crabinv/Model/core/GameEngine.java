package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.player.Player;
import it.unibo.crabinv.Model.levels.LevelFactory;
import it.unibo.crabinv.Model.save.GameSession;

/**
 * Defines the contract for the game simulation
 */
public interface GameEngine {

    /**
     * Initializes the instance of the game
     *
     * @param gameSession the GameSession from which the GameEngine will be initialized
     */
    void init(GameSession gameSession, LevelFactory levelFactory, EnemyFactory enemyFactory, RewardsService rewardsService);

    /**
     * Defines the logic of the simulation and advances one tick
     */
    void tick();

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