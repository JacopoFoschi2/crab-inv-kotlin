package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Model.Enemies.*;
import it.unibo.crabinv.Model.Levels.Level;
import it.unibo.crabinv.Model.input.InputSnapshot;
import it.unibo.crabinv.Model.player.Player;
import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.GameSessionImpl;

import java.util.List;

public class GameEngineImpl implements GameEngine {

    final int WorldWidth = 800;
    final int WorldHeight = 1600;
    final int playerMinX = 0;
    final int playerMaxX = WorldWidth;
    final int playerStartX = playerMaxX / 2;
    final int playerFixedY = 100;

    GameSession gameSession;
    WaveProvider waveProvider;
    RewardsService rewardsService;
    Level level;
    long elapsedTicks;

    Player player;
    PlayerController playerController;


    public GameEngineImpl() {
    }

    @Override
    public void newGame() {
        this.gameSession = new GameSessionImpl();
        this.rewardsService = new EnemyRewardService(this.gameSession);

        player = new Player(this.gameSession.getPlayerHealth(), playerStartX, playerFixedY, 1, 1);
        playerController = new PlayerController(this.player, playerMinX, playerMaxX);

        EnemyFactory enemyFactory = new BaseEnemyFactoryLogic();




//        List<Wave> waves = List.of(
//                new WaveImpl(List.of(EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT), enemyFactory, rewardsService),
//                new WaveImpl(List.of(EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT, EnemyType.SERVANT), enemyFactory, rewardsService)
//        );

        this.elapsedTicks = 0;

    }

    @Override
    public void tick(InputSnapshot inputSnapshot) {
        if (this.gameSession == null) {throw new IllegalStateException("call newGame() before tick()");}
        if (this.gameSession.isGameOver()){return;} //da cambiare, il metodo gameOver() deve bloccare i tick e svolgere le operazioni di fine partita
        //if tick == 0 prima wave
        playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta());

        if (!level.isLevelFinished()) {
            final Wave currentWave = level.getCurrentWave();
            if (currentWave != null) {
                currentWave.tickLogicUpdate();
                if (currentWave.isWaveFinished()){
                    level.advanceWave();
                }
            }
        }

        this.elapsedTicks ++;
        //if nemici su asse y giocatore -> -1hp
        //if hp == 0 -> is gameOver = gameOver()
        //if nemici == 0, prossima wave

    }

    @Override
    public GameSnapshot snapshot() {
        return null;
    }

    @Override
    public void gameOver() {
        this.gameSession.markGameOver();
        //Crea SessionRecord
        //Aggiorna UserProfile

    }


}
