package it.unibo.crabinv.Controller.entities.enemy;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entities.entity.EntityNotCapableOfInputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.entity.Delta;

import java.util.Random;

/**
 * It's the EnemyController, should control each enemy.
 */
public final class EnemyController extends EntityAbstractController<Enemy> implements EntityNotCapableOfInputController {
    private static final double ENEMY_SHOOTING_CHANCE = 0.007;
    private final AudioController audio;
    private final GameEngine engine;
    private final Random rand = new Random();

    /**
     * It's the enemyController, it needs the enemy, audio, and game engine to work.
     *
     * @param enemy the enemy to be created.
     * @param audio the audio that it's needed in the class.
     * @param engine the game engine.
     */
    public EnemyController(final Enemy enemy,
                           final AudioController audio,
                           final GameEngine engine) {
        super(enemy);
        this.audio = audio;
        this.engine = engine;
    }

    /**
     * Updates the status of the enemy.
     */
    @Override
    public void update(final Delta delta) {
        tick();
        move(delta);

        if (rand.nextDouble() < ENEMY_SHOOTING_CHANCE) {
            shoot();
        }
    }

    /**
     * Gives the speed of the enemy.
     *
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return super.getEntity().getSpeed();
    }

    /**
     * Tells the enemy to go to a specific direction for 1 tick.
     *
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(final Delta delta) {
        super.getEntity().move(delta);
    }

    /**
     * Makes the enemy shoot if it can.
     */
    private void shoot() {
        if (super.getEntity().isAbleToShoot()) {
            super.getEntity().shoot();
            engine.spawnEnemyBullet(super.getEntity());
            audio.playSFX(SFXTracks.SHOT_ENEMY);
        }
    }

    /**
     * Updates the status for the tick.
     */
    private void tick() {
        super.getEntity().tick();
    }
}
