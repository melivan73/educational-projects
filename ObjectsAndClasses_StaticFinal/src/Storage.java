public class Storage {
    private final StorageType storageType;
    private final int storageSize; //Gb
    private final double storageWeight; // g

    public Storage(StorageType storageType, int storageSize, double storageWeight) {
        this.storageType = storageType;
        this.storageSize = storageSize;
        this.storageWeight = storageWeight;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public double getStorageWeight() {
        return storageWeight;
    }

    public enum StorageType {
        HDD,SSD;
    }
}
