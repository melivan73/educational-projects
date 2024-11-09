public class RAM {
    private final RAMType ramType;
    private final int ramSize; // Gb
    private final double ramWeight; // g

    public RAM(RAMType ramType, int ramSize, double ramWeight) {
        this.ramType = ramType;
        this.ramSize = ramSize;
        this.ramWeight = ramWeight;
    }

    public RAMType getRamType() {
        return ramType;
    }

    public int getRamSize() {
        return ramSize;
    }

    public double getRamWeight() {
        return ramWeight;
    }

    public enum RAMType {
        DDR4, DDR5, DDR6, SRAM;
    }
}
