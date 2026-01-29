package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.input.*;

public interface InputMapper {

    CommandMovement mapToMovement (int inputCode);
    CommandAction mapToAction (int inputCode);
}