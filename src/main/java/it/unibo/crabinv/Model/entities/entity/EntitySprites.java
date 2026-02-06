package it.unibo.crabinv.Model.entities.entity;

public enum EntitySprites {
    PLAYER_BULLET("/bullets/bullet_player.png"),
    ENEMY_BULLET("/bullets/bullet_enemy.png"),
    PLAYER("/player/player_appearance.png"),
    ENEMY_SERVANT("/enemies/crab_enemy.png");

    private final String imagePath;

    EntitySprites(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
