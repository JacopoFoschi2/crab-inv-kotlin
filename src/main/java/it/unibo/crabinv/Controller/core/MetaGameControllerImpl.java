package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.input.InputControllerPlayer;
import it.unibo.crabinv.Controller.input.InputMapperImpl;
import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Controller.save.SessionController;
import it.unibo.crabinv.Model.Enemies.BaseEnemyFactoryLogic;
import it.unibo.crabinv.Model.Enemies.EnemyRewardService;
import it.unibo.crabinv.Model.Levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineImpl;
import it.unibo.crabinv.Model.save.GameSession;

import java.util.Objects;

public class MetaGameControllerImpl implements MetaGameController {

    private final SessionController sessionController;

    public MetaGameControllerImpl(SessionController sessionController) {
        this.sessionController = Objects.requireNonNull(sessionController, "SessionController cannot be null");
    }

    @Override
    public GameLoopController startGame() {
        GameSession gameSession = Objects.requireNonNull(
                this.sessionController.newOrLoadGameSession(),
                "GameSession cannot be null");
        GameEngine gameEngine = new GameEngineImpl();
        gameEngine.init(gameSession,
                new LevelFactoryImpl(),
                new BaseEnemyFactoryLogic(),
                new EnemyRewardService(gameSession)
        );
        return new GameLoopControllerImpl(
                gameEngine,
                new InputControllerPlayer(new InputMapperImpl()),
                new PlayerController(
                        gameEngine.getPlayer(),
                        gameEngine.getWorldMinX(),
                        gameEngine.getWorldMaxX())
        );
    }

    @Override
    public void stepCheck(GameLoopController gameLoopController) {

    }

}
