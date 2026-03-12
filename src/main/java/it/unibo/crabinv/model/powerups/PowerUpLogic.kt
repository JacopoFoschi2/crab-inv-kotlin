package it.unibo.crabinv.model.powerups;

/**
 * It's the implementation of the powerUp.
 */
public final class PowerUpLogic implements PowerUp {
    private final PowerUpType type;
    private final int cost;
    private final int maxLevel;

    /**
     * It's the constructor of the power Up.
     *
     * @param type the type of powerUp
     * @param cost the cost of the powerUp
     * @param maxLevel the maximum level of the powerUp
     */
    public PowerUpLogic(final PowerUpType type, final int cost, final int maxLevel) {
        this.type = type;
        this.cost = cost;
        this.maxLevel = maxLevel;
    }

    /**
     * {@inheritDoc}
     *
     * @return the cost of the powerUp
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /**
     * {@inheritDoc}
     *
     * @return the max level of the powerUp
     */
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * {@inheritDoc}
     *
     * @return the name of the powerUp
     */
    @Override
    public String getPowerUpName() {
        return this.type.name();
    }

    /**
     * {@inheritDoc}
     *
     * @return the powerUpType
     */
    @Override
    public PowerUpType getPowerUpType() {
        return type;
    }
}
