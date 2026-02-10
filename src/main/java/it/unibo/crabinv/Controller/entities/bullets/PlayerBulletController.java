package it.unibo.crabinv.Controller.entities.bullets;

import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Model.entities.bullets.BulletPlayer;
import it.unibo.crabinv.Model.entities.entity.Delta;

/**
 * It's the PlayerBulletController that handles the PlayerBullets.
 */
public class PlayerBulletController extends EntityAbstractController<BulletPlayer> implements BulletController {

    /**
     * It's the constructor of the EnemyBulletController.
     *
     * @param entity it's the entity needed to modify it
     */
    public PlayerBulletController(final BulletPlayer entity) {
        super(entity);
    }

    @Override
    public void update(final Delta delta) {
        move(delta);
    }

    /**
     * Gives the speed of the bullet.
     *
     * @return the speed of the bullet
     */
    public double getSpeed() {
        return super.getEntity().getSpeed();
    }

    /**
     * Tells the bullet to go to a specific direction for 1 tick.
     *
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(final Delta delta) {
        super.getEntity().move(delta);
    }

}
