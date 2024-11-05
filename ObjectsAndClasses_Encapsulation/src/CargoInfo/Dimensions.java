package CargoInfo;

public class Dimensions {
    private final int length; // mm
    private final int width; // mm
    private final int height; // mm

    public Dimensions() {
        this.length = 0;
        this.width = 0;
        this.height = 0;
    }

    public Dimensions(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public Dimensions setLength(int length) {
        return new Dimensions(length, width, height);
    }

    public int getWidth() {
        return width;
    }

    public Dimensions setWidth(int width) {
        return new Dimensions(length, width, height);
    }

    public int getHeight() {
        return height;
    }

    public Dimensions setHeight(int height) {
        return new Dimensions(length, width, height);
    }

    // volume in meters ^ 3
    public double calcVolume() {
        return (double) (length * width * height) / 1000000000;
    }

    @Override
    public String toString() {
        return "\nHashCode = " + this.hashCode() +
            ". Длина = " + length + ". Ширина = " + width + ". Высота = " + height + ".";
    }
}