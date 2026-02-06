package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.*;

public class SessionControllerImpl implements SessionController {

    private Save save;

    public void SessionController(Save save) {
        this.save = save;
    }

    @Override
    public Save getSave() {
        return this.save;
    }

    @Override
    public GameSession getGameSession() {
        return this.save.getGameSession();
    }

    @Override
    public GameSession newOrLoadGameSession() {
        return this.save.getGameSession() != null ? getGameSession() : this.save.newGameSession();
    }

    @Override
    public void gameOverGameSession() {
        GameSession gameSession = save.getGameSession();
        PlayerMemorial playerMemorial = save.getPlayerMemorial();
        SessionRecord sessionRecord = new SessionRecordImpl(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency());
        playerMemorial.addMemorialRecord(sessionRecord);
        this.save.closeGameSession();
    }

}
