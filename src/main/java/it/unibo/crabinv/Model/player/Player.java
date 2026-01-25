package it.unibo.crabinv.Model.player;

import it.unibo.crabinv.Model.entity.AbstractEntity;
import it.unibo.crabinv.Model.entity.Movable;
import it.unibo.crabinv.Model.entity.Shooter;

public class Player extends AbstractEntity implements Shooter, Movable {

    public Player(int maxHealth, double x, double y) {
        super(maxHealth, x, y);
    }

    @Override
    public void move() {

    }

    @Override
    public boolean isAbleToShoot() {
        return false;
    }

    @Override
    public int getFireRate() {
        return 0;
    }
}
