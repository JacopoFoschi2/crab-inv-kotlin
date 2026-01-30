package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.input.*;

public interface InputMapper {

    Delta mapToXDelta(int inputCode);
    Delta mapToYDelta(int inputCode);
    boolean mapToShoot(int inputCode);

}