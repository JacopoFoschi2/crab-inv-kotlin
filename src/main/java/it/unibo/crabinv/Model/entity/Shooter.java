package it.unibo.crabinv.Model.entity;

public interface Shooter {
    /**
     * Checks if the entity is currently able to shoot
     * @return true if it can shoot, false if it can't
     */
    public boolean isAbleToShoot();

    /**
     * Returns the fireRate of the entity
     * @return the amount of ticks needed between two shots
     */
    public int getFireRate();
}
