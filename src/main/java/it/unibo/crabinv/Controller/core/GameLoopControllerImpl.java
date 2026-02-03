package it.unibo.crabinv.Controller.core;

import it.unibo.crabinv.Controller.input.InputController;
import it.unibo.crabinv.Model.core.GameEngine;
import it.unibo.crabinv.Model.core.GameEngineState;
import it.unibo.crabinv.Model.core.GameSnapshot;

public class GameLoopControllerImpl implements GameLoopController{

    private final GameEngine gameEngine;
    private final InputController inputController;

    long STANDARD_TICK_MILLIS = 16;
    int STANDARD_MAX_TICKS_PER_FRAME = 5;
    private final long tickDurationMillis;
    private final int maxTicksPerFrame;
    private long accumulatedMillis;
    private long totalElapsedTicks;
    private GameSnapshot latestSnapshot;

    public GameLoopControllerImpl(GameEngine gameEngine, InputController inputController){
        if (gameEngine == null) {throw new NullPointerException("gameEngine cannot be null");}
        if (inputController == null) {throw new NullPointerException(("inputController cannot be null"));}
        this.gameEngine = gameEngine;
        this.inputController = inputController;
        this.tickDurationMillis = STANDARD_TICK_MILLIS;
        this.maxTicksPerFrame = STANDARD_MAX_TICKS_PER_FRAME;
        this.accumulatedMillis = 0;
        this.totalElapsedTicks = 0;
        this.gameEngine.newGame();
        this.latestSnapshot = this.gameEngine.snapshot();
    }

    /** {@inheritDoc} */
    @Override
    public long getTickDurationMillis() {
        return tickDurationMillis;
    }

    /** {@inheritDoc} */
    @Override
    public long getAccumulatedMillis() {
        return accumulatedMillis;
    }

    /** {@inheritDoc} */
    @Override
    public long getTotalElapsedTicks() {
        return totalElapsedTicks;
    }

    /** {@inheritDoc} */
    @Override
    public GameSnapshot step(long frameElapsedMillis) {
        if (gameEngine.getGameState() == GameEngineState.RUNNING) {
            this.accumulatedMillis += frameElapsedMillis;
            long ticksOfStep = accumulatedMillis / this.tickDurationMillis;
            if (ticksOfStep > maxTicksPerFrame) {
                ticksOfStep = maxTicksPerFrame;
            }
            for (long i = 0; i < ticksOfStep; i++) {
                this.gameEngine.tick(inputController.getInputState());
                totalElapsedTicks++;
            }
            accumulatedMillis -= ticksOfStep * tickDurationMillis;
        } else {
            this.accumulatedMillis = 0;
        }
        latestSnapshot = gameEngine.snapshot();
        return latestSnapshot;
    }

    /** {@inheritDoc} */
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
}
