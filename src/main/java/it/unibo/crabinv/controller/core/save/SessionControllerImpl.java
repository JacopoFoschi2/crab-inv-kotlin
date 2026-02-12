package it.unibo.crabinv.controller.core.save;

import it.unibo.crabinv.model.core.save.GameSession;
import it.unibo.crabinv.model.core.save.PlayerMemorial;
import it.unibo.crabinv.model.core.save.Save;
import it.unibo.crabinv.model.core.save.SessionRecord;
import it.unibo.crabinv.model.core.save.SessionRecordImpl;

/**
 * Implementation of {@link SessionController}.
 *
 * @param save the {@link Save} used by the {@link SessionControllerImpl}
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
    public Save save() {
        return this.save;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSession getGameSession() {
        return this.save.getGameSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSession newGameSession() {
        this.save.closeGameSession();
        this.save.newGameSession();
        return this.save.getGameSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gameOverGameSession() {
        final GameSession gameSession = save.getGameSession();
        final PlayerMemorial playerMemorial = save.getPlayerMemorial();
        final SessionRecord sessionRecord = new SessionRecordImpl(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency(),
                gameSession.isGameWon());
        if (sessionRecord.isGameWon()) {
            final SessionRecord winSessionRecord = new SessionRecordImpl(
                    gameSession.getStartingTimeStamp(),
                    gameSession.getCurrentLevel() - 1,
                    gameSession.getCurrency(),
                    gameSession.isGameWon());
            playerMemorial.addMemorialRecord(winSessionRecord);
        } else {
            playerMemorial.addMemorialRecord(sessionRecord);
        }
        this.save.getUserProfile().addCurrency(gameSession.getCurrency());
        this.save.closeGameSession();
    }
}
