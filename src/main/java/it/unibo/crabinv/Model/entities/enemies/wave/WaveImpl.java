package it.unibo.crabinv.Model.entities.enemies.wave;

import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.enemies.EnemyFactory;
import it.unibo.crabinv.Model.entities.enemies.EnemyType;
import it.unibo.crabinv.Model.entities.enemies.rewardService.RewardsService;
import it.unibo.crabinv.Model.entities.entity.Delta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaveImpl implements Wave {

    private static final double DEFAULT_TOP_MARGIN_Y_MULT = 0.05;

    private final List<EnemyType> enemy;
    private final List<Integer> spawnSlots;
    private final int maxSpawnSlots;

    private final List<Enemy> activeEnemies = new ArrayList<>();
    private final EnemyFactory enemyFactory;
    private final RewardsService rewardsService;

    private final double spawnYNorm; //is also the upper bound of Y
    private final double bottomYNorm;
    private boolean spawned;

    /**
     * LITE Constructor for preset creation
     * @param enemies the composition of the wave
     * @param spawnSlots the slots for the enemies
     * @param enemyFactory the factory used to create enemies
     * @param rewardsService the reward handler
     * @param maxSpawnSlots the number of available slots for spawn
     */
    public WaveImpl(final List<EnemyType> enemies,
                    final List<Integer> spawnSlots,
                    final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    final int maxSpawnSlots) {
        this(
                enemies,
                spawnSlots,
                enemyFactory,
                rewardsService,
                maxSpawnSlots,
                DEFAULT_TOP_MARGIN_Y_MULT,
                1.0);
    }

    /**
     * FULL Constructor with all parameters
     * @param enemies the composition of the wave
     * @param spawnSlots the slots for the enemies
     * @param enemyFactory the factory used to create enemies
     * @param rewardsService the reward handler
     * @param maxSpawnSlots the number of available slots for spawn
     * @param spawnYNorm the spawn position (and upper bound)
     * @param bottomYNorm the lower bound
     */
    public WaveImpl(final List<EnemyType> enemies,
                    final List<Integer> spawnSlots,
                    final EnemyFactory enemyFactory,
                    final RewardsService rewardsService,
                    final int maxSpawnSlots,
                    final double spawnYNorm,
                    final double bottomYNorm
    ) {
        this.enemy = List.copyOf(Objects.requireNonNull(enemies, "enemies cannot be null"));
        this.spawnSlots = List.copyOf(Objects.requireNonNull(spawnSlots, "spawnSlots cannot be null"));
        this.enemyFactory = Objects.requireNonNull(enemyFactory, "enemyFactory cannot be null");
        this.rewardsService = Objects.requireNonNull(rewardsService, "rewardsService cannot be null");

        if (this.enemy.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("enemies cannot contain null elements");
        }
        if (this.spawnSlots.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("spawnSlots cannot contain null elements");
        }
        if (this.enemy.size() != this.spawnSlots.size()) {
            throw new IllegalArgumentException("enemies and spawnSlots must have the same size");
        }
        if (maxSpawnSlots <= 0) {
            throw new IllegalArgumentException("maxSpawnSlots must be > 0");
        }
        if (this.spawnSlots.stream().anyMatch(s -> s < 0 || s >= maxSpawnSlots)) {
            throw new IllegalArgumentException("spawnSlots must be in range [0.." + (maxSpawnSlots - 1) + "]");
        }
        if (spawnYNorm < 0.0 || spawnYNorm > 1.0 || bottomYNorm < 0.0 || bottomYNorm > 1.0) {
            throw new IllegalArgumentException("spawnYNorm/bottomYNorm must be in [0..1]");
        }
        if (bottomYNorm < spawnYNorm) {
            throw new IllegalArgumentException("bottomYNorm must be >= spawnYNorm");
        }

        this.maxSpawnSlots = maxSpawnSlots;
        this.spawnYNorm = spawnYNorm;
        this.bottomYNorm = bottomYNorm;
        this.spawned = false;
    }

    /**
     *
     */
    private void spawnIfNeeded() {//Adapted from MoseBarbieri
        if (!this.spawned) {
            for (int i = 0; i < enemy.size(); i++) {
                final int slot = spawnSlots.get(i);
                final double xNorm = (slot + 0.5) / this.maxSpawnSlots;
                final EnemyType enemyType = enemy.get(i);
                activeEnemies.add(enemyFactory.createEnemy(enemyType, xNorm, spawnYNorm, 1, 1));
            }
            this.spawned = true;
        }
    }


    /**
     * Advances the logic of movement of the wave's enemies
     */
    private void updateMovement() {
        for (Enemy enemy : activeEnemies) {
            enemy.move(Delta.DECREASE);
            System.out.println("x del nemico:"+enemy.getX());
            System.out.println("y del nemico: "+ enemy.getY());
            enemy.tick();
        }
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

    @Override
    public int getMaxSpawnSlots() {
        return this.maxSpawnSlots;
    }


}
