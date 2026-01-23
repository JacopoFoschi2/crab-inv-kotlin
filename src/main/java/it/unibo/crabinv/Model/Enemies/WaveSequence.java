package it.unibo.crabinv.Model.Enemies;

import java.util.List;

public class WaveSequence implements WaveProvider{

    private List<Wave> waveList;
    private int currentWaveIndex = 0;

    public WaveSequence(List<Wave> waveList){
        this.waveList = waveList;
    }

    @Override
    public Wave getNextWave() {
        if (hasMoreWaves()) {
            Wave nextWave = this.waveList.get(currentWaveIndex);
            this.currentWaveIndex++;
            return nextWave;
        }
        else{
            throw new IndexOutOfBoundsException("Out of bounds index:" + this.currentWaveIndex);
        }

    }

    @Override
    public boolean hasMoreWaves() {
        if(this.currentWaveIndex < this.waveList.size()){
            return true;
        }else {
            return false;
        }
    }
}
