package it.unibo.crabinv.Model.player;

import it.unibo.crabinv.Model.Enemies.WaveProvider;
import it.unibo.crabinv.Model.PowerUpsShop.PowerUp;
import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Movable;
import it.unibo.crabinv.Model.entity.Shooter;
import it.unibo.crabinv.Model.save.UserProfile;

/**
 * Provides the implementation of what a player is
 */
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

    /**
     * Creates a player
     * @param speed the space covered in one tick
     * @param fireRate the minimal amount of ticks needed between two shots
     */
    public Player(int maxHealth, double x, double y, double speed, int fireRate) {
        super(maxHealth, x, y);
        this.speed = speed;
        this.fireRate = fireRate;
        this.shootingCounter = 0;

        // applyPowerUps(profile);
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {
        double movement = delta.getValue() * speed;
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
