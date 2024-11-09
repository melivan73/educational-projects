public class Computer {
    CPU cpu;
    RAM ram;
    Storage storage;
    Display display;
    Keyboard keyboard;

    private final String vendor;
    private final String name;

    public String getVendor() {
        return vendor;
    }

    public String getName() {
        return name;
    }

    public Computer(String vendor, String name) {
        this.vendor = vendor;
        this.name = name;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    double getTotalWeight(){
        return cpu.getCpuWeight() + ram.getRamWeight() + storage.getStorageWeight() +
                display.getDisplayWeight() + keyboard.getKeyboardWeight();
    }

    @Override
    public String toString() {
        return "Компьютер " + name + ", производитель " + vendor +
            "\nКонфигурация: { " +
            "\nПроцессор: " + cpu.getCpuManufacturer() + " " + cpu.getName() +
                ", Количество ядер: " + cpu.getCpuCoreNum() +  ", частота: " + cpu.getCpuFrequency() +
            "\nОперативная память: " + " Тип: " + ram.getRamType() +
                ". Объём памяти: " + ram.getRamSize() + "Gb" +
            "\nНакопитель: " + " Тип: " + storage.getStorageType() +
                "Объём накопителя: " + storage.getStorageSize() + "Gb" +
            "\nДисплей: " + " Диагональ: " + display.getDisplaySize() + " '' , Тип : " +
                display.getDisplayType() +
            "\nКлавиатура: " + " Тип: " + keyboard.getKeyboardType() + " с подсветкой " + boolToYesNo(keyboard.isHasBacklight()) + " }";
    }

    private String boolToYesNo(boolean val) {
        return val ? "Да" : "Нет";
    }
}
