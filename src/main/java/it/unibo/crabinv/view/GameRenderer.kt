package it.unibo.crabinv.view

import it.unibo.crabinv.model.core.snapshot.GameSnapshot
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color
import java.util.Objects
import kotlin.math.max
import kotlin.math.min

/**
 * Renderer to be shown in the [GameScreen].
 * Initial generation with AI, adapted to work correctly
 * @param gc the [GraphicsContext] used by the [GameRenderer]
 */
class GameRenderer(
    gc: GraphicsContext?,
) {
    private val gc: GraphicsContext = Objects.requireNonNull<GraphicsContext>(gc, "gc must not be null")
    private val imageCache: MutableMap<String?, Image> = HashMap()

    /**
     * Renderer based on the [GameSnapshot] received.
     * @param snapshot received from the game and used to render
     */
    fun render(snapshot: GameSnapshot?) {
        Objects.requireNonNull<GameSnapshot?>(snapshot, "snapshot must not be null")
        val canvasW = gc.canvas.width
        val canvasH = gc.canvas.height
        val worldSquareSideDimension = min(canvasW, canvasH)
        val offsetX = (canvasW - worldSquareSideDimension) / 2.0
        val offsetY = (canvasH - worldSquareSideDimension) / 2.0
        clear(canvasW, canvasH)
        gc.stroke = Color.DARKGRAY
        gc.strokeRect(offsetX, offsetY, worldSquareSideDimension, worldSquareSideDimension)
        for (obj in snapshot!!.renderObjects) {
            val cx = offsetX + toPixels(obj.x, worldSquareSideDimension)
            val cy = offsetY + toPixels(obj.y, worldSquareSideDimension)
            drawSpriteCentered(obj.imagePath, cx, cy, worldSquareSideDimension)
        }
    }

    private fun toPixels(
        coordNorm: Double,
        worldSquareSideDimension: Double,
    ): Double {
        val clamped = max(0.0, min(1.0, coordNorm))
        return clamped * worldSquareSideDimension
    }

    private fun clear(
        canvasW: Double,
        canvasH: Double,
    ) {
        gc.fill = Color.BLACK
        gc.fillRect(0.0, 0.0, canvasW, canvasH)
    }

    private fun drawSpriteCentered(
        imagePath: String?,
        centerX: Double,
        centerY: Double,
        worldSquareSideDimension: Double,
    ) {
        val spriteW: Double = worldSquareSideDimension * SPRITE_MULT
        val spriteH: Double = worldSquareSideDimension * SPRITE_MULT
        val x = centerX - (spriteW / 2.0)
        val y = centerY - (spriteH / 2.0)
        if (imagePath.isNullOrBlank()) {
            gc.fill = Color.MAGENTA
            gc.fillRect(x, y, spriteW, spriteH)
            return
        }
        val img = imageCache.computeIfAbsent(imagePath) { imagePath: String? -> this.loadImage(imagePath!!) }
        gc.drawImage(img, x, y, spriteW, spriteH)
    }

    private fun loadImage(imagePath: String): Image {
        val `is` =
            javaClass.getResourceAsStream(imagePath) ?: return Image(
                Objects.requireNonNull(
                    javaClass.getResourceAsStream("/uiImages/frameMenuButton.png"),
                    "Fallback image missing too",
                ),
            )
        return Image(`is`)
    }

    companion object {
        private const val SPRITE_MULT = 0.08
    }
}
