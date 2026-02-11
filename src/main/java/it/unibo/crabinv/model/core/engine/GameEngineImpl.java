package it.unibo.crabinv.model.core.engine;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.controller.core.collision.CollisionController;
import it.unibo.crabinv.model.core.snapshot.GameSnapshot;
import it.unibo.crabinv.model.core.snapshot.RenderObjectSnapshot;
import it.unibo.crabinv.model.core.collisions.CollisionGroups;
import it.unibo.crabinv.model.entities.bullets.Bullet;
import it.unibo.crabinv.model.entities.bullets.BulletFactory;
import it.unibo.crabinv.model.entities.bullets.EnemyBulletFactory;
import it.unibo.crabinv.model.entities.bullets.PlayerBulletFactory;
import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.model.entities.enemies.rewardservice.RewardsService;
import it.unibo.crabinv.model.entities.enemies.wave.Wave;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.entities.entity.Entity;
import it.unibo.crabinv.model.entities.entity.EntitySprites;
import it.unibo.crabinv.model.entities.player.Player;
import it.unibo.crabinv.model.levels.Level;
import it.unibo.crabinv.model.levels.LevelFactory;
import it.unibo.crabinv.model.core.save.GameSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructor for the {@link GameEngine}.
 */
public class GameEngineImpl implements GameEngine {

    private static final double PLAYER_SPRITE_MULT = 0.08;
    private static final double PLAYER_HALF_SIZE_NORM = PLAYER_SPRITE_MULT / 2.0;
    private static final double WORLD_MIN_X = PLAYER_HALF_SIZE_NORM;
    private static final double WORLD_MAX_X = 1.0 - PLAYER_HALF_SIZE_NORM;
    private static final double PLAYER_START_X = 0.5;
    private static final double PLAYER_FIXED_Y = 0.90;
    private static final double ENTITY_SPRITE_BULLET_SPAWN = 0.05;
    private static final double PLAYER_RADIUS = 0.01;
    private final List<Bullet> activeBullets = new ArrayList<>();
    private final BulletFactory playerBulletFactory = new PlayerBulletFactory();
    private final BulletFactory enemyBulletFactory = new EnemyBulletFactory();
    private GameSession gameSession;
    private int currentLevel;
    private LevelFactory levelFactory;
    private Level level;
    private Player player;
    private GameEngineState gameEngineState;
    private CollisionController collisionController;
    private EnemyFactory enemyFactory;
    private RewardsService rewardsService;

    /**
     * Starts the {@link GameEngine} creating an empty instance.
     * Uses the {@code init()} method to initialize the {@link GameEngine}
     *
     * @see #init(GameSession, LevelFactory, EnemyFactory, RewardsService, CollisionController)
     */
    public GameEngineImpl() {
        //Empty Constructor.
    }

    /**
     * Initializes the parameters of the {@link GameEngine}.
     *
     * @param gameSessionParam         the {@link GameSession} from which the {@link GameEngine} will be initialized.
     * @param levelFactoryParam        the {@link LevelFactory} used by the {@link GameEngine}.
     * @param enemyFactoryParam        the {@link EnemyFactory} used by the {@link GameEngine}.
     * @param rewardsServiceParam      the {@link RewardsService} used by the {@link GameEngine}.
     * @param collisionControllerParam the {@link CollisionController} used by the {@link GameEngine}.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    @Override
    public final void init(final GameSession gameSessionParam,
                           final LevelFactory levelFactoryParam,
                           final EnemyFactory enemyFactoryParam,
                           final RewardsService rewardsServiceParam,
                           final CollisionController collisionControllerParam) {
        this.gameSession = gameSessionParam;
        this.levelFactory = levelFactoryParam;
        this.currentLevel = gameSessionParam.getCurrentLevel();
        this.collisionController = collisionControllerParam;
        this.enemyFactory = enemyFactoryParam;
        this.rewardsService = rewardsServiceParam;
        this.level = createLevel();
        this.player = Player.builder()
                .x(PLAYER_START_X)
                .y(PLAYER_FIXED_Y)
                .maxHealth(this.gameSession.getPlayerHealth())
                .health(this.gameSession.getPlayerHealth())
                .collisionGroup(CollisionGroups.FRIENDLY)
                .radius(PLAYER_RADIUS)
                .speed(this.gameSession.getPlayerSpeed())
                .fireRate(this.gameSession.getPlayerFireRate())
                .shootingCounter(0)
                .minBound(this.getWorldMinX())
                .maxBound(this.getWorldMaxX())
                .sprite(EntitySprites.PLAYER)
                .build();
        this.gameEngineState = GameEngineState.RUNNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void tick() {
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
            }
            case PAUSED, GAME_OVER, WIN -> {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameSnapshot snapshot() {
        checkGameStarted();
        return createSnapshot(populateSnapshot());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameEngineState getGameState() {
        return this.gameEngineState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void gameOver() {
        this.gameEngineState = GameEngineState.GAME_OVER;
        this.gameSession.markGameOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void winGame() {
        this.gameEngineState = GameEngineState.WIN;
        this.gameSession.markGameWon();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void pauseGame() {
        if (this.gameEngineState == GameEngineState.RUNNING) {
            this.gameEngineState = GameEngineState.PAUSED;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resumeGame() {
        if (this.gameEngineState == GameEngineState.PAUSED) {
            this.gameEngineState = GameEngineState.RUNNING;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE")//exposes internal representation by design
    @Override
    public final Player getPlayer() {
        if (this.player == null) {
            throw new IllegalStateException("call newGame() before getPlayer()");
        }
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getWorldMinX() {
        return WORLD_MIN_X;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final double getWorldMaxX() {
        return WORLD_MAX_X;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Enemy> getEnemyList() {
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
     * Checks if the current level is over and advances to the next one.
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
     * Creates a new level using the {@link LevelFactory}.
     *
     * @return the newly created Level.
     */
    private Level createLevel() {
        this.level = levelFactory.createLevel(this.currentLevel, this.enemyFactory, this.rewardsService);
        return this.level;
    }

    /**
     * Checks if an enemy reaches the Y of the player, destroys the enemy and subtracts player health.
     */
    private void enemyToGroundCheck() {
        final double eps = 0.01;
        final List<Enemy> enemyList = this.level.getCurrentWave().getAliveEnemies();
        for (final Enemy enemy : enemyList) {
            if (Math.abs(enemy.getY() - this.player.getY()) < eps) {
                this.gameSession.subPlayerHealth(1);
                enemy.destroy();
            }
        }
    }

    /**
     * Checks game over conditions, calls game over procedures if needed.
     */
    private void checkGameOver() {
        if (this.gameSession.getPlayerHealth() <= 0
                && this.gameEngineState != GameEngineState.GAME_OVER) {
            gameOver();
        }
    }

    /**
     * Checks win conditions, calls win procedures if needed.
     */
    private void checkWin() {
        if (this.getEnemyList().isEmpty()
                && this.level.getCurrentWave().isWaveFinished()
                && this.gameSession.getPlayerHealth() > 0) {
            winGame();
        }
    }

    private void bulletsUpdate() {
        for (final Bullet b : activeBullets) {
            if (b.getCollisionGroup() == CollisionGroups.FRIENDLY) {
                b.move(Delta.DECREASE);
            } else {
                b.move(Delta.INCREASE);
            }
        }
        activeBullets.removeIf(b -> b.getY() < 0 || b.getY() > 1.0 || !b.isAlive());
    }

    private void collisionUpdate() {
        if (this.collisionController == null) {
            return;
        }
        final int healthBefore = player.getHealth();

        final List<Entity> allEntities = new ArrayList<>();
        allEntities.add(player);
        if (level.getCurrentWave() != null) {
            allEntities.addAll(level.getCurrentWave().getAliveEnemies());
        }
        allEntities.addAll(activeBullets);
        collisionController.resolve(allEntities);

        final int damageTaken = healthBefore - player.getHealth();

        if (damageTaken > 0) {
            this.gameSession.subPlayerHealth(damageTaken);
        }
    }

    @Override
    public final void spawnPlayerBullet() {
        final double bulletX = this.player.getX();
        final double bulletY = this.player.getY() - ENTITY_SPRITE_BULLET_SPAWN;
        activeBullets.add(playerBulletFactory.createBullet(bulletX, bulletY,
                0.0,
                1.0
        ));
    }

    @Override
    public final void spawnEnemyBullet(final Enemy enemy) {
        this.activeBullets.add(enemyBulletFactory.createBullet(enemy.getX(),
                enemy.getY() + ENTITY_SPRITE_BULLET_SPAWN,
                0.0,
                1.0
        ));
    }

    private void populateBullets(final List<RenderObjectSnapshot> renderObjects) {
        for (final Bullet b : activeBullets) {
            renderObjects.add(new RenderObjectSnapshot(b.getSprite(), b.getX(), b.getY()));
        }
    }

    /**
     * Checks if there is an active {@link GameSession}.
     */
    private void checkGameStarted() {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
    }

    /**
     * Creates a {@link List<RenderObjectSnapshot>} of all game entities.
     *
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
     * Creates a {@link GameSnapshot} from a {@link List<RenderObjectSnapshot>}, used by the {@link GameEngine}.
     *
     * @param renderObjects the {@link List<RenderObjectSnapshot>}
     * @return the {@link GameSnapshot}
     */
    private GameSnapshot createSnapshot(final List<RenderObjectSnapshot> renderObjects) {
        if (this.gameSession == null) {
            throw new IllegalStateException("newGame() needed before snapshot()");
        }
        return new GameSnapshot(List.copyOf(renderObjects), this.gameSession);
    }

    /**
     * Adds the player entity to the {@link List<RenderObjectSnapshot>} of entities to be rendered.
     *
     * @param renderObjects the {@link List<RenderObjectSnapshot>} of entities to be rendered
     */
    private void populatePlayer(final List<RenderObjectSnapshot> renderObjects) {
        renderObjects.add(new RenderObjectSnapshot(player.getSprite(), player.getX(), player.getY()));
    }

    /**
     * Adds the enemy entities to the {@link List<RenderObjectSnapshot>} of entities to be rendered.
     *
     * @param renderObjects the {@link List<RenderObjectSnapshot>} of entities to be rendered
     */
    private void populateEnemies(final List<RenderObjectSnapshot> renderObjects) {
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
