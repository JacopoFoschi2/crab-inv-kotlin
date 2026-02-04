package it.unibo.crabinv.Model.audio;

/**
 * Provides a set list of present SFX Tracks and their relative path in resources
 */
public enum SFXTracks {
    MENU_OPEN("/audio/SFX/menu_open.mp3"),
    MENU_CLOSE("/audio/SFX/menu_close.mp3"),
    MENU_SELECT("/audio/SFX/menu_select.mp3"),
    MENU_HOVER("/audio/SFX/menu_hover.mp3"),
    SHOT_ENEMY("/audio/SFX/shot_enemy.mp3"),
    SHOT_PLAYER("/audio/SFX/shot_player.mp3"),
    EXPLOSION("/audio/SFX/explosion.mp3"),
    SLIDER("/audio/SFX/slider.mp3"),;

    private final String path;

    SFXTracks(String path) {
        this.path = path;
    }

    /**
     * @return the resource path of the selected SFX
     */
    public String getPath() {
        return path;
    }
}
