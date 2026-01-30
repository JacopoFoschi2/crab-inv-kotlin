package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.input.InputSnapshot;

public interface GameEngine {
    void update (long elapsedMillis, InputSnapshot inputSnapshot);
    GameSnapshot getGameSnapshot();
}