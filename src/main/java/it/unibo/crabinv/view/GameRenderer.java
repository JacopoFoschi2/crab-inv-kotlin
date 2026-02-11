package it.unibo.crabinv.view;

import it.unibo.crabinv.model.core.snapshot.GameSnapshot;
import it.unibo.crabinv.model.core.snapshot.RenderObjectSnapshot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Renderer to be shown in the {@link GameScreen}.
 *
 * <p>Initial generation with AI, adapted to work correctly
 */
public final class GameRenderer {

    private static final double SPRITE_MULT = 0.08;

    private final GraphicsContext gc;
    private final Map<String, Image> imageCache = new HashMap<>();

    /**
     * Constructor of {@link GameRenderer}.
     *
     * @param gc the {@link GraphicsContext} used by the {@link GameRenderer}
     */
    public GameRenderer(final GraphicsContext gc) {
        this.gc = Objects.requireNonNull(gc, "gc must not be null");
    }

    /**
     * Renderer based on the {@link GameSnapshot} received.
     *
     * @param snapshot received from the game and used to render
     */
    public void render(final GameSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot must not be null");
        final double canvasW = gc.getCanvas().getWidth();
        final double canvasH = gc.getCanvas().getHeight();
        final double worldSquareSideDimension = Math.min(canvasW, canvasH);
        final double offsetX = (canvasW - worldSquareSideDimension) / 2.0;
        final double offsetY = (canvasH - worldSquareSideDimension) / 2.0;
        clear(canvasW, canvasH);
        gc.setStroke(Color.DARKGRAY);
        gc.strokeRect(offsetX, offsetY, worldSquareSideDimension, worldSquareSideDimension);
        for (final RenderObjectSnapshot obj : snapshot.renderObjects()) {
            final double cx = offsetX + toPixels(obj.x(), worldSquareSideDimension);
            final double cy = offsetY + toPixels(obj.y(), worldSquareSideDimension);
            drawSpriteCentered(obj.imagePath(), cx, cy, worldSquareSideDimension);
        }
    }

    private double toPixels(final double coordNorm, final double worldSquareSideDimension) {
        final double clamped = Math.max(0.0, Math.min(1.0, coordNorm));
        return clamped * worldSquareSideDimension;
    }

    private void clear(final double canvasW, final double canvasH) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasW, canvasH);
    }

    private void drawSpriteCentered(final String imagePath,
                                    final double centerX,
                                    final double centerY,
                                    final double worldSquareSideDimension) {
        final double spriteW = worldSquareSideDimension * SPRITE_MULT;
        final double spriteH = worldSquareSideDimension * SPRITE_MULT;
        final double x = centerX - (spriteW / 2.0);
        final double y = centerY - (spriteH / 2.0);
        if (imagePath == null || imagePath.isBlank()) {
            gc.setFill(Color.MAGENTA);
            gc.fillRect(x, y, spriteW, spriteH);
            return;
        }
        final Image img = imageCache.computeIfAbsent(imagePath, this::loadImage);
        gc.drawImage(img, x, y, spriteW, spriteH);
    }

    private Image loadImage(final String imagePath) {
        final InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            return new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/uiImages/frameMenuButton.png"),
                    "Fallback image missing too"
            ));
        }
        return new Image(is);
    }
}
