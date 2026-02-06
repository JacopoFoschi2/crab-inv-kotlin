package it.unibo.crabinv.Model.entities.bullets;

public enum BulletSprites {
    PLAYER("/bullets/bullet_player.png"),
    ENEMY("/bullets/bullet_enemy.png");

    private final String imagePath;

    BulletSprites(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
