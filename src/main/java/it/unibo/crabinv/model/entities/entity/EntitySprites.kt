package it.unibo.crabinv.model.entities.entity

/**
 * Provides the entities with the set sprites.
 */
enum class EntitySprites(
    /**
     * @return the imagePath of the sprite
     */
    val imagePath: String,
) {
    PLAYER_BULLET("/bullets/bullet_player.png"),
    ENEMY_BULLET("/bullets/bullet_enemy.png"),
    PLAYER("/player/player_appearance.png"),
    ENEMY_SERVANT("/enemies/crab_enemy.png"),
}
