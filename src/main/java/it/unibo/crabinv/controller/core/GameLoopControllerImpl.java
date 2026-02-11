package it.unibo.crabinv.controller.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.crabinv.controller.core.audio.AudioController;
import it.unibo.crabinv.controller.entities.enemy.EnemyController;
import it.unibo.crabinv.controller.entities.player.PlayerController;
import it.unibo.crabinv.controller.input.InputController;
import it.unibo.crabinv.model.core.GameEngine;
import it.unibo.crabinv.model.core.GameEngineState;
import it.unibo.crabinv.model.core.GameSnapshot;
import it.unibo.crabinv.model.entities.enemies.Enemy;
import it.unibo.crabinv.model.entities.entity.Delta;
import it.unibo.crabinv.model.input.InputSnapshot;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link GameLoopController}.
 */
public class GameLoopControllerImpl implements GameLoopController {

    private static final long STANDARD_TICK_MILLIS = 16;
    private static final int STANDARD_MAX_TICKS_PER_FRAME = 5;

    private final long tickDurationMillis;
    private final int maxTicksPerFrame;
    private final GameEngine gameEngine;
    private final InputController inputController;
    private final PlayerController playerController;
    private final AudioController audioController;
    private final Map<Enemy, EnemyController> enemyControllerMap;
    private long accumulatedMillis;
    private long totalElapsedTicks;
    private GameSnapshot latestSnapshot;

    /**
     * Constructor for the {@link GameLoopControllerImpl}.
     *
     * @param gameEngine       the {@link GameEngine} used by the {@link GameLoopControllerImpl}
     * @param inputController  the {@link GameEngine} used by the {@link GameLoopControllerImpl}
     * @param playerController the {@link GameEngine} used by the {@link GameLoopControllerImpl}
     * @param audioController  the {@link GameEngine} used by the {@link GameLoopControllerImpl}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2") //dependencies are injected and owned by caller
    public GameLoopControllerImpl(final GameEngine gameEngine,
                                  final InputController inputController,
                                  final PlayerController playerController,
                                  final AudioController audioController) {

        this.tickDurationMillis = STANDARD_TICK_MILLIS;
        this.maxTicksPerFrame = STANDARD_MAX_TICKS_PER_FRAME;
        this.accumulatedMillis = 0;
        this.totalElapsedTicks = 0;
        this.gameEngine = gameEngine;
        this.inputController = inputController;
        this.playerController = playerController;
        this.audioController = audioController;
        this.enemyControllerMap = new IdentityHashMap<>();
        tickUpdate();
        this.latestSnapshot = this.gameEngine.snapshot();
    }

    @Override
    public final GameSnapshot step(final long frameElapsedMillis) {
        checkPause();
        checkResume();
        if (this.gameEngine.getGameState() == GameEngineState.GAME_OVER) {
            return latestSnapshot;
        }

        if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
            accumulateTime(frameElapsedMillis);
            final int nextStepTicks = calculateTicks();
            executeTicks(nextStepTicks);
            if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
                updateSnapshot(nextStepTicks);
            }
        }
        return latestSnapshot;
    }

    @Override
    public final long getTickDurationMillis() {
        return tickDurationMillis;
    }

    @Override
    public final long getAccumulatedMillis() {
        return accumulatedMillis;
    }

    @Override
    public final long getTotalElapsedTicks() {
        return totalElapsedTicks;
    }

    @Override
    public final GameSnapshot getLatestSnapshot() {
        return this.latestSnapshot;
    }

    @Override
    public final void pause() {
        this.gameEngine.pauseGame();
    }

    @Override
    public final void resume() {
        this.gameEngine.resumeGame();
    }

    /**
     * Controls if the game is in the correct state to be resumed.
     */
    private void checkResume() {
        if (this.gameEngine.getGameState() == GameEngineState.PAUSED
                && inputController.getInputState().isUnpause()) {
                resume();
        }
    }

    /**
     * Controls if the game is in the correct state to be paused.
     */
    private void checkPause() {
        if (inputController.getInputState().isPause()
                && this.gameEngine.getGameState() == GameEngineState.RUNNING) {
                pause();
        }
    }

    /**
     * Adds the milliseconds of the last frame to the accumulated milliseconds.
     *
     * @param frameElapsedMillis the milliseconds to add.
     */
    private void accumulateTime(final long frameElapsedMillis) {
        this.accumulatedMillis += frameElapsedMillis;
    }

    private int calculateTicks() {
        final long ticks = this.accumulatedMillis / this.tickDurationMillis;
        final long cappedTicks = Math.min(ticks, maxTicksPerFrame);
        return (int) cappedTicks;
    }

    /**
     * Requests the GameEngine to calculate the game logic for the number of ticks.
     *
     * @param nextStepTicks the ticks the Game Engine must calculate.
     */
    private void executeTicks(final int nextStepTicks) {
        for (int i = 0; i < nextStepTicks; i++) {
            playerUpdate();
            enemyUpdate();
            tickUpdate();
        }
    }

    /**
     * Updates the Player input data.
     */
    private void playerUpdate() {
        final InputSnapshot inputSnapshot = inputController.getInputState();
        this.playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta());
    }

    /**
     * Updates the Active enemies' data.
     */
    private void enemyUpdate() {
        final List<Enemy> enemyList = this.gameEngine.getEnemyList();
        for (final Enemy enemy : enemyList) {
            enemyControllerMap.computeIfAbsent(enemy, e -> new EnemyController(e, this.audioController, gameEngine));
        }
        for (final Enemy enemy : enemyList) {
            final EnemyController enemyController = enemyControllerMap.get(enemy);
            if (enemyController != null) {
                enemyController.update(Delta.INCREASE);
            }
        }
        enemyControllerMap.keySet().removeIf(e -> !e.isAlive() || !enemyList.contains(e));
    }

    /**
     * Calls the {@link GameEngine} to execute the ticks.
     */
    private void tickUpdate() {
        this.gameEngine.tick();
        totalElapsedTicks++;
    }

    /**
     * Calls the {@link GameEngine} to update its snapshot.
     *
     * @param nextStepTicks the ticks for the next step.
     */
    private void updateSnapshot(final int nextStepTicks) {
        this.accumulatedMillis -= nextStepTicks * this.tickDurationMillis;
        this.latestSnapshot = this.gameEngine.snapshot();
    }
}
