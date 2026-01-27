package it.unibo.crabinv.Model.entity;

/**
 * provides the method that a movable entity should implement
 */
public interface Movable {
    /**
     * Handles unidimensional movement for every tick based on given delta
     * @param delta the delta of movement, which is either +1, 0 or -1, to be then applied to either the x or y axis
     * @param minBound the minimal bound that the entity cannot surpass of the wanted axis
     * @param maxBound the maximal bound that the entity cannot surpass of the wanted axis
     */
    void move(Delta delta, double minBound, double maxBound);
}
