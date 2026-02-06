package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Controller.input.InputControllerPlayer;
import it.unibo.crabinv.Controller.input.InputMapperImpl;
import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.Enemies.BaseEnemyFactoryLogic;
import it.unibo.crabinv.Model.Enemies.EnemyRewardService;
import it.unibo.crabinv.Model.Levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineImpl;
import it.unibo.crabinv.Model.core.GameSnapshot;
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
        if (gameLoopController != null) {
            throw new IllegalStateException("Cannot start a Game with another Game running");
        }
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
                        gameEngine.getWorldMinX(),
                        gameEngine.getWorldMaxX()));
    }


    @Override
    public GameSnapshot stepCheck(long frameElapsedMillis) {
        if (gameLoopController == null) {
            throw new IllegalStateException("No Game is currently active");
        }
        GameSnapshot gameSnapshot = this.gameLoopController.step(frameElapsedMillis);
        GameSession gameSession = this.sessionController.getGameSession();
        if (gameSession == null){
            return gameSnapshot;
        }
        if (gameSession.isGameOver()){
            this.sessionController.gameOverGameSession();
            this.gameLoopController = null;
            this.inputController = null;
        }
        return gameSnapshot;
    }

}
