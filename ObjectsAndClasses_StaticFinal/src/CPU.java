public class CPU {
    private final double cpuFrequency; // GGz
    private final int cpuCoreNum;
    private final CPUManufacturer cpuManufacturer;
    private final double cpuWeight; // g
    private final String name;

    public CPU(double cpuFrequency, int cpuCoreNum, CPUManufacturer cpuManufacturer, double cpuWeight, String name) {
        this.cpuFrequency = cpuFrequency;
        this.cpuCoreNum = cpuCoreNum;
        this.cpuManufacturer = cpuManufacturer;
        this.cpuWeight = cpuWeight;
        this.name = name;
    }

    public double getCpuFrequency() {
        return cpuFrequency;
    }

    public int getCpuCoreNum() {
        return cpuCoreNum;
    }

    public CPUManufacturer getCpuManufacturer() {
        return cpuManufacturer;
    }

    public double getCpuWeight() {
        return cpuWeight;
    }

    public String getName() {
        return name;
    }

    public enum CPUManufacturer {
        AMD, INTEL;
    }
}