package it.unibo.crabinv.controller.core;

import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.core.collision.CollisionController;
import it.unibo.crabinv.controller.entities.player.PlayerController;
import it.unibo.crabinv.controller.input.InputController;
import it.unibo.crabinv.controller.input.InputControllerPlayer;
import it.unibo.crabinv.controller.input.InputMapperImpl;
import it.unibo.crabinv.controller.save.SaveControllerImpl;
import it.unibo.crabinv.controller.save.SessionController;
import it.unibo.crabinv.model.core.GameEngine;
import it.unibo.crabinv.model.core.GameEngineImpl;
import it.unibo.crabinv.model.core.GameEngineState;
import it.unibo.crabinv.model.core.GameSnapshot;
import it.unibo.crabinv.model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.model.entities.enemies.BaseEnemyFactoryLogic;
import it.unibo.crabinv.model.entities.enemies.rewardservice.EnemyRewardService;
import it.unibo.crabinv.model.levels.LevelFactoryImpl;
import it.unibo.crabinv.model.save.GameSession;
import it.unibo.crabinv.persistence.repository.SaveRepository;

import java.io.IOException;
import java.util.Objects;

/**
 * Implementation of {@link MetaGameController}.
 */
public class MetaGameControllerImpl implements MetaGameController {

    private final SessionController sessionController;
    private final SaveRepository saveRepository;
    private final GameEngine gameEngine;
    private InputController inputController;
    private GameLoopController gameLoopController;

    /**
     * Constructor of {@link MetaGameControllerImpl}.
     *
     * @param sessionController the {@link SessionController} used by the {@link MetaGameControllerImpl}
     * @param saveRepository    the {@link SaveRepository} used by the {@link MetaGameControllerImpl}
     */
    public MetaGameControllerImpl(SessionController sessionController, SaveRepository saveRepository) {
        this.sessionController = Objects.requireNonNull(sessionController, "SessionController cannot be null");
        this.saveRepository = Objects.requireNonNull(saveRepository, "SaveRepository cannot be null");
        this.gameEngine = new GameEngineImpl();
        this.gameLoopController = null;
        this.inputController = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void startGame() {
        GameSession gameSession = Objects.requireNonNull(
                this.sessionController.newGameSession(),
                "GameSession cannot be null");
        AudioController sharedAudio = new AudioController(new JavaFXSoundManager());
        this.gameEngine.init(
                gameSession,
                new LevelFactoryImpl(),
                new BaseEnemyFactoryLogic(),
                new EnemyRewardService(gameSession),
                new CollisionController(sharedAudio)
        );
        this.inputController = new InputControllerPlayer(new InputMapperImpl());
        this.gameLoopController = new GameLoopControllerImpl(
                gameEngine,
                this.inputController,
                new PlayerController(
                        gameEngine.getPlayer(),
                        sharedAudio,
                        this.gameEngine),
                sharedAudio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameSnapshot stepCheck(final long frameElapsedMillis) throws IOException {
        if (gameLoopController == null) {
            throw new IllegalStateException("No Game is currently active");
        }
        final GameSnapshot gameSnapshot = this.gameLoopController.step(frameElapsedMillis);
        final GameSession gameSession = this.sessionController.getGameSession();
        if (gameSession == null) {
            return gameSnapshot;
        }
        checkAndManageGameEnd(gameSession);
        return gameSnapshot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final InputController getInputController() {
        return Objects.requireNonNull(this.inputController, "inputController cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameLoopController getGameLoopController() {
        return Objects.requireNonNull(this.gameLoopController, "GameLoopController cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameEngineState getGameEngineState() {
        return this.gameEngine.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void endGame() {
        this.gameEngine.gameOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateSave() throws IOException {
        new SaveControllerImpl(saveRepository).updateSave(this.sessionController.save());
    }

    /**
     * Checks if the {@link GameSession} has a Game over or win status.
     *
     * @param gameSession the {@link GameSession} to check
     * @throws IOException if an IO error occurs
     */
    private void checkAndManageGameEnd(final GameSession gameSession) throws IOException {
        if (gameSession.isGameOver() || gameSession.isGameWon()) {
            this.sessionController.gameOverGameSession();
            this.gameLoopController = null;
            this.inputController = null;
            updateSave();
        }
    }
}