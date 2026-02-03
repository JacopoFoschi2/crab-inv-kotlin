package it.unibo.crabinv.Controller.input;

public enum KeyCodeKeyboard {
    LEFT(37),
    RIGHT(39),
    UP(38),
    DOWN(40),
    SHOOT(32),
    PAUSE(27);


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
