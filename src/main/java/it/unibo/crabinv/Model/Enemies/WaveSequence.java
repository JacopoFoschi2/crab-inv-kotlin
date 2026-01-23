package it.unibo.crabinv.Model.Enemies;

import java.util.List;

public class WaveSequence implements WaveProvider{

    private List<Wave> waveList;
    private int currentWaveIndex = 0;

    @Override
    public Wave getNextWave() {
       Wave nexWave = this.waveList.get(currentWaveIndex);
       this.currentWaveIndex++;
       return nexWave;

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
