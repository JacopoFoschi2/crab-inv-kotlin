package it.unibo.crabinv.view;

import it.unibo.crabinv.SceneManager;
import it.unibo.crabinv.controller.core.MetaGameController;
import it.unibo.crabinv.controller.core.MetaGameControllerImpl;
import it.unibo.crabinv.controller.save.SessionController;
import it.unibo.crabinv.controller.save.SessionControllerImpl;
import it.unibo.crabinv.model.core.GameEngine;
import it.unibo.crabinv.model.core.GameEngineState;
import it.unibo.crabinv.model.save.Save;
import it.unibo.crabinv.persistence.repository.SaveRepository;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * View {@code} Class to show the game when running.
 */
public class GameScreen {

    private final SceneManager sceneManager;
    private final Save save;
    private final SaveRepository repo;
    private MetaGameController metaGameController;
    private AnimationTimer timer;
    private GameRenderer gameRenderer;
    private GameEngineState lastEngineState;

    /**
     * Constructor of {@link GameScreen}.
     *
     * @param sceneManager the {@link SceneManager} used by the {@link GameScreen}
     * @param save         the {@link Save} used by the {@link GameScreen}
     * @param repo         the {@link SaveRepository} used by the {@link GameScreen}
     */
    public GameScreen(final SceneManager sceneManager,
                      final Save save,
                      final SaveRepository repo) {
        this.sceneManager = Objects.requireNonNull(sceneManager, "sceneManager must not be null");
        this.save = Objects.requireNonNull(save, "save must not be null");
        this.repo = Objects.requireNonNull(repo, "SaveRepository must not be null");
        this.lastEngineState = null;
    }

    /**
     * The Node of the {@link GameScreen} view.
     *
     * @return the Node of the {@link GameScreen} view
     */
    public final Node getView() {
        final StackPane root = new StackPane();
        final double width = sceneManager.getWidth();
        final double height = sceneManager.getHeight();
        final Canvas canvas = new Canvas(width, height);
        final Label hp = new Label();
        final Label money = new Label();
        final VBox hud = new VBox(ViewParameters.DEFAULT_HUD_MARGIN, hp, money);
        StackPane.setAlignment(hud, Pos.TOP_LEFT);
        StackPane.setMargin(hud, new Insets(ViewParameters.DEFAULT_INSETS_PANE));
        root.getChildren().addAll(canvas, hud);
        final SessionController sessionController = new SessionControllerImpl(this.save);
        metaGameController = new MetaGameControllerImpl(sessionController, repo);
        this.gameRenderer = new GameRenderer(canvas.getGraphicsContext2D());
        metaGameController.startGame();
        metaGameController.getInputController();
        this.lastEngineState = metaGameController.getGameEngineState();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            metaGameController.getInputController().onKeyPressed(e.getCode().getCode());
            if (e.getCode() == KeyCode.ESCAPE) {
                sceneManager.showPauseMenu();
                metaGameController.getGameLoopController().resume();
            }
        });
        canvas.setOnKeyReleased(e -> metaGameController.getInputController().onKeyReleased(e.getCode().getCode()));
        this.timer = new AnimationTimer() {
            private long lastNow;

            @Override
            public void handle(final long now) {
                if (lastNow == 0) {
                    lastNow = now;
                    return;
                }
                hp.setText("HP: " + sessionController.getGameSession().getPlayerHealth());
                money.setText("Coins: " + sessionController.getGameSession().getCurrency());
                final long frameElapsedMillis = Math.max(0L, (now - lastNow) / 1_000_000L);
                try {
                    gameRenderer.render(metaGameController.stepCheck(frameElapsedMillis));
                } catch (final IOException error) {
                    throw new UncheckedIOException(error);
                }
                final GameEngineState currentEngineState = metaGameController.getGameEngineState();
                if (currentEngineState != lastEngineState) {
                    lastEngineState = currentEngineState;
                    engineStatusTrigger(currentEngineState);
                }
                lastNow = now;
            }
        };
        timer.start();
        javafx.application.Platform.runLater(canvas::requestFocus);
        return root;
    }

    /**
     * Checks the Win/Game Over Conditions of the {@link GameEngine} and triggers the relative screens.
     *
     * @param state the {@link GameEngineState} of the {@link GameEngine}
     */
    private void engineStatusTrigger(final GameEngineState state) {
        if (state == GameEngineState.GAME_OVER || state == GameEngineState.WIN) {
            closeEngineStep1();
            closeEngineStep2();
            switch (state) {
                case GAME_OVER -> {
                    sceneManager.showGameOver(GameOver.MessageTypes.GAME_OVER);
                }
                case WIN -> {
                    sceneManager.showGameOver(GameOver.MessageTypes.VICTORY);
                }
                default -> {

                }
            }
        }
    }

    /**
     * Exposes the resume method of the gameLoop to be used by the resume menu.
     *
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
     * Executes the first part of closing procedures for the {@link GameEngine}.
     */
    private void closeEngineStep1() {
        timer.stop();
    }

    /**
     * Executes the second part of closing procedures for the {@link GameEngine}.
     */
    private void closeEngineStep2() {
        timer = null;
        gameRenderer = null;
        metaGameController = null;
    }

    /**
     * Exposes the gameOver method of the gameLoop to be used by the resume menu.
     *
     * @return the runnable of the gameOver method
     */
    public Runnable getGameOver() {
        return new Runnable() {
            @Override
            public void run() {
                closeEngineStep1();
                metaGameController.endGame();
                closeEngineStep2();
            }
        };
    }
}
