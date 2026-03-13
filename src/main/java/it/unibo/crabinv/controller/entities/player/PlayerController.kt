package it.unibo.crabinv.controller.entities.player;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.entities.entity.AbstractEntityController;
import it.unibo.crabinv.controller.entities.entity.EntityCapableOfInputController;
import it.unibo.crabinv.model.core.engine.GameEngine;
import it.unibo.crabinv.model.core.audio.SFXTracks;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.player.Player;

/**
 * Provides all the apis to control a {@link Player}.
 */
public class PlayerController extends AbstractEntityController<Player> implements EntityCapableOfInputController {
    private final AudioController audio;
    private final GameEngine engine;

    /**
     * Sets the controller linking it to a set player.
     *
     * @param player the player
     * @param audio the audioController used to play sounds
     * @param engine the engine used to spawn bullets
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public PlayerController(final Player player, final AudioController audio, final GameEngine engine) {
        super(player);
        this.audio = audio;
        this.engine = engine;
    }

    /**
     * {@inheritDoc}
     */
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
