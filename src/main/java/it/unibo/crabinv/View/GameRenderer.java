package it.unibo.crabinv.View;

import it.unibo.crabinv.Model.core.GameSnapshot;
import it.unibo.crabinv.Model.core.RenderObjectSnapshot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GameRenderer {
    private static final double SPRITE_W = 96;
    private static final double SPRITE_H = 96;

    private final GraphicsContext gc;
    private final double canvasWidth;
    private final double canvasHeight;

    private final double worldWidth;
    private final double worldHeight;

    private final Map<String, Image> imageCache = new HashMap<>();

    public GameRenderer(final GraphicsContext gc,
                        final double canvasWidth,
                        final double canvasHeight,
                        final double worldWidth,
                        final double worldHeight) {
        this.gc = Objects.requireNonNull(gc, "gc must not be null");
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void render(final GameSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot must not be null");
        clear();

        final double playfieldWidth = canvasWidth / 3.0;
        final double offsetX = (canvasWidth - playfieldWidth) / 2.0;

        final double scaleX = playfieldWidth / worldWidth;
        final double scaleY = canvasHeight / worldHeight;

        for (final RenderObjectSnapshot obj : snapshot.renderObjects()) {
            final double px = offsetX + obj.x() * scaleX;
            final double py = obj.y() * scaleY;
            drawSprite(obj.imagePath(), px, py);
        }
    }

    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    private void drawSprite(final String imagePath, final double x, final double y) {
        if (imagePath == null || imagePath.isBlank()) {
            // fallback: if missing sprite path, draw a placeholder rectangle
            gc.setFill(Color.MAGENTA);
            gc.fillRect(x, y, SPRITE_W, SPRITE_H);
            return;
        }

        final Image img = imageCache.computeIfAbsent(imagePath, this::loadImage);
        gc.drawImage(img, x, y, SPRITE_W, SPRITE_H);
    }

    private Image loadImage(final String imagePath) {
        final InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            // If resource missing, return a 1x1 transparent image to avoid crashing render loop
            return new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/uiImages/frameMenuButton.png"),
                    "Fallback image missing too"
            ));
        }
        return new Image(is);
    }
}
