package it.unibo.crabinv.Controller.enemy;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entity.EntityNotCapableOfInputController;
import it.unibo.crabinv.Model.Enemies.Enemy;
import it.unibo.crabinv.Model.Enemies.EnemyImpl;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Entity;

import java.util.Random;


public class EnemyController extends EntityAbstractController<EnemyImpl> implements EntityNotCapableOfInputController {
    private final double minBounds;
    private final double maxBounds;

    public EnemyController(EnemyImpl enemy, double minBounds, double maxBounds) {
        super(enemy);
        this.minBounds = minBounds;
        this.maxBounds = maxBounds;
    }

    /**
     * Updates the status of the enemy
     */
    @Override
    public void update(Delta delta) {
        tick();
        move(delta);

        Random rand = new Random();
        int n = rand.nextInt();
        if (n%2 == 1){
            shoot();
        }
    }

    /**
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return entity.getSpeed();
    }


    /**
     * Tells the enemy to go to a specific direction for 1 tick
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(Delta delta) {
        entity.move(delta, minBounds, maxBounds);
    }

    /**
     * Makes the enemy shoot if it can
     */
    private void shoot() {
        if (entity.isAbleToShoot()) {
            entity.shoot();
        }
    }

    /**
     * Updates the enemy status for the tick
     */
    private void tick() {
        entity.tick();
    }
}
