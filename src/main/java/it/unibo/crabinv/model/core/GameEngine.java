package it.unibo.crabinv.model.core;

import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.controller.core.collision.CollisionController;
import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService;
import it.unibo.crabinv.model.entities.player.Player;
import it.unibo.crabinv.model.levels.LevelFactory;
import it.unibo.crabinv.model.save.GameSession;

import java.util.List;

/**
 * Defines the contract for the game simulation
 */
public interface GameEngine {

    /**
     * Initializes the instance of the game
     *
     * @param gameSession    the {@link GameSession} from which the {@link GameEngine} will be initialized
     * @param levelFactory   the {@link LevelFactory} used by the {@link GameEngine}
     * @param enemyFactory   the {@link EnemyFactory} used by the {@link GameEngine}
     * @param rewardsService the {@link RewardsService} used by the {@link GameEngine}
     */
    void init(GameSession gameSession,
              LevelFactory levelFactory,
              EnemyFactory enemyFactory,
              RewardsService rewardsService,
              CollisionController collisionController);

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
     * Marks the current attempt as won
     */
    void winGame();

    /**
     * Pauses the game, blocks the game logic to the latest snapshot
     */
    void pauseGame();

    /**
     * Resumes the game, resumes the game logic
     */
    void resumeGame();

    /**
     * @return the {@link Player} in the game engine
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

    List<Enemy> getEnemyList();

    /**
     * Creates the bullets for the GameEngine
     */
    void spawnPlayerBullet();

    /**
     * Spawns the bullet for the enemies
     * @param enemy to know the position of it to make the bullet come from it
     */
    void spawnEnemyBullet(Enemy enemy);
}