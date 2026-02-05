package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.GameLoopController;
import it.unibo.crabinv.Controller.core.GameLoopControllerImpl;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
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

        final InputControllerPlayer input = new InputControllerPlayer(new InputMapperImpl());
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> input.onKeyPressed(e.getCode().getCode()));
        canvas.setOnKeyReleased(e -> input.onKeyReleased(e.getCode().getCode()));

        final GameEngine engine = new GameEngineImpl();
        final GameLoopController gameLoopController = new GameLoopControllerImpl(engine, input);
        final GameRenderer renderer = new GameRenderer(
                canvas.getGraphicsContext2D()
        );

        final AnimationTimer timer = new AnimationTimer() {
            private long lastNow = 0;

            @Override
            public void handle(long now) {
                if (lastNow == 0) {
                    lastNow = now;
                    return;
                }

                final long frameElapsedMillis = Math.max(0L, (now - lastNow) / 1_000_000L);
                renderer.render(gameLoopController.step(frameElapsedMillis));

                lastNow = now;
            }
        };
        timer.start();

        javafx.application.Platform.runLater(canvas::requestFocus);

        return root;
    }
}
