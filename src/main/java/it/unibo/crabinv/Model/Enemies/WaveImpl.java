package it.unibo.crabinv.Model.Enemies;

import it.unibo.crabinv.Controller.entity.EntityAbstractController;
import it.unibo.crabinv.Model.entity.Delta;
import it.unibo.crabinv.Model.entity.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaveImpl extends EntityAbstractController implements Wave {

    private static final double DEFAULT_TOP_MARGIN_Y_MULT = 0.05;
    private static final double DEFAULT_SIDE_MARGIN_X_MULT = 0.05;
    private static final double DEFAULT_SPACING_X_MULT = 0.08;

    private final List<EnemyType> enemyTypes;
    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private final RewardsService rewardsService;

    private final double spawnY; //is also the upper bound of Y
    private final double spawnX;
    private final double spawnXSpacing;
    private final double bottomY;
    private boolean spawned;

    /**
     * Constructor with calculated default position parameters
     * To be used as the default constructor for wave creation
     *
     * @param enemyTypes     the composition of the wave
     * @param enemyFactory   the factory used to create enemies
     * @param rewardsService the reward handler
     * @param worldWidth     the width of the world, used for calculating positions
     * @param worldHeight    the height of the world, used for calculating positions
     */
    public WaveImpl(final List<EnemyType> enemyTypes,
                    final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    double worldWidth,
                    double worldHeight
    ) {
        this(enemyTypes,
                enemyFactory,
                rewardsService,
                requirePositive("worldHeight", worldHeight) * DEFAULT_TOP_MARGIN_Y_MULT,
                requirePositive("worldWidth", worldWidth) * DEFAULT_SIDE_MARGIN_X_MULT,
                requirePositive("worldWidth", worldWidth) * DEFAULT_SPACING_X_MULT,
                requirePositive("worldHeight", worldHeight)
        );
    }

    /**
     * Constructor with ALL parameters
     *
     * @param enemyTypes     the composition of the wave
     * @param enemyFactory   the factory used to create enemies
     * @param rewardsService the reward handler
     * @param spawnY         Y position of enemy spawn and upper bound of enemy movement
     * @param spawnX         X position of first enemy spawn
     * @param spawnXSpacing  X spacing between enemies
     * @param bottomY        Y lower bound of enemy movement
     */
    public WaveImpl(final List<EnemyType> enemyTypes,
                    final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    final double spawnY,
                    final double spawnX,
                    final double spawnXSpacing,
                    final double bottomY
    ) {
        this.enemyTypes = List.copyOf(Objects.requireNonNull(enemyTypes, "enemyTypes cannot be null"));
        this.enemyFactory = Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        this.rewardsService = Objects.requireNonNull(rewardsService, "rewardsService cannot be null");

        if (spawnXSpacing <= 0) {
            throw new IllegalArgumentException("invalid spawnXSpacing, must be > 0");
        }
        if (bottomY < spawnY) {
            throw new IllegalArgumentException("spawnY (the y upper bound) cannot be lower than bottomY");
        }

        this.spawnY = spawnY;
        this.spawnX = spawnX;
        this.spawnXSpacing = spawnXSpacing;
        this.bottomY = bottomY;
        this.spawned = false;
    }

    /**
     * Helper method for Constructor refuses negative values
     * @param name the name of the parameter to be checked, needed for debugging
     * @param value the parameter to be checked
     * @return the validated parameter
     */
    private static double requirePositive(final String name, final double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be > 0: " + value);
        }
        return value;
    }

    /**
     *
     */
    private void spawnIfNeeded() { //Adapted from MoseBarbieri
        if (!this.spawned) {
            double x = spawnX;
            for (EnemyType type : enemyTypes) {
                activeEnemies.add(enemyFactory.createEnemy(type, x, spawnY));
                x += spawnXSpacing;
            }
            this.spawned = true;
        }
    }

    /**
     *
     */
    private void updateMovement() {
        update(Delta.INCREASE);
    }

    /**
     *
     */
    private void cleanUpAndRewards() { //Adapted from MoseBarbieri
        activeEnemies.removeIf(enemy -> {
            if (!enemy.isAlive()) {
                rewardsService.rewardEnemyDeath(enemy);
                return true;
            }
            return false;
        });
    }

    /* {@inheritDoc} */
    @Override
    public void tickLogicUpdate() {
        spawnIfNeeded();
        updateMovement();
        cleanUpAndRewards();
    }

    /* {@inheritDoc} */
    @Override
    public List<Enemy> getAliveEnemies() {
        return List.copyOf(activeEnemies);
    }

    /* {@inheritDoc} */
    @Override
    public boolean isWaveFinished() {
        return spawned && activeEnemies.isEmpty();
    }

    /* {@inheritDoc} */
    @Override
    public void update(Delta delta) {
        for (Enemy enemy : activeEnemies) {
            if (enemy instanceof Movable movableEnemy) {
                movableEnemy.move(delta, spawnY, bottomY);
            }
        }
    }

}
