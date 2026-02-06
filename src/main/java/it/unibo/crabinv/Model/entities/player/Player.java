package it.unibo.crabinv.Model.entities.player;

import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.entity.AbstractEntity;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.entity.Movable;
import it.unibo.crabinv.Model.entities.entity.Shooter;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

/**
 * Provides the implementation of what a player is
 */
@SuperBuilder
public class Player extends AbstractEntity implements Shooter, Movable {
    /**
     * The speed at witch the player moves in a tick
     */
    private final double speed;
    /**
     * Ticks counter for the next shot
     */
    @Builder.Default
    private int shootingCounter = 0;
    /**
     * The minimal amount of ticks needed between two shots
     */
    private final int fireRate;
    private final double minBound;
    private final double maxBound;

    /**
     * Creates a player
     * @param speed the space covered in one tick
     * @param fireRate the minimal amount of ticks needed between two shots
     */
    public Player(double x, double y, int maxHealth, double radius, double speed, int fireRate, double minBound, double maxBound) {
        super(x, y, maxHealth, CollisionGroups.FRIENDLY, radius);
        this.speed = speed;
        this.fireRate = fireRate;
        this.shootingCounter = 0;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    @Override
    public void move(Delta delta) {
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
    public double getSpeed() {
        return speed;
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

    /**
     * @return the path of the sprite
     */
    public String getImagePath() {
        return "/player/player_appearance.png";
    }

}
