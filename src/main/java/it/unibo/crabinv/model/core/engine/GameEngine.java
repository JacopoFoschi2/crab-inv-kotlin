package it.unibo.crabinv.model.core.engine;

import it.unibo.crabinv.controller.core.collision.CollisionController;
import it.unibo.crabinv.model.core.snapshot.GameSnapshot;
import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService;
import it.unibo.crabinv.model.entities.player.Player;
import it.unibo.crabinv.model.levels.LevelFactory;
import it.unibo.crabinv.model.core.save.GameSession;

import java.util.List;

/**
 * Defines the contract for the game simulation.
 */
public interface GameEngine {

    /**
     * Initializes the instance of the game.
     *
     * @param gameSession         the {@link GameSession} from which the {@link GameEngine} will be initialized
     * @param levelFactory        the {@link LevelFactory} used by the {@link GameEngine}
     * @param enemyFactory        the {@link EnemyFactory} used by the {@link GameEngine}
     * @param rewardsService      the {@link RewardsService} used by the {@link GameEngine}
     * @param collisionController the {@link CollisionController} used by the {@link GameEngine}
     */
    void init(GameSession gameSession,
              LevelFactory levelFactory,
              EnemyFactory enemyFactory,
              RewardsService rewardsService,
              CollisionController collisionController);

    /**
     * Defines the logic of the simulation and advances one tick.
     */
    void tick();

    /**
     * Returns the {@link GameSnapshot} of the current state.
     *
     * @return the {@link GameSnapshot} of the current state
     */
    GameSnapshot snapshot();

    /**
     * Returns the current GameEngineState.
     *
     * @return the current GameEngineState
     */
    GameEngineState getGameState();

    /**
     * Marks the current attempt as game over.
     */
    void gameOver();

    /**
     * Marks the current attempt as won.
     */
    void winGame();

    /**
     * Pauses the game, blocks the game logic to the latest snapshot.
     */
    void pauseGame();

    /**
     * Resumes the game, resumes the game logic.
     */
    void resumeGame();

    /**
     * Returns the {@link Player} of the {@link GameEngine}.
     *
     * @return the {@link Player} in the {@link GameEngine}
     */
    Player getPlayer();

    /**
     * Return the min coordinates of the world.
     *
     * @return the min coordinates of the world
     */
    double getWorldMinX();

    /**
     * Returns the max coordinates of the world.
     *
     * @return the max coordinates of the world
     */
    double getWorldMaxX();

    /**
     * Returns the {@link List} of {@link Enemy} in the {@link GameEngine}.
     *
     * @return the {@link List<Enemy>} in the {@link GameEngine}
     */
    List<Enemy> getEnemyList();

    /**
     * Creates the bullets for the GameEngine.
     */
    void spawnPlayerBullet();

    /**
     * Spawns the bullet for the enemies.
     *
     * @param enemy to know the position of it to make the bullet come from it
     */
    void spawnEnemyBullet(Enemy enemy);
}
