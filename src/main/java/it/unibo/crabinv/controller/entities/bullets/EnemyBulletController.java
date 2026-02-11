package it.unibo.crabinv.controller.entities.bullets;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.controller.entities.entity.AbstractEntityController;
import it.unibo.crabinv.model.entities.bullets.BulletEnemy;
import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * It's the EnemyBulletController and it handles the enemyBullets.
 */
public final class EnemyBulletController extends AbstractEntityController<BulletEnemy> implements BulletController {

    /**
     * It's the constructor of the EnemyBulletController.
     *
     * @param entity it's the entity needed to modify it
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public EnemyBulletController(final BulletEnemy entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     *
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
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
