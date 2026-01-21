package it.unibo.crabinv.i18n;

public enum TextKeys {
    PLAY("play");

    private final String key;

    TextKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}