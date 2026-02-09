package it.unibo.crabinv.Model.core;

/**
 * Contains the data needed by the renderer to rendere a single game object
 * @param imagePath the sprite to render
 * @param x the X-axis position of the sprite
 * @param y the Y-axis position of the sprite
 */
public record RenderObjectSnapshot(
        String imagePath,
        double x,
        double y
) {}
