package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.input.InputSnapshot;

public interface InputController {

    void onKeyPressed(int keyCode);

    void onKeyReleased(int keyCode);

    InputSnapshot getInputState();
}
