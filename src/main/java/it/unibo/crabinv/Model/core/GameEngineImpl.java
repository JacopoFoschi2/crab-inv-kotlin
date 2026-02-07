package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Model.Enemies.Enemy;
import it.unibo.crabinv.Model.Enemies.EnemyFactory;
import it.unibo.crabinv.Model.Enemies.RewardsService;
import it.unibo.crabinv.Model.Enemies.Wave;
import it.unibo.crabinv.Model.Levels.Level;
import it.unibo.crabinv.Model.Levels.LevelFactory;
import it.unibo.crabinv.Model.Levels.LevelFactoryImpl;
import it.unibo.crabinv.Model.player.Player;
import it.unibo.crabinv.Model.save.GameSession;

import java.util.ArrayList;
import java.util.List;

public class GameEngineImpl implements GameEngine {

    private static final double PLAYER_SPRITE_MULT = 0.08;
    private static final double PLAYER_HALF_SIZE_NORM = PLAYER_SPRITE_MULT / 2.0;
    private static final double WORLD_MIN_X = PLAYER_HALF_SIZE_NORM;
    private static final double WORLD_MAX_X = 1.0 - PLAYER_HALF_SIZE_NORM;
    private static final double PLAYER_START_X = 0.5;
    private static final double PLAYER_FIXED_Y = 0.90;

    private GameSession gameSession;
    private int currentLevel;
    private LevelFactory levelFactory;
    private EnemyFactory enemyFactory;
    private RewardsService rewardsService;
    private Level level;
    private Player player;
    private long elapsedTicks;
    private GameEngineState gameEngineState;

    public GameEngineImpl() {
    }

    @Override
    public void init(GameSession gameSession,
                     LevelFactory levelFactory,
                     EnemyFactory enemyFactory,
                     RewardsService rewardsService) {
        this.gameSession = gameSession;
        this.levelFactory = levelFactory;
        this.currentLevel = gameSession.getCurrentLevel();
        this.level = levelFactory.createLevel(this.currentLevel, enemyFactory, rewardsService);
        this.player = new Player(
                this.gameSession.getPlayerHealth(),
                PLAYER_START_X,
                PLAYER_FIXED_Y,
                this.gameSession.getPlayerSpeed(),
                this.gameSession.getPlayerFireRate());
        this.elapsedTicks = 0; //TODO controllare se necessario salvare in GameSession
        this.gameEngineState = GameEngineState.RUNNING;
    }

    @Override
    public void tick() {
        if (this.gameSession == null) {
            throw new IllegalStateException("call newGame() before tick()");
        }
        if (this.level == null) {
            throw new IllegalStateException("newGame() called a null level");
        }
        if (this.gameEngineState == null) {
            throw new IllegalStateException("gameEngineState cannot be null");
        }
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
            }
        }
    }

    @Override
    public GameSnapshot snapshot() {
        checkGameStarted();
        return createSnapshot(populateSnapshot());//TODO inserire snapshot bullet in populateSnapshot().populateBullet()
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
    }

    @Override
    public void pauseGame() {
        if (this.gameEngineState == GameEngineState.RUNNING) {
            this.gameEngineState = GameEngineState.PAUSED;
        }
    }

    @Override
    public void resumeGame() {
        if (this.gameEngineState == GameEngineState.PAUSED) {
            this.gameEngineState = GameEngineState.RUNNING;
        }
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

    private void waveUpdate() {
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

    private void checkGameStarted() {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
    }

    private List<RenderObjectSnapshot> populateSnapshot() {
        final List<RenderObjectSnapshot> renderObjects = new ArrayList<>();
        populatePlayer(renderObjects);
        populateEnemies(renderObjects);
        //populateBullets(renderObjects); //TODO
        return renderObjects;
    }


    private GameSnapshot createSnapshot(List<RenderObjectSnapshot> renderObjects) {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
        return new GameSnapshot(List.copyOf(renderObjects), this.gameSession);
    }

    private void populatePlayer(List<RenderObjectSnapshot> renderObjects) {
        renderObjects.add(new RenderObjectSnapshot(player.getImagePath(), player.getX(), player.getY()));
    }

    private void populateEnemies(List<RenderObjectSnapshot> renderObjects) {
        if (this.level != null && !this.level.isLevelFinished()) {
            final Wave wave = level.getCurrentWave();
            if (wave != null) {
                for (final Enemy enemy : wave.getAliveEnemies()) {
                    renderObjects.add(new RenderObjectSnapshot(enemy.getImagePath(), enemy.getX(), enemy.getY()));
                }
            }
        }
    }

    private void populateBullets(List<RenderObjectSnapshot> renderObjects) {

    }

    private void checkGameOver() {
        if (this.gameSession.getPlayerHealth() <= 0) {
            gameOver();
        }
    }
}

