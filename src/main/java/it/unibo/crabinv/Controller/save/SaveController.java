package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.*;

public interface SaveController {

    int countExistingSaves();

    Save createSave();

    Save loadSave();

    void deleteSave();

    boolean gameSessionCheck();


}
