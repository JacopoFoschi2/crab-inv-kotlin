package it.unibo.crabinv.Controller.save;

import it.unibo.crabinv.Model.save.*;

public class SessionControllerImpl implements SessionController {

    private final Save save;

    public SessionControllerImpl(Save save) {
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
    public GameSession newGameSession() {
        this.save.closeGameSession();
        this.save.newGameSession();
        return this.save.getGameSession();
    }

    @Override
    public void gameOverGameSession() {
        GameSession gameSession = save.getGameSession();
        PlayerMemorial playerMemorial = save.getPlayerMemorial();
        SessionRecord sessionRecord = new SessionRecordImpl(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency(),
                gameSession.isGameWon());
        playerMemorial.addMemorialRecord(sessionRecord);
        this.save.getUserProfile().addCurrency(gameSession.getCurrency());
        this.save.closeGameSession();
    }
}
