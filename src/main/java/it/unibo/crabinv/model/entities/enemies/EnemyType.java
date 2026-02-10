package it.unibo.crabinv.model.entities.enemies;

public enum EnemyType {
    SERVANT (10);

    private final int rewardForKill;

    EnemyType( int rewardForKill) {
        this.rewardForKill = rewardForKill;
    }

    public int getRewardForKill() {
        return rewardForKill;
    }

}