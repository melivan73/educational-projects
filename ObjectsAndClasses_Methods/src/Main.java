public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);
        basket.print("Milk");

        // по идее вес должен зависеть от count,
        // реализовано без связи с count, так как  в задании это не было указано
        // в задании также было указано создать метод getTotalWeight(), хотя в реализаци
        Basket basket2 = new Basket();
        basket2.add("Cheese", 800, 2, 1.2);
        basket2.add("Water", 100, 1, 1.0);

        basket2.print("Корзина 2");
        System.out.println("Вес всей корзины " + basket2.getTotalWeight());
    }
}
