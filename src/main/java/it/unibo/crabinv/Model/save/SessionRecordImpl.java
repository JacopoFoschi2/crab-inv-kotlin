package it.unibo.crabinv.Model.save;

/**
 * Record-Class Implementation of SessionRecord
 *
 * @param timeStamp    the starting timeStamp of the gameSession
 * @param lastLevel    currentLevel at the moment of the record
 * @param lastCurrency the currency at the moment of the record
 */

public record SessionRecordImpl(
        long timeStamp,
        int lastLevel,
        int lastCurrency,
        boolean gameWon
) implements SessionRecord {
    /**
     * Convenience constructor for SessionRecordImpl from a gameOver=true GameSessionImpl
     * @param gameSession the session to record
     */
    public SessionRecordImpl(GameSessionImpl gameSession) {
        this(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency(),
                gameSession.isGameWon()
        );
    }

    @Override
    public long getStartingTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public int getLastLevel() {
        return this.lastLevel;
    }

    @Override
    public int getLastCurrency() {
        return this.lastCurrency;
    }

    @Override
    public boolean getWonGame() {
        return this.gameWon;
    }
}
