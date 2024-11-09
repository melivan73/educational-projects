public class Keyboard {
    private final KeyboardType keyboardType; // diagonal in inches
    private final boolean hasBacklight;
    private final double keyboardWeight; // g

    public Keyboard(KeyboardType keyboardType, boolean hasBacklight, double keyboardWeight) {
        this.keyboardType = keyboardType;
        this.hasBacklight = hasBacklight;
        this.keyboardWeight = keyboardWeight;
    }

    public KeyboardType getKeyboardType() {
        return keyboardType;
    }

    public boolean isHasBacklight() {
        return hasBacklight;
    }

    public double getKeyboardWeight() {
        return keyboardWeight;
    }

    public enum KeyboardType {
        MEMBRANE, OPTICAL, MECHANICAL, SCISSOR
    }
}
