package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.audio.AudioController;
import it.unibo.crabinv.Controller.i18n.LocalizationController;
import it.unibo.crabinv.Controller.input.InputControllerPlayer;
import it.unibo.crabinv.Controller.input.InputMapperImpl;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineImpl;
import it.unibo.crabinv.SceneManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class GameScreen {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;

    public GameScreen(final SceneManager sceneManager, final LocalizationController loc, final AudioController audio) {
        this.sceneManager = Objects.requireNonNull(sceneManager, "sceneManager must not be null");
        this.loc = Objects.requireNonNull(loc, "loc must not be null");
        this.audio = Objects.requireNonNull(audio, "audio must not be null");
    }

    public Node getView() {
        final StackPane root = new StackPane();

        final double width = sceneManager.getWidth();
        final double height = sceneManager.getHeight();

        final Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        // INPUT
        final InputControllerPlayer input = new InputControllerPlayer(new InputMapperImpl());

        // Important: the node must be focusable to receive key events
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(e -> input.onKeyPressed(e.getCode().getCode()));
        canvas.setOnKeyReleased(e -> input.onKeyReleased(e.getCode().getCode()));

        // MODEL
        final GameEngine engine = new GameEngineImpl();
        engine.newGame();

        final double worldWidth = 800;
        final double worldHeight = 1600;

        // RENDERER
        final GameRenderer renderer = new GameRenderer(
                canvas.getGraphicsContext2D(),
                width,
                height,
                worldWidth,
                worldHeight
        );

        // LOOP (JavaFX)
        final AnimationTimer timer = new AnimationTimer() {
            private long lastNow = 0;

            @Override
            public void handle(long now) {
                if (lastNow == 0) {
                    lastNow = now;
                    return;
                }

                // tick-based engine: one engine.tick per call (simple for now)
                engine.tick(input.getInputState());

                renderer.render(engine.snapshot());

                lastNow = now;
            }
        };
        timer.start();

        // Request focus AFTER it is attached: Platform.runLater is the safe way
        javafx.application.Platform.runLater(canvas::requestFocus);

        return root;
    }
}
