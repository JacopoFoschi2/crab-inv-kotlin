package it.unibo.crabinv.Model.entities.enemies;

public enum EnemyType {
    SERVANT ("/enemies/crab_enemy.png", 10);

    private final String imagePath;
    private final int rewardForKill;

    EnemyType(String imagePath, int rewardForKill) {
        this.imagePath = imagePath;
        this.rewardForKill = rewardForKill;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getRewardForKill() {
        return rewardForKill;
    }

}