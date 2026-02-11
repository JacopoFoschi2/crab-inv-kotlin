package it.unibo.crabinv.model.core.save;

/**
 * Record-Class Implementation of {@link SessionRecord}.
 *
 * @param timeStamp    the starting timeStamp of the {@link GameSession}
 * @param lastLevel    currentLevel at the moment of the record creation
 * @param lastCurrency the currency at the moment of the record creation
 * @param gameWon true if the game has been won
 */
public record SessionRecordImpl(
        long timeStamp,
        int lastLevel,
        int lastCurrency,
        boolean gameWon
) implements SessionRecord {
    /**
     * Constructor for {@link SessionRecordImpl} from a {@code gameOver=true} {@link GameSessionImpl}.
     *
     * @param gameSession the {@link GameSessionImpl} to record
     */
    public SessionRecordImpl(final GameSessionImpl gameSession) {
        this(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency(),
                gameSession.isGameWon()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getStartingTimeStamp() {
        return this.timeStamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastLevel() {
        return this.lastLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastCurrency() {
        return this.lastCurrency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameWon() {
        return this.gameWon;
    }
}
