package it.unibo.crabinv.Model.core;

import it.unibo.crabinv.Controller.core.collision.CollisionController;
import it.unibo.crabinv.Model.core.collisions.CollisionGroups;
import it.unibo.crabinv.Model.entities.bullets.Bullet;
import it.unibo.crabinv.Model.entities.bullets.BulletFactory;
import it.unibo.crabinv.Model.entities.bullets.EnemyBulletFactory;
import it.unibo.crabinv.Model.entities.bullets.PlayerBulletFactory;
import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.enemies.wave.Wave;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.entities.entity.Entity;
import it.unibo.crabinv.Model.entities.entity.EntitySprites;
import it.unibo.crabinv.Model.entities.player.Player;
import it.unibo.crabinv.Model.levels.Level;
import it.unibo.crabinv.Model.levels.LevelFactory;
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
    private static final double ENTITY_SPRITE_BULLET_SPAWN = 0.05;
    private static final double PLAYER_RADIUS = 0.01;
    private static final int PLAYER_FIRERATE = 30;
    private static final double ENEMY_BULLET_SPAWN = 0.005;
    private final List<Bullet> activeBullets = new ArrayList<>();
    private final BulletFactory playerBulletFactory = new PlayerBulletFactory();
    private final BulletFactory enemyBulletFactory = new EnemyBulletFactory();
    private GameSession gameSession;
    private int currentLevel;
    private LevelFactory levelFactory;
    private Level level;
    private Player player;
    private long elapsedTicks;
    private GameEngineState gameEngineState;
    private CollisionController collisionController;
    private EnemyFactory enemyFactory;
    private RewardsService rewardsService;

    public GameEngineImpl() {
    }

    @Override
    public void init(GameSession gameSession,
                     LevelFactory levelFactory,
                     EnemyFactory enemyFactory,
                     RewardsService rewardsService,
                     CollisionController collisionController) {
        this.gameSession = gameSession;
        this.levelFactory = levelFactory;
        this.currentLevel = gameSession.getCurrentLevel();
        this.collisionController = collisionController;
        this.enemyFactory = enemyFactory;
        this.rewardsService = rewardsService;
        this.level = createLevel();
        player = Player.builder()
                .x(PLAYER_START_X)
                .y(PLAYER_FIXED_Y)
                .maxHealth(this.gameSession.getPlayerHealth())
                .health(this.gameSession.getPlayerHealth())
                .collisionGroup(CollisionGroups.FRIENDLY)
                .radius(PLAYER_RADIUS)
                .speed(this.gameSession.getPlayerSpeed())
                .fireRate(this.gameSession.getPlayerFireRate())
                .minBound(this.getWorldMinX())
                .maxBound(this.getWorldMaxX())
                .sprite(EntitySprites.PLAYER)
                .build();
        this.elapsedTicks = 0;
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
                bulletsUpdate();
                collisionUpdate();
                enemyToGroundCheck();
                waveCheck();
                levelCheck();
                checkGameOver();
                checkWin();
                this.elapsedTicks++;
            }
            case PAUSED, GAME_OVER, WIN -> {
            }
        }
    }

    @Override
    public GameSnapshot snapshot() {
        checkGameStarted();
        return createSnapshot(populateSnapshot());
    }

    @Override
    public GameEngineState getGameState() {
        return this.gameEngineState;
    }

    @Override
    public void gameOver() {
        this.gameEngineState = GameEngineState.GAME_OVER;
        this.gameSession.markGameOver();
    }

    @Override
    public void winGame() {
        this.gameEngineState = GameEngineState.WIN;
        this.gameSession.markGameWon();

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

    @Override
    public List<Enemy> getEnemyList() {
        if (this.level == null || this.level.isLevelFinished()) {
            return List.of();
        }
        final Wave wave = this.level.getCurrentWave();
        if (wave == null) {
            return List.of();
        }
        return wave.getAliveEnemies();
    }

    private void waveCheck() {
        if (this.level.isLevelFinished()) {
            return;
        }

        final Wave wave = this.level.getCurrentWave();
        if (wave == null) {
            return;
        }

        wave.tickUpdate();

        if (wave.isWaveFinished()) {
            if (wave.getAliveEnemies().isEmpty()) {
                this.level.advanceWave();
            }
            this.level.advanceWave();
        }
    }

    /**
     * Checks if the current level is over and advances to the next one
     */
    private void levelCheck() {
        if (this.level.isLevelFinished()) {
            gameSession.advanceLevel();
            this.currentLevel = gameSession.getCurrentLevel();
            this.level = createLevel();
            checkWin();
        }
    }

    /**
     * Creates a new level using the {@link LevelFactory}
     *
     * @return the newly created Level
     */
    private Level createLevel() {
        return this.level = levelFactory.createLevel(this.currentLevel, this.enemyFactory, this.rewardsService);
    }

    /**
     * Checks if an enemy reaches the Y of the player, destroys the enemy and subtracts player health
     */
    private void enemyToGroundCheck() {
        List<Enemy> enemyList = this.level.getCurrentWave().getAliveEnemies();
        for (Enemy enemy : enemyList) {
            if (enemy.getY() == this.player.getY()) {
                this.gameSession.subPlayerHealth(1);
                enemy.destroy();
            }
        }
    }

    /**
     * Checks game over conditions, calls game over procedures if needed
     */
    private void checkGameOver() {
        if (this.gameSession.getPlayerHealth() <= 0 && this.gameEngineState != GameEngineState.GAME_OVER) {
            gameOver();
        }
    }

    /**
     * Checks win conditions, calls win procedures if needed
     */
    private void checkWin() {
        if (this.getEnemyList().isEmpty() &&
                this.level.getCurrentWave().isWaveFinished() &&
                this.gameSession.getPlayerHealth() > 0) {
            winGame();
        }
    }

    private void bulletsUpdate() {
        for (Bullet b : activeBullets) {
            if (b.getCollisionGroup() == CollisionGroups.FRIENDLY) {
                b.move(Delta.DECREASE);
            } else {
                b.move(Delta.INCREASE);
            }
        }
        activeBullets.removeIf(b -> b.getY() < 0 || b.getY() > 1.0 || !b.isAlive());
    }

    private void collisionUpdate() {
        if (this.collisionController == null) return;
        int healthBefore = player.getHealth();

        List<Entity> allEntities = new ArrayList<>();
        allEntities.add(player);
        if (level.getCurrentWave() != null) {
            allEntities.addAll(level.getCurrentWave().getAliveEnemies());
        }
        allEntities.addAll(activeBullets);
        collisionController.resolve(allEntities);

        int damageTaken = healthBefore - player.getHealth();

        if (damageTaken > 0) {
            this.gameSession.subPlayerHealth(damageTaken);
        }
    }

    @Override
    public void spawnPlayerBullet() {
        double bulletX = this.player.getX();
        double bulletY = this.player.getY() - ENTITY_SPRITE_BULLET_SPAWN;
        activeBullets.add(playerBulletFactory.createBullet(bulletX, bulletY,
                0.0,
                1.0
        ));
    }

    @Override
    public void spawnEnemyBullet(Enemy enemy) {
        this.activeBullets.add(enemyBulletFactory.createBullet(enemy.getX(),
                enemy.getY() + ENTITY_SPRITE_BULLET_SPAWN,
                0.0,
                1.0
        ));
    }

    private void populateBullets(List<RenderObjectSnapshot> renderObjects) {
        for (Bullet b : activeBullets) {
            renderObjects.add(new RenderObjectSnapshot(b.getSprite(), b.getX(), b.getY()));
        }
    }

    /**
     * Checks if there is an active {@link GameSession}
     */
    private void checkGameStarted() {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
    }

    /**
     * Creates a {@link List<RenderObjectSnapshot>} of all game entities
     * @return the {@link List<RenderObjectSnapshot>} filled with the entities
     */
    private List<RenderObjectSnapshot> populateSnapshot() {
        final List<RenderObjectSnapshot> renderObjects = new ArrayList<>();
        populatePlayer(renderObjects);
        populateEnemies(renderObjects);
        populateBullets(renderObjects);
        return renderObjects;
    }

    /**
     * Creates a {@link GameSnapshot} from a {@link List<RenderObjectSnapshot>}, used by the {@link GameEngine}
     * @param renderObjects
     * @return
     */
    private GameSnapshot createSnapshot(List<RenderObjectSnapshot> renderObjects) {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
        return new GameSnapshot(List.copyOf(renderObjects), this.gameSession);
    }

    /**
     * Adds the player entity to the {@link List<RenderObjectSnapshot>} of entities to be rendered
     * @param renderObjects the {@link List<RenderObjectSnapshot>} of entities to be rendered
     */
    private void populatePlayer(List<RenderObjectSnapshot> renderObjects) {
        renderObjects.add(new RenderObjectSnapshot(player.getSprite(), player.getX(), player.getY()));
    }

    /**
     * Adds the enemy entities to the {@link List<RenderObjectSnapshot>} of entities to be rendered
     * @param renderObjects the {@link List<RenderObjectSnapshot>} of entities to be rendered
     */
    private void populateEnemies(List<RenderObjectSnapshot> renderObjects) {
        if (this.level != null && !this.level.isLevelFinished()) {
            final Wave wave = level.getCurrentWave();
            if (wave != null) {
                for (final Enemy enemy : wave.getAliveEnemies()) {
                    renderObjects.add(new RenderObjectSnapshot(enemy.getSprite(), enemy.getX(), enemy.getY()));
                }
            }
        }
    }

}

