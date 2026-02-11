package it.unibo.crabinv.controller.save;

import it.unibo.crabinv.model.save.GameSession;
import it.unibo.crabinv.model.save.PlayerMemorial;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.model.save.SessionRecord;
import it.unibo.crabinv.model.save.SessionRecordImpl;

/**
 * Implementation of {@link SessionController}
 */
public record SessionControllerImpl(Save save) implements SessionController {

    /**
     * Constructor for the {@link SessionControllerImpl}.
     *
     * @param save the saved managed by the {@link SessionControllerImpl}
     */
    public SessionControllerImpl {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Save save() {
        return this.save;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameSession getGameSession() {
        return this.save.getGameSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameSession newGameSession() {
        this.save.closeGameSession();
        this.save.newGameSession();
        return this.save.getGameSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void gameOverGameSession() {
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
