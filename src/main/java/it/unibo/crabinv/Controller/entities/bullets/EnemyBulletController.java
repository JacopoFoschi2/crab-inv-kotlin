package it.unibo.crabinv.Controller.entities.bullets;

import it.unibo.crabinv.Controller.entities.entity.EntityAbstractController;
import it.unibo.crabinv.Model.entities.bullets.BulletEnemy;
import it.unibo.crabinv.Model.entities.entity.Delta;

public class EnemyBulletController extends EntityAbstractController<BulletEnemy> implements BulletController {

    public EnemyBulletController( BulletEnemy entity) {
        super(entity);
    }

    @Override
    public void update(Delta delta) {
        move(delta);
    }

    /**
     * Gives the speed of the bullet
     * @return the speed of the bullet
     */
    public double getSpeed() {
        return super.getEntity().getSpeed();
    }

    /**
     * Tells the bullet to go to a specific direction for 1 tick
     * @param delta either -1, 0 or 1, the former moves to the left, the latter moves to the right
     */
    private void move(Delta delta) {
        super.getEntity().move(delta);
    }


}
