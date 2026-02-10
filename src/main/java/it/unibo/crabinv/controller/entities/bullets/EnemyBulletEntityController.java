package it.unibo.crabinv.controller.entities.bullets;

import it.unibo.crabinv.controller.entities.entity.AbstractEntityController;
import it.unibo.crabinv.model.entities.bullets.BulletEnemy;
import it.unibo.crabinv.model.entities.entity.Delta;

/**
 * It's the EnemyBulletEntityController and it handles the enemyBullets.
 */
public final class EnemyBulletEntityController extends AbstractEntityController<BulletEnemy> implements BulletController {

    /**
     * It's the constructor of the EnemyBulletEntityController.
     *
     * @param entity it's the entity needed to modify it
     */
    public EnemyBulletEntityController(final BulletEnemy entity) {
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
