package it.unibo.crabinv.Controller.enemy;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Model.Enemies.EnemyImpl;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Entity;

import java.util.Random;


public class EnemyController extends EntityAbstractController {
    private final EnemyImpl enemy;
    private final double minBounds;
    private final double maxBounds;

    public EnemyController(EnemyImpl enemy, double minBounds, double maxBounds) {
        this.enemy = enemy;
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

    @Override
    public boolean isAlive() {
        return enemy.isAlive();
    }

    @Override
    public void onCollisionWith(Entity other) {
        enemy.onCollisionWith(other);
    }

    @Override
    public int getHealth() {
        return enemy.getHealth();
    }

    @Override
    public int getMaxHealth() {
        return enemy.getMaxHealth();
    }

    @Override
    public double getX() {
        return enemy.getX();
    }

    @Override
    public double getY() {
        return enemy.getY();
    }

    /**
     * @return the speed of the enemy
     */
    public double getSpeed() {
        return enemy.getSpeed();
    }


    /**
     * Tells the enemy to go to a specific direction for 1 tick
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(Delta delta) {
        enemy.move(delta, minBounds, maxBounds);
    }

    /**
     * Makes the enemy shoot if it can
     */
    private void shoot() {
        if (enemy.isAbleToShoot()) {
            enemy.shoot();
        }
    }

    /**
     * Updates the enemy status for the tick
     */
    private void tick() {
        enemy.tick();
    }
}
