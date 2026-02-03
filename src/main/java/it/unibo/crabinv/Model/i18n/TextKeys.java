package it.unibo.crabinv.Model.i18n;

/**
 * Provides all currently supported keys of localization.
 * These keys are to be used with {@code getKey()} method of {@link Localization} class.
 */
public enum TextKeys {
    PLAY("play"),
    SHOP("shop"),
    RUN_LOG("run_log"),
    SETTINGS("settings"),
    RETURN("return"),
    EXIT_GAME("exit_game"),
    BGM_VOLUME("bgm_volume"),
    SFX_VOLUME("sfx_volume"),
    BGM_MUTE("bgm_mute"),
    SFX_MUTE("sfx_mute"),
    LANGUAGE("language"),
    BUY("buy"),
    COST("cost"),
    CURRENCY("currency"),
    RETURN_TO_MENU("return_to_menu"),
    ARE_YOU_SURE("are_you_sure"),
    YES("yes"),
    NO("no"),
    CURRENCY("currency"),
    SPEED_UP ("Speed Up"),
    FIRERATE_UP("Firerate Up"),
    HEALTH_UP("Health Up"),
    ;
    private final String key;

    TextKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}