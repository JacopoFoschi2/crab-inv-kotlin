package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Model.Enemies.BaseEnemyFactoryLogic;
import it.unibo.crabinv.Model.Enemies.Enemy;
import it.unibo.crabinv.Model.Enemies.WaveSequence;
import it.unibo.crabinv.Model.input.InputSnapshot;
import it.unibo.crabinv.Model.player.Player;
import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.GameSessionImpl;

import java.util.List;

public class GameEngineImpl implements GameEngine{

    GameSession gameSession;
    Player player;
    PlayerController playerController;
    List<Enemy> enemyList;
    long elapsedTicks;

    final int WorldWidth = 800;
    final int WorldHeight = 800;

    final int playerMinX = 0;
    final int playerMaxX = WorldWidth;
    final int playerStartX = playerMaxX/2;
    final int playerFixedY = WorldHeight/16;

    public GameEngineImpl(){

    }


    @Override
    public void tick(InputSnapshot inputSnapshot) {
        //if tick == 0 prima wave
        playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta());
        //metodo di movimento nemici
        //if nemici su asse y giocatore -> -1hp
        //if hp == 0 -> is gameOver = true, return
        //if nemici == 0, prossima wave


    }

    @Override
    public GameSnapshot snapshot() {
        return null;
    }

    @Override
    public void newGame() {
        this.gameSession = new GameSessionImpl();
        this.elapsedTicks = 0;
        player = new Player(this.gameSession.getPlayerHealth(),playerStartX,playerFixedY,1,1);
        playerController = new PlayerController(this.player,playerMinX,playerMaxX);
        // waves wip
        BaseEnemyFactoryLogic enemyFactory = new BaseEnemyFactoryLogic();

    }
}
