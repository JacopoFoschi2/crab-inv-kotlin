package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.entities.player.PlayerController;
import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Controller.input.InputControllerPlayer;
import it.unibo.crabinv.Controller.input.InputMapperImpl;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineImpl;
import it.unibo.crabinv.Model.core.GameSnapshot;
import it.unibo.crabinv.Model.core.audio.JavaFXSoundManager;
import it.unibo.crabinv.Model.entities.enemies.BaseEnemyFactoryLogic;
import it.unibo.crabinv.Model.entities.enemies.rewardService.EnemyRewardService;
import it.unibo.crabinv.Model.levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.save.GameSession;

import java.util.Objects;

public class MetaGameControllerImpl implements MetaGameController {

    private final SessionController sessionController;
    private final GameEngine gameEngine;
    private InputController inputController;
    private GameLoopController gameLoopController;

    public MetaGameControllerImpl(SessionController sessionController) {
        this.sessionController = Objects.requireNonNull(sessionController, "SessionController cannot be null");
        this.gameEngine = new GameEngineImpl();
        this.gameLoopController = null;
        this.inputController = null;
    }

    @Override
    public void startGame() {
        GameSession gameSession = Objects.requireNonNull(
                this.sessionController.newOrLoadGameSession(),
                "GameSession cannot be null");
        this.gameEngine.init(
                gameSession,
                new LevelFactoryImpl(),
                new BaseEnemyFactoryLogic(),
                new EnemyRewardService(gameSession)
        );
        this.inputController = new InputControllerPlayer(new InputMapperImpl());
        this.gameLoopController = new GameLoopControllerImpl(
                gameEngine,
                this.inputController,
                new PlayerController(
                        gameEngine.getPlayer(),
                        new AudioController(new JavaFXSoundManager())
                ));
    }



    @Override
    public GameSnapshot stepCheck(long frameElapsedMillis) {
        if (gameLoopController == null) {
            throw new IllegalStateException("No Game is currently active");
        }
        GameSnapshot gameSnapshot = this.gameLoopController.step(frameElapsedMillis);
        GameSession gameSession = this.sessionController.getGameSession();
        if (gameSession == null) {
            return gameSnapshot;
        }
        if (gameSession.isGameOver()) {
            this.sessionController.gameOverGameSession();
            this.gameLoopController = null;
            this.inputController = null;
        }
        return gameSnapshot;
    }

    @Override
    public InputController getInputController(){
        return Objects.requireNonNull(this.inputController, "inputController cannot be null");
    }

    @Override
    public GameLoopController getGameLoopController(){
        return Objects.requireNonNull(this.gameLoopController, "GameLoopController cannot be null");
    }
}
