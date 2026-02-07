package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entities.entity.Delta;

public interface InputMapper {

    Delta mapToXDelta(int inputCode);
    Delta mapToYDelta(int inputCode);
    boolean mapToShoot(int inputCode);
    boolean mapToPause (int inputCode);
    boolean mapToUnPause (int inputCode);
}