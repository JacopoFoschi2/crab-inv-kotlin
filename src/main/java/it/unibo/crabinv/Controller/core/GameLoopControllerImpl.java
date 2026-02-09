package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.entities.player.PlayerController;
import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineState;
import it.unibo.crabinv.Model.core.GameSnapshot;
import it.unibo.crabinv.Model.input.InputSnapshot;

public class GameLoopControllerImpl implements GameLoopController {

    private static final long STANDARD_TICK_MILLIS = 16;
    private static final int STANDARD_MAX_TICKS_PER_FRAME = 5;

    private final long tickDurationMillis;
    private final int maxTicksPerFrame;
    private final GameEngine gameEngine;
    private final InputController inputController;
    private final PlayerController playerController;
    private long accumulatedMillis;
    private long totalElapsedTicks;
    private GameSnapshot latestSnapshot;

    public GameLoopControllerImpl(GameEngine gameEngine,
                                  InputController inputController,
                                  PlayerController playerController) {

        this.tickDurationMillis = STANDARD_TICK_MILLIS;
        this.maxTicksPerFrame = STANDARD_MAX_TICKS_PER_FRAME;
        this.accumulatedMillis = 0;
        this.totalElapsedTicks = 0;
        this.gameEngine = gameEngine;
        this.inputController = inputController;
        this.playerController = playerController;
        this.latestSnapshot = this.gameEngine.snapshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSnapshot step(long frameElapsedMillis) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTickDurationMillis() {
        return tickDurationMillis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getAccumulatedMillis() {
        return accumulatedMillis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalElapsedTicks() {
        return totalElapsedTicks;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public GameSnapshot getLatestSnapshot() {
        return this.latestSnapshot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.gameEngine.pauseGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.gameEngine.resumeGame();
    }

    private void checkResume() {
        if (this.gameEngine.getGameState() == GameEngineState.PAUSED) {
            if (inputController.getInputState().isUnpause()) {
                resume();
            }
        }
    }

    private void checkPause() {
        if (inputController.getInputState().isPause()) {
            if (this.gameEngine.getGameState() == GameEngineState.RUNNING) {
                pause();
            }
        }
    }

    private void accumulateTime(long frameElapsedMillis) {
        this.accumulatedMillis += frameElapsedMillis;
    }

    private int calculateTicks() {
        final long ticks = this.accumulatedMillis / this.tickDurationMillis;
        final long cappedTicks = Math.min(ticks, maxTicksPerFrame);
        return (int) cappedTicks;
    }

    private void executeTicks(int nextStepTicks) {
        for (int i = 0; i < nextStepTicks; i++) {
            if (this.gameEngine.getGameState() == GameEngineState.GAME_OVER) {
                break;
            }
            playerUpdate();
            tickUpdate();
        }
    }

    private void playerUpdate() {
        final InputSnapshot inputSnapshot = inputController.getInputState();
        this.playerController.update(inputSnapshot.isShooting(), inputSnapshot.getXMovementDelta());
    }

    private void enemyUpdate() {
        //TODO
    }

    private void tickUpdate() {
        this.gameEngine.tick();
        totalElapsedTicks++;
    }

    private void updateSnapshot(int nextStepTicks) {
        this.accumulatedMillis -= nextStepTicks * this.tickDurationMillis;
        this.latestSnapshot = this.gameEngine.snapshot();
    }

}
