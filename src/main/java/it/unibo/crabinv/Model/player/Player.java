package it.unibo.crabinv.Model.player;

import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Movable;
import it.unibo.crabinv.Model.entity.Shooter;

public class Player extends AbstractEntity implements Shooter, Movable {
    /**
     * The speed at witch the player moves in a tick
     */
    private final double speed;
    /**
     * Ticks counter for the next shot
     */
    private int shootingCounter;
    /**
     * The minimal amount of ticks needed between two shots
     */
    private final int fireRate;


    public Player(int maxHealth, double x, double y, double speed, int fireRate) {
        super(maxHealth, x, y);
        this.speed = speed;
        this.fireRate = fireRate;
        this.shootingCounter = 0;
    }

    @Override
    public void move(double dt, double minBound, double maxBound) {
        double movement = dt * speed;
        double newX = this.getX() + movement;
        if (newX <  minBound) {
            newX = minBound;
        } else if (newX > maxBound) {
            newX = maxBound;
        }
        this.setPosition(newX, this.getY());
    }

    @Override
    public boolean isAbleToShoot() {
        return shootingCounter == 0;
    }

    @Override
    public int getFireRate() {
        return fireRate;
    }

    @Override
    public void shoot() {
        shootingCounter = fireRate;
    }

    /**
     * Updates the state of the internal counters of Player
     */
    public void tick() {
        if (shootingCounter > 0) {
            shootingCounter--;
        }
    }
}
