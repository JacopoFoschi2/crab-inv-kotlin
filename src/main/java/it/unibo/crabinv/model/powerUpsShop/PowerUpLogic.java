package it.unibo.crabinv.model.powerUpsShop;

/**
 * It's the logic of the power up.
 */
public final class PowerUpLogic implements PowerUp {
    private final PowerUpType type;
    private final int cost;
    private final int maxLevel;

    /**
     * It's the constructor of the powerUpsLogic.
     *
     * @param type the type of power Up
     * @param cost the cost of it
     * @param maxLevel the maxLevel of it
     */
    public PowerUpLogic(final PowerUpType type, final int cost, final int maxLevel) {
        this.type = type;
        this.cost = cost;
        this.maxLevel = maxLevel;
    }

    /**
     * {{@inheritDoc}}.
     *
     * @return gives the cost of the power up
     */
    @Override
    public int getCost() {
        return this.cost;
    }

    /**
     * {@inheritDoc}
     *
     * @return the max level of the power up
     */
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * {@inheritDoc}
     *
     * @return the name of the power up
     */
    @Override
    public String getPowerUpName() {
        return this.type.name();
    }

    /**
     * {@inheritDoc}
     *
     * @return the {@link PowerUpType}
     */
    @Override
    public PowerUpType getPowerUpType() {
        return type;
    }
}
