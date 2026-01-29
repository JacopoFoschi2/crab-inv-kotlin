package it.unibo.crabinv.Model.Enemies;

public enum EnemyType {
    SERVANT ("/enemies/crab_enemy.png");

    private final String imagePath;

    EnemyType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

}

// TODO: modify based on type of enemies