package it.unibo.crabinv.Model.entity;

public interface Movable {
    /**
     * Handles movement for every tick based on given delta
     * @param dt the delta of movement, which is either +1, 0 or -1
     * @param minBound the minimal bound that the entity cannot surpass
     * @param maxBound the maximal bound that the entity cannot surpass
     */
    void move(double dt, double minBound, double maxBound);
}
