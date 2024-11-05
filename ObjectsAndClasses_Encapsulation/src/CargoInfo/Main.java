package CargoInfo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Dimensions dimensions = new Dimensions(503, 321, 200);
        System.out.println("Создали объект класса Dimensions: " + dimensions);
        CargoInfo cargoInfo = new CargoInfo(dimensions, 299, true, "Москва, ул. Летняя, д.27, кв. 58", "N-123456", false);
        System.out.println("Создали объект класса CargoInfo: " + cargoInfo);

        Dimensions  updDimensions = dimensions.setLength(700);
        System.out.println("Меняем свойство длина в dimensions. " +
                "\nТак как класс Dimensions immutable, ьудет создан новый объект.");
        System.out.println("Проверяем новый объект dimensions: " + updDimensions);
        // в контейнере лежит старая ссылка на Dimensions, так как класс CargoInfo immutable
        System.out.println("Проверяем объект контейнер: " + cargoInfo);

        CargoInfo updCargoInfo = cargoInfo.setDimensions(updDimensions);
        System.out.println("Устаналиваем ссылку на новый Dimensions в CargoInfo. \n" +
                "При этои будет создан новый объект класса CargoInfo");
        System.out.println("Проверяем объект контейнер: " + updCargoInfo);

        // видимо, можно было бы использовать переменную updCargoInfo,
        // так как у нас нет задачи хранить все объекты. На всякий случай в новую )
        CargoInfo updupdCargoInfo = cargoInfo.setDeliveryAddress("На деревню дедушке");
        System.out.println("Меняен адрес доставки. \n" +
                "При этои будет создан новый объект класса CargoInfo");
        System.out.println("Проверяем объект контейнер: " + updupdCargoInfo);
    }
}