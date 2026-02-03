package it.unibo.crabinv.Controller.player;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entity.EntityCapableOfInputController;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.player.Player;

/**
 * Provides all the apis to control a {@link Player}
 */
public class PlayerController extends EntityAbstractController<Player> implements EntityCapableOfInputController {
    private final double minBounds;
    private final double maxBounds;

    /**
     * Sets the controller linking it to a set player
     * @param player the player
     * @param minBounds the minimal bounds of the x-axis
     * @param maxBounds the maximal bounds of the x-axis
     */
    public PlayerController(Player player, double minBounds, double maxBounds) {
        super(player);
        this.minBounds = minBounds;
        this.maxBounds = maxBounds;
    }

    @Override
    public void update(boolean firePressed, Delta delta) {
        tick();
        move(delta);
        if (firePressed) {
            shoot();
        }
    }

    /**
     * @return the speed of the player
     */
    public double getSpeed() {
        return entity.getSpeed();
    }

    /**
     * Tells the player to move in a certain direction for 1 tick
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(Delta delta) {
        entity.move(delta, minBounds, maxBounds);
    }

    /**
     * Tells the player to shoot if possible
     * TODO handle the bullet being created
     */
    private void shoot() {
        if (entity.isAbleToShoot()) {
            entity.shoot();
        }
    }

    /**
     * Updates the player status for the tick
     */
    private void tick() {
        entity.tick();
    }
}
