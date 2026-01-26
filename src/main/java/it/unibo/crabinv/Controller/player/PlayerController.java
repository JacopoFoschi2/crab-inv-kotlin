package it.unibo.crabinv.Controller.player;

import it.unibo.crabinv.Model.player.Player;

public class PlayerController {
    Player player;
    double minBounds;
    double maxBounds;

    public PlayerController(Player player, double minBounds, double maxBounds) {
        this.player = player;
        this.minBounds = minBounds;
        this.maxBounds = maxBounds;
    }

    /**
     * Updates the status of the player, it needs to be called for every tick
     * @param firePressed tells the controller if the user requested to fire
     * @param dt either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    public void update(boolean firePressed, double dt) {
        move(dt);
        if (firePressed) {
            shoot();
        }
        tick();
    }

    /**
     * Tells the player to move in a certain direction for 1 tick
     * @param dt either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(double dt) {
        player.move(dt, minBounds, maxBounds);
    }

    /**
     * Tells the player to shoot if possible
     * TODO handle the bullet being created
     */
    private void shoot() {
        if (player.isAbleToShoot()) {
            player.shoot();
        }
    }

    /**
     * Updates the player status for the tick
     */
    private void tick() {
        player.tick();
    }
}
