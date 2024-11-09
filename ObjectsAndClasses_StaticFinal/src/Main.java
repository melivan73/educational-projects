public class Main {
    public static void main(String[] args) {

        // comp1
        String name = "BangTheory ver.2";
        String vendor = "Cooper Inc.";

        Computer btComputer = new Computer(vendor, name);

        CPU btCPU = new CPU(2.9, 6,
                CPU.CPUManufacturer.valueOf("INTEL"),
                8.0, "i5_10400F");
        btComputer.setCpu(btCPU);
        RAM btRAM = new RAM(RAM.RAMType.DDR4, 16, 16);
        btComputer.setRam(btRAM);
        Storage btStorage = new Storage(Storage.StorageType.SSD, 840, 90);
        btComputer.setStorage(btStorage);
        Display btDisplay = new Display(15.2, Display.DisplayType.IPS, 2000);
        btComputer.setDisplay(btDisplay);
        Keyboard btKeyboard = new Keyboard(Keyboard.KeyboardType.MEMBRANE, true, 145);
        btComputer.setKeyboard(btKeyboard);
        System.out.println(btComputer);
        System.out.println("");

        // comp2
        name = "DynamoS12";
        vendor = "PowaDelivery";

        Computer plComputer = new Computer(vendor, name);

        CPU plCPU = new CPU(3.4, 8,
                CPU.CPUManufacturer.valueOf("INTEL"),
                8.0, "i7_10400F");
        plComputer.setCpu(plCPU);

        RAM plRAM = new RAM(RAM.RAMType.DDR5, 32, 28);
        plComputer.setRam(plRAM);

        Storage plStorage = new Storage(Storage.StorageType.SSD, 1024,128);
        plComputer.setStorage(plStorage);

        Display plDisplay = new Display(24.0, Display.DisplayType.TN, 2200);
        plComputer.setDisplay(plDisplay);

        Keyboard plKeyboard = new Keyboard(Keyboard.KeyboardType.SCISSOR, false, 168);
        plComputer.setKeyboard(plKeyboard);
        System.out.println(plComputer);
    }
}