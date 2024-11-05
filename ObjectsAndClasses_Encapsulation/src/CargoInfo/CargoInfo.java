package CargoInfo;

public class CargoInfo implements Cloneable {
    private final Dimensions dimensions;
    private final double weight; // kg
    private final boolean canTurnOver;
    private final String deliveryAddress;
    private final String regNumber;
    private final boolean isFragile;

    public CargoInfo(Dimensions dimensions,
                     double weight,
                     boolean canTurnOver,
                     String deliveryAddress,
                     String regNumber,
                     boolean isFragile) {
        this.dimensions = dimensions;
        this.weight = weight;
        this.canTurnOver = canTurnOver;
        this.deliveryAddress = deliveryAddress;
        this.regNumber = regNumber;
        this.isFragile = isFragile;
    }

    public CargoInfo setDimensions(Dimensions dimensions) {
        return new CargoInfo(dimensions, weight, canTurnOver, deliveryAddress, regNumber, isFragile);
    }

    public CargoInfo setWeight(double weight) {
        return new CargoInfo(dimensions, weight, canTurnOver, deliveryAddress, regNumber, isFragile);
    }

    public CargoInfo setDeliveryAddress(String deliveryAddress) {
        return new CargoInfo(dimensions, weight, canTurnOver, deliveryAddress, regNumber, isFragile);
    }

    @Override
    public String toString() {
        return "\n---Начало описания объекта\nHashCode = " + this.hashCode() +
                ". \nСодержимое: " + "Объект класса dimensions: " + dimensions.toString() +
                "\nМасса = " + weight +
                ". \nАдрес доставки = " + deliveryAddress +
                ". \nМожно переворачивать = " + boolToYesNo(canTurnOver) +
                ". \nРегмстрационный номер = " + regNumber +
                ". \nХрупкий = " + boolToYesNo(isFragile) +
                ". \n---";
    }

    private String boolToYesNo(boolean val){
        return val ? "Да" : "Нет";
    }

}