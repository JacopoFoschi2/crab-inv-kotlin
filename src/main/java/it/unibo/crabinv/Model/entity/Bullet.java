package it.unibo.crabinv.Model.entity;

public class Bullet extends AbstractEntity implements Movable {

    /**
     * Creates the bullet by setting its basic parameters
     *
     * @param maxHealth
     * @param x
     * @param y
     */
    public Bullet(int maxHealth, double x, double y) {
        super(maxHealth, x, y);
    }

    @Override
    public void move(Delta delta, double minBound, double maxBound) {

    }
}
