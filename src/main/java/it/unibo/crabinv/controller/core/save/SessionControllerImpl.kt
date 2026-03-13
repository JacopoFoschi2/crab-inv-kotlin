package it.unibo.crabinv.controller.core.save

import it.unibo.crabinv.model.core.save.GameSession
import it.unibo.crabinv.model.core.save.Save
import it.unibo.crabinv.model.core.save.SessionRecord
import it.unibo.crabinv.model.core.save.SessionRecordImpl

/**
 * Implementation of [SessionController].
 * @param save the [Save] used by the [SessionControllerImpl]
 */
@JvmRecord
data class SessionControllerImpl(
    override val save: Save,
) : SessionController {
    override val gameSession: GameSession
        get() = this.save.getGameSession()

    override fun newGameSession(): GameSession {
        this.save.closeGameSession()
        this.save.newGameSession()
        return this.save.getGameSession()
    }

    override fun gameOverGameSession() {
        val gameSession = save.getGameSession()
        val playerMemorial = save.getPlayerMemorial()
        val sessionRecord: SessionRecord =
            SessionRecordImpl(
                gameSession.getStartingTimeStamp(),
                gameSession.getCurrentLevel(),
                gameSession.getCurrency(),
                gameSession.isGameWon(),
            )
        if (sessionRecord.isGameWon()) {
            val winSessionRecord: SessionRecord =
                SessionRecordImpl(
                    gameSession.getStartingTimeStamp(),
                    gameSession.getCurrentLevel() - 1,
                    gameSession.getCurrency(),
                    gameSession.isGameWon(),
                )
            playerMemorial.addMemorialRecord(winSessionRecord)
        } else {
            playerMemorial.addMemorialRecord(sessionRecord)
        }
        this.save.getUserProfile().addCurrency(gameSession.getCurrency())
        this.save.closeGameSession()
    }
}
