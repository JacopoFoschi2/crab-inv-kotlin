package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.input.CommandAction;
import it.unibo.crabinv.Model.input.CommandMovement;
import it.unibo.crabinv.Model.input.InputSnapshot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputControllerPlayer implements InputController, KeyListener {

    private final InputMapper mapper;

    // volatile per permettere aggiornamenti continui
    private volatile CommandMovement movement = CommandMovement.IDLE;
    private volatile CommandAction action = CommandAction.NO_ACTION;

    public InputControllerPlayer (InputMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public InputSnapshot getInputState() {
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        CommandMovement commandMovement = mapper.mapToMovement(e.getKeyCode());
        CommandAction commandAction = mapper.mapToAction(e.getKeyCode());

        if (commandMovement != CommandMovement.IDLE){this.movement = commandMovement;};
        if (commandAction != CommandAction.NO_ACTION){this.action = commandAction;};

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        CommandMovement commandMovement = mapper.mapToMovement(e.getKeyCode());
        CommandAction commandAction = mapper.mapToAction(e.getKeyCode());

    }
}
