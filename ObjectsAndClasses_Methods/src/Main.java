public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);

        Basket basket2 = new Basket();
        basket2.add("Cheese", 800, 2, 1.2);
        basket2.add("Water", 100, 1, 1.0);

        basket2.print("Корзина 2");
        System.out.println("Вес всей корзины " + basket2.getTotalWeight());

        basket2.clear();
        basket2.print("Корзина 2");
        basket2.add("Water", 100, 1, 1.0);
        basket2.print("Корзина 2");

        System.out.println("Всего в " + Basket.getCount() + " корзинах, содержится " +
                Basket.getAllBasketsTotalGoodsAmount() + " товаров");
        System.out.println("Всего в " + Basket.getCount() + " корзинах, товаров на сумму " +
                Basket.getAllBasketsTotalPrice() + " руб.");
    }
}