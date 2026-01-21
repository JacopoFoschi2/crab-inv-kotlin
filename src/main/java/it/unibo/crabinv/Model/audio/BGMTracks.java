package it.unibo.crabinv.Model.audio;

public enum BGMTracks {
    LEVEL("/audio/BGM/main_bgm.mp3");

    private final String path;

    BGMTracks(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
