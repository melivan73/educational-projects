public class Display {
    private final double displaySize; // diagonal in inches
    private final DisplayType displayType;
    private final double displayWeight; // g

    public Display(double displaySize, DisplayType displayType, double displayWeight) {
        this.displaySize = displaySize;
        this.displayType = displayType;
        this.displayWeight = displayWeight;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public double getDisplayWeight() {
        return displayWeight;
    }

    public enum DisplayType {
        IPS, TN, VA;
    }
}
