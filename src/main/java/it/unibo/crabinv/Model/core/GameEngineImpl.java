package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Controller.player.PlayerController;
import it.unibo.crabinv.Model.Enemies.*;
import it.unibo.crabinv.Model.Levels.Level;
import it.unibo.crabinv.Model.Levels.LevelFactory;
import it.unibo.crabinv.Model.Levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.input.InputSnapshot;
import it.unibo.crabinv.Model.player.Player;
import it.unibo.crabinv.Model.save.*;

import java.util.ArrayList;
import java.util.List;

public class GameEngineImpl implements GameEngine {

    private static final double PLAYER_SPRITE_MULT = 0.08;
    private static final double PLAYER_HALF_SIZE_NORM = PLAYER_SPRITE_MULT / 2.0;

    private static final double WORLD_MIN_X = PLAYER_HALF_SIZE_NORM;
    private static final double WORLD_MAX_X = 1.0 - PLAYER_HALF_SIZE_NORM;

    private static final double PLAYER_START_X = 0.5;
    private static final double PLAYER_FIXED_Y = 0.90;

    Save save;
    GameSession gameSession;
    WaveProvider waveProvider; //TODO check safe-remove after correct implementation
    GameEngineState gameEngineState;
    RewardsService rewardsService;
    Level level;
    int currentLevel;
    long elapsedTicks;

    Player player;

    private final LevelFactory levelFactory = new LevelFactoryImpl();

    public GameEngineImpl() {
    }

    @Override
    public void newGame() {
        this.gameSession = new GameSessionImpl();
        this.rewardsService = new EnemyRewardService(this.gameSession);

        player = new Player(
                this.gameSession.getPlayerHealth(),
                PLAYER_START_X,
                PLAYER_FIXED_Y,
                this.gameSession.getPlayerSpeed(),
                this.gameSession.getPlayerFireRate());
        //playerController = new PlayerController(this.player, WORLD_MIN_X, WORLD_MAX_X);

        final EnemyFactory enemyFactory = new BaseEnemyFactoryLogic();

        this.gameEngineState = GameEngineState.RUNNING;
        this.currentLevel = 1;
        this.level = levelFactory.createLevel(this.currentLevel, enemyFactory, this.rewardsService);
        this.elapsedTicks = 0;
    }

    @Override
    public void tick() {
        if (this.gameSession == null) {throw new IllegalStateException("call newGame() before tick()");}
        if (this.level == null){throw new IllegalStateException("newGame() called a null level");}
        if (this.gameEngineState == null){throw new IllegalStateException("gameEngineState cannot be null");}

        switch (this.gameEngineState) {
            case RUNNING -> {
                waveUpdate();

                //TODO IMPLEMENTARE LE SEGUENTI COMPONENTI DI GAME_ENGINE
                //enemyUpdate() ? o integrare in waveUpdate();
                //collisionUpdate(); //calcola tutte le collisioni
                //enemyHealthUpdate(); //calcola tutte le modifiche alle vite degli enemy
                //playerHealthUpdate(); //calcola tutte le modifiche alla vita del player
                checkGameOver();

                this.elapsedTicks++;
                //if nemici su asse y giocatore -> -1hp
                //if hp == 0 -> is gameOver = gameOver()
                //if nemici == 0, prossima wave
                }
            case PAUSED, GAME_OVER -> {
                return;
            }
        }
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
    public GameEngineState getGameState() {
        return this.gameEngineState;
    }

    @Override
    public void gameOver() {
        if (this.gameEngineState == GameEngineState.GAME_OVER) {
            return;
        }
        this.gameEngineState = GameEngineState.GAME_OVER;

        this.gameSession.markGameOver();
        SessionRecord sessionRecord = new SessionRecordImpl(
                this.gameSession.getStartingTimeStamp(),
                this.gameSession.getCurrentLevel(),
                this.gameSession.getCurrency());
        //
        //TODO Aggiorna UserProfile
    }

    @Override
    public void pauseGame() {
        if (this.gameEngineState == GameEngineState.RUNNING) {this.gameEngineState = GameEngineState.PAUSED;}
    }

    @Override
    public void resumeGame() {
        if (this.gameEngineState == GameEngineState.PAUSED) {this.gameEngineState = GameEngineState.RUNNING;}
    }

    @Override
    public Player getPlayer() {
        if (this.player == null) {
            throw new IllegalStateException("call newGame() before getPlayer()");
        }
        return this.player;
    }

    @Override
    public double getWorldMinX() {
        return WORLD_MIN_X;
    }

    @Override
    public double getWorldMaxX() {
        return WORLD_MAX_X;
    }

    private void waveUpdate(){
        if (!level.isLevelFinished()) {
            final Wave currentWave = level.getCurrentWave();
            if (currentWave != null) {
                currentWave.tickLogicUpdate();
                if (currentWave.isWaveFinished()) {
                    level.advanceWave();
                }
            }
        }
    }

    private void checkGameOver(){
        if (this.gameSession.getPlayerHealth() <= 0) {gameOver();}
        }
    }

