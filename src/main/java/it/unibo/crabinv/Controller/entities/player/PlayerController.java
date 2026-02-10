package it.unibo.crabinv.Controller.entities.player;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Controller.entities.entity.EntityCapableOfInputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.audio.SFXTracks;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.player.Player;

/**
 * Provides all the apis to control a {@link Player}.
 */
public class PlayerController extends EntityAbstractController<Player> implements EntityCapableOfInputController {
    private final AudioController audio;
    private final GameEngine engine;
    /**
     * Sets the controller linking it to a set player.
     *
     * @param player the player
     * @param audio the audioController used to play sounds
     * @param engine the engine used to spawn bullets
     */
    public PlayerController(final Player player, final AudioController audio, final GameEngine engine) {
        super(player);
        this.audio = audio;
        this.engine = engine;
    }

    @Override
    public void update(final boolean firePressed, final Delta delta) {
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
        return super.getEntity().getSpeed();
    }

    /**
     * Tells the player to move in a certain direction for 1 tick.
     *
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(final Delta delta) {
        super.getEntity().move(delta);
    }

    /**
     * Tells the player to shoot if possible.
     */
    private void shoot() {
        if (super.getEntity().isAbleToShoot()) {
            super.getEntity().shoot();
            engine.spawnPlayerBullet();
            audio.playSFX(SFXTracks.SHOT_PLAYER);
        }
    }

    /**
     * Updates the player status for the tick.
     */
    private void tick() {
        super.getEntity().tick();
    }
}
