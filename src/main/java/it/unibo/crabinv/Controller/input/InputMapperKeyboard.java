package it.unibo.crabinv.Controller.input;

import it.unibo.crabinv.Model.input.CommandAction;
import it.unibo.crabinv.Model.input.CommandMovement;

import java.awt.event.KeyEvent;
import java.util.Map;

import static java.util.Map.entry;

public class InputMapperKeyboard implements InputMapper {

    private final Map<Integer, CommandMovement> movementMap;
    private final Map<Integer, CommandAction> actionMap;

    public InputMapperKeyboard(){
        this.movementMap = Map.ofEntries(
                entry(KeyEvent.VK_W, CommandMovement.MOVE_UP),
                entry(KeyEvent.VK_UP, CommandMovement.MOVE_UP),
                entry(KeyEvent.VK_S, CommandMovement.MOVE_DOWN), // Correggi MODE_DOWN nel tuo Enum
                entry(KeyEvent.VK_DOWN, CommandMovement.MOVE_DOWN),
                entry(KeyEvent.VK_A, CommandMovement.MOVE_LEFT),
                entry(KeyEvent.VK_LEFT, CommandMovement.MOVE_LEFT),
                entry(KeyEvent.VK_D, CommandMovement.MOVE_RIGHT),
                entry(KeyEvent.VK_RIGHT, CommandMovement.MOVE_RIGHT)
        );

        this.actionMap = Map.ofEntries(
                entry(KeyEvent.VK_SPACE, CommandAction.SHOOT)
        );

    }

    @Override
    public CommandMovement mapToMovement(int inputCode) {
        return movementMap.getOrDefault(inputCode, CommandMovement.IDLE);
    }

    @Override
    public CommandAction mapToAction(int inputCode) {
        return actionMap.getOrDefault(inputCode, CommandAction.NO_ACTION);
    }
}
