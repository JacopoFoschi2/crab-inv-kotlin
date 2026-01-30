package it.unibo.crabinv.Controller.input;

public enum KeyCodeKeyboard {
    LEFT(65),
    RIGHT(68),
    UP(87),
    DOWN(83),
    SHOOT(32);

    private final int keyCode;

    KeyCodeKeyboard(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode(){
        return keyCode;
    }

    public static KeyCodeKeyboard findKeyCode(int code){
        for (KeyCodeKeyboard k : values()) {
            if (k.keyCode == code){
                return k;
            }
        }
        return null;
    }
}
