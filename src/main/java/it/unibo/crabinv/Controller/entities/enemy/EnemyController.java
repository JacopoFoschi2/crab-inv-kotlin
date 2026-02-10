package it.unibo.crabinv.Controller.entities.enemy;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entities.entity.EntityNotCapableOfInputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.entity.Delta;

import java.util.Random;


public class EnemyController extends EntityAbstractController<Enemy> implements EntityNotCapableOfInputController {
    private final AudioController audio;
    private final GameEngine engine;
    private final Random rand = new Random();
    private final double ENEMY_SHOOTING_CHANCE = 0.007;

    public EnemyController(Enemy enemy, AudioController audio, GameEngine engine) {
        super(enemy);
        this.audio = audio;
        this.engine = engine;
    }

    /**
     * Updates the status of the enemy
     */
    @Override
    public void update(Delta delta) {
        tick();
        move(delta);

        if (rand.nextDouble() < ENEMY_SHOOTING_CHANCE) {
            shoot();
        }
    }

    /**
     * Gives the speed of the enemy
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return super.getEntity().getSpeed();
    }


    /**
     * Tells the enemy to go to a specific direction for 1 tick
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(Delta delta) {
        super.getEntity().move(delta);
    }

    /**
     * Makes the enemy shoot if it can
     */
    private void shoot() {
        if (super.getEntity().isAbleToShoot()) {
            super.getEntity().shoot();
            engine.spawnEnemyBullet(super.getEntity());
            audio.playSFX(SFXTracks.SHOT_ENEMY);
        }
    }

    /**
     * Updates the status for the tick
     */
    private void tick() {
        super.getEntity().tick();
    }
}
