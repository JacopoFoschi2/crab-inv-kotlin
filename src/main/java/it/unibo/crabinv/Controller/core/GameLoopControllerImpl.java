package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.core.audio.AudioController;
import it.unibo.crabinv.Controller.entities.enemy.EnemyController;
import it.unibo.crabinv.Controller.entities.player.PlayerController;
import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineState;
import it.unibo.crabinv.Model.core.GameSnapshot;
import it.unibo.crabinv.Model.entities.enemies.Enemy;
import it.unibo.crabinv.Model.entities.entity.Delta;
import it.unibo.crabinv.Model.input.InputSnapshot;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class GameLoopControllerImpl implements GameLoopController {

    private static final long STANDARD_TICK_MILLIS = 16;
    private static final int STANDARD_MAX_TICKS_PER_FRAME = 5;

    private final long tickDurationMillis;
    private final int maxTicksPerFrame;
    private final GameEngine gameEngine;
    private final InputController inputController;
    private final PlayerController playerController;
    private final AudioController audioController;
    private long accumulatedMillis;
    private long totalElapsedTicks;
    private GameSnapshot latestSnapshot;
    private Map<Enemy, EnemyController> enemyControllerMap;

    public GameLoopControllerImpl(GameEngine gameEngine,
                                  InputController inputController,
                                  PlayerController playerController,
                                  AudioController audioController) {

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
    public GameSnapshot step(long frameElapsedMillis) {
        checkPause();
        checkResume();
        if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
            accumulateTime(frameElapsedMillis);
            final int nextStepTicks = calculateTicks();
            executeTicks(nextStepTicks);
            updateSnapshot(nextStepTicks);
        }
        return latestSnapshot;
    }

    @Override
    public long getTickDurationMillis() {
        return tickDurationMillis;
    }

    @Override
    public long getAccumulatedMillis() {
        return accumulatedMillis;
    }

    @Override
    public long getTotalElapsedTicks() {
        return totalElapsedTicks;
    }

    @Override
    public GameSnapshot getLatestSnapshot() {
        return this.latestSnapshot;
    }

    @Override
    public void pause() {
        this.gameEngine.pauseGame();
    }

    @Override
    public void resume() {
        this.gameEngine.resumeGame();
    }

    /**
     * Controls if the game is in the correct state to be resumed
     */
    private void checkResume() {
        if (this.gameEngine.getGameState() == GameEngineState.PAUSED) {
            if (inputController.getInputState().isUnpause()) {
                resume();
            }
        }
    }

    /**
     * Controls if the game is in the correct state to be paused
     */
    private void checkPause() {
        if (inputController.getInputState().isPause()) {
            if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
                pause();
            }
        }
    }


    /**
     * Adds the milliseconds of the last frame to the accumulated milliseconds
     * @param frameElapsedMillis the milliseconds to add
     */
    private void accumulateTime(long frameElapsedMillis) {
        this.accumulatedMillis += frameElapsedMillis;
    }

    /**
     * Calculates (with capping and rounding) the ticks of the next frame
     * @return the ticks
     */
    private int calculateTicks() {
        final long ticks = this.accumulatedMillis / this.tickDurationMillis;
        final long cappedTicks = Math.min(ticks, maxTicksPerFrame);
        return (int) cappedTicks;
    }

    /**
     * Requests the GameEngine to calculate the game logic for the number of ticks
     * @param nextStepTicks the ticks the Game Engine must calculate
     */
    private void executeTicks(int nextStepTicks) {
        for (int i = 0; i < nextStepTicks; i++) {
            playerUpdate();
            enemyUpdate();
            tickUpdate();
        }
    }

    /**
     * Updates the Player input data
     */
    private void playerUpdate() {
        final InputSnapshot inputSnapshot = inputController.getInputState();
        this.playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta());
    }

    /**
     * Updates the Active enemies data
     */
    private void enemyUpdate() {
        List<Enemy> enemyList = this.gameEngine.getEnemyList();
        for (Enemy enemy : enemyList) {
            enemyControllerMap.computeIfAbsent(enemy, e -> new EnemyController(e, this.audioController));
        }
        for (Enemy enemy : enemyList) {
            EnemyController enemyController = enemyControllerMap.get(enemy);
            if (enemyController != null) {
                enemyController.update(Delta.INCREASE);
            }
        }
        enemyControllerMap.keySet().removeIf(e -> !e.isAlive() || !enemyList.contains(e));
    }

    /**
     * Calls the {@link GameEngine} to execute the ticks
     */
    private void tickUpdate() {
        this.gameEngine.tick();
        totalElapsedTicks++;
    }

    /**
     * Calls the {@link GameEngine} to update its snapshot
     * @param nextStepTicks
     */
    private void updateSnapshot(int nextStepTicks) {
        this.accumulatedMillis -= nextStepTicks * this.tickDurationMillis;
        this.latestSnapshot = this.gameEngine.snapshot();
    }

}
