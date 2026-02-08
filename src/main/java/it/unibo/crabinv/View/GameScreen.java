package it.unibo.crabinv.View;

import it.unibo.crabinv.Controller.core.MetaGameController;
import it.unibo.crabinv.Controller.core.MetaGameControllerImpl;
import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.core.i18n.LocalizationController;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Controller.save.SessionControllerImpl;
import it.unibo.crabinv.Model.save.Save;
import it.unibo.crabinv.SceneManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class GameScreen {

    private final SceneManager sceneManager;
    private final LocalizationController loc;
    private final AudioController audio;
    private final Save save;
    private MetaGameController metaGameController;

    public GameScreen(final SceneManager sceneManager, final LocalizationController loc, final AudioController audio, final Save save) {
        this.sceneManager = Objects.requireNonNull(sceneManager, "sceneManager must not be null");
        this.loc = Objects.requireNonNull(loc, "loc must not be null");
        this.audio = Objects.requireNonNull(audio, "audio must not be null");
        this.save = Objects.requireNonNull(save, "save must not be null");
    }

    public Node getView() {
        final StackPane root = new StackPane();
        final double width = sceneManager.getWidth();
        final double height = sceneManager.getHeight();
        final Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        final SessionController sessionController = new SessionControllerImpl(this.save);
        metaGameController = new MetaGameControllerImpl(sessionController);
        final GameRenderer gameRenderer = new GameRenderer(canvas.getGraphicsContext2D());

        metaGameController.startGame();
        metaGameController.getInputController();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            metaGameController.getInputController().onKeyPressed(e.getCode().getCode());
            if (e.getCode() == KeyCode.ESCAPE) {
                sceneManager.showPauseMenu();
                metaGameController.getGameLoopController().resume();
            }
        });
        canvas.setOnKeyReleased(e -> metaGameController.getInputController().onKeyReleased(e.getCode().getCode()));
        /*
        final InputControllerPlayer input = new InputControllerPlayer(new InputMapperImpl());
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> input.onKeyPressed(e.getCode().getCode()));
        canvas.setOnKeyReleased(e -> input.onKeyReleased(e.getCode().getCode()));

        final GameEngine engine = new GameEngineImpl();
        final GameLoopController gameLoopController = new GameLoopControllerImpl(engine, input);
        final GameRenderer renderer = new GameRenderer(
                canvas.getGraphicsContext2D()
        );
         */
        final AnimationTimer timer = new AnimationTimer() {
            private long lastNow = 0;

            @Override
            public void handle(long now) {
                if (lastNow == 0) {
                    lastNow = now;
                    return;
                }

                final long frameElapsedMillis = Math.max(0L, (now - lastNow) / 1_000_000L);
                gameRenderer.render(metaGameController.stepCheck(frameElapsedMillis));

                lastNow = now;
            }
        };
        timer.start();

        javafx.application.Platform.runLater(canvas::requestFocus);

        return root;
    }

    /**
     * Exposes the resume method of the gameLoop to be used by the resume menu
     * @return the runnable of the resume method
     */
    public Runnable getResume() {
        return new Runnable() {
            @Override
            public void run() {
                metaGameController.getGameLoopController().resume();
            }
        };
    }

    /**
     * Exposes the gameOver method of the gameLoop to be used by the resume menu
     * @return the runnable of the gameOver method
     */
    public Runnable getGameOver() {
        return new Runnable() {
            @Override
            public void run() {
                metaGameController.endGame();
            }
        };
    }
}
