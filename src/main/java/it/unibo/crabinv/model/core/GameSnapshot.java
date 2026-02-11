package it.unibo.crabinv.model.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.model.save.GameSession;

import java.util.List;

/**
 * Contains the data produced by the {@link GameEngine} that needs to be exposed.
 *
 * @param renderObjects the list of all game objects to be renderer
 * @param session the {@link GameSession} of the snapshot
 */
@SuppressFBWarnings({"EI_EXPOSE_REP2", "EI_EXPOSE"})
//dependencies are injected and owned by caller,
//exposes internal representation by design
public record GameSnapshot(
        List<RenderObjectSnapshot> renderObjects,
        GameSession session) {
}
