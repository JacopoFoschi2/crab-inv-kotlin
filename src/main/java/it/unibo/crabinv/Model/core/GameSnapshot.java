package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.save.GameSession;

import java.util.List;

public record GameSnapshot (
        List<RenderObjectSnapshot> renderObjects,
        GameSession session
){}
