package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Model.Enemies.*;
import it.unibo.crabinv.Model.Levels.Level;
import it.unibo.crabinv.Model.Levels.LevelFactory;
import it.unibo.crabinv.Model.Levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.input.InputSnapshot;
import it.unibo.crabinv.Model.player.Player;
import it.unibo.crabinv.Model.save.GameSession;
import it.unibo.crabinv.Model.save.GameSessionImpl;

import java.util.ArrayList;
import java.util.List;

public class GameEngineImpl implements GameEngine {

    final double WorldWidth = 800;
    final double WorldHeight = 1600;
    final double playerMinX = 0;
    final double playerMaxX = WorldWidth;
    final double playerStartX = playerMaxX / 2;
    final double playerFixedY = 1500; //TODO togliere la const di prova

    GameSession gameSession;
    WaveProvider waveProvider;
    RewardsService rewardsService;
    Level level;
    int currentLevel;
    long elapsedTicks;

    Player player;
    PlayerController playerController;

    private final LevelFactory levelFactory = new LevelFactoryImpl();

    public GameEngineImpl() {
    }

    @Override
    public void newGame() {
        this.gameSession = new GameSessionImpl();
        this.rewardsService = new EnemyRewardService(this.gameSession);

        player = new Player(this.gameSession.getPlayerHealth(), playerStartX, playerFixedY, 1, 1);
        playerController = new PlayerController(this.player, playerMinX, playerMaxX);

        EnemyFactory enemyFactory = new BaseEnemyFactoryLogic();

        this.currentLevel = 1;
        this.level = levelFactory.createLevel(this.currentLevel, enemyFactory, this.rewardsService);

        this.elapsedTicks = 0;
    }

    @Override
    public void tick(InputSnapshot inputSnapshot) {
        if (inputSnapshot == null) {throw new IllegalStateException("inputSnapshot cannot be null");}
        if (this.gameSession == null) {throw new IllegalStateException("call newGame() before tick()");}
        if (this.level == null){throw new IllegalStateException("newGame() called a null level");}
        if (this.gameSession.isGameOver()){return;} //da cambiare, il metodo gameOver() deve bloccare i tick e svolgere le operazioni di fine partita

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
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
        final List<RenderObjectSnapshot> objects = new ArrayList<>();

        objects.add(new RenderObjectSnapshot(player.getImagePath(), player.getX(), player.getY()));

        if (this.level != null && !this.level.isLevelFinished()) {
            final Wave wave = level.getCurrentWave();
            if (wave != null) {
                for (final Enemy enemy : wave.getAliveEnemies()) {
                    objects.add(new RenderObjectSnapshot(enemy.getImagePath(), enemy.getX(), enemy.getY()));
                }
            }
        }

        return new GameSnapshot(List.copyOf(objects), this.gameSession);
    }

    @Override
    public void gameOver() {
        this.gameSession.markGameOver();
        //TODO Crea SessionRecord
        //TODO Aggiorna UserProfile

    }


}
