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

        final InputControllerPlayer input = new InputControllerPlayer(new InputMapperImpl());

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> input.onKeyPressed(e.getCode().getCode()));
        canvas.setOnKeyReleased(e -> input.onKeyReleased(e.getCode().getCode()));

        final GameEngine engine = new GameEngineImpl();
        engine.newGame();

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

                engine.tick(input.getInputState());
                renderer.render(engine.snapshot());

                lastNow = now;
            }
        };
        timer.start();

        javafx.application.Platform.runLater(canvas::requestFocus);

        return root;
    }
}
