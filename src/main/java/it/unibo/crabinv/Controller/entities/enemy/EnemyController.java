package it.unibo.crabinv.Controller.entities.enemy;

import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entities.entity.EntityNotCapableOfInputController;
import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.entity.Delta;

import java.util.Random;


public class EnemyController extends EntityAbstractController<Enemy> implements EntityNotCapableOfInputController {
    private final double minBounds;
    private final double maxBounds;

    public EnemyController(Enemy enemy, double minBounds, double maxBounds) {
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
        super.getEntity().move(delta, minBounds, maxBounds);
    }

    /**
     * Makes the enemy shoot if it can
     */
    private void shoot() {
        if (super.getEntity().isAbleToShoot()) {
            super.getEntity().shoot();
        }
    }

    /**
     * Updates the status for the tick
     */
    private void tick() {
        super.getEntity().tick();
    }
}
