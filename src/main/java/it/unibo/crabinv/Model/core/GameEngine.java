package it.unibo.crabinv.Model.core;

import java.util.logging.Logger;

public abstract class GameEngine {

    private long period = 20; //20 ms, 50 fps
    private Logger logger = Logger.getLogger("GameEngine");

    public GameEngine(){

    }

    public void setup(){

    }

    public void mainLoop(){
        long previousCycleStartTime = System.currentTimeMillis();
        while(true){
            long currentCycleStartTime = System.currentTimeMillis();
            long elapsed = currentCycleStartTime - previousCycleStartTime;
            processInput();
            /* move on the game state of elapsed time */
            updateGame(elapsed);
            render();
            waitForNextFrame(currentCycleStartTime);
            previousCycleStartTime = currentCycleStartTime;
        }
    }

    protected void processInput(){

    }

    protected void updateGame(long elapsed){

    }

    protected void render(){

    }

    protected void waitForNextFrame(long cycleStartTime){

    }
}
