package it.unibo.crabinv.Model.core.audio;

/**
 * Provides a set list of present BGM Tracks and their relative path in resources.
 */
public enum BGMTracks {
    LEVEL("/audio/BGM/main_bgm.mp3"),
    MENU("/audio/BGM/menu_bgm.mp3"),;

    private final String path;

    BGMTracks(final String path) {
        this.path = path;
    }

    /**
     * @return the resource path of the selected BGM
     */
    public String getPath() {
        return path;
    }
}
