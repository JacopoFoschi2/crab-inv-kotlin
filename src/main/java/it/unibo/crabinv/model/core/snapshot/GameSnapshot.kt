package it.unibo.crabinv.model.core.snapshot

import it.unibo.crabinv.model.core.save.GameSession

/**
 * Contains the data produced by the [GameEngine] that needs to be exposed.
 * @param renderObjects the list of all game objects to be renderer
 * @param session the [GameSession] of the snapshot
 */
@JvmRecord
data class GameSnapshot(
    val renderObjects: MutableList<RenderObjectSnapshot>,
    val session: GameSession?,
)
