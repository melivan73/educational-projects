public class Basket {

    private static int count = 0; // количество корзин?
    private String items = "";
    private int totalPrice = 0;
    private int totalCount = 0; // количество товаров в данной корзине
    private int limit;
    private double totalWeight = 0; // кг

    private static int allBasketsTotalGoodsAmount;
    private static int allBasketsTotalPrice;

    public static void addBasketAmountToAllBasketsTotal(int amount) {
        allBasketsTotalGoodsAmount = allBasketsTotalGoodsAmount + amount;
    }

    public static void addBasketPriceToAllBasketsTotal(int price) {
        allBasketsTotalPrice = allBasketsTotalPrice + price;
    }

    public static int allBasketsAverageProductPrice() {
        return (allBasketsTotalGoodsAmount <=0) ? 0 : allBasketsTotalPrice / allBasketsTotalGoodsAmount;
    }

    public static int allBasketsAverageBasketPrice() {
        return (count <= 0) ? 0 : allBasketsTotalPrice / count;
    }

    public Basket() {
        increaseCount(1);
        items = "Список товаров:";
        this.limit = 1000000;
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
    }

    // В этом конструкторе неясно сколько товаров добавляется (не строку же парсить) -
    // (даже itemS намекает, на то, что может добавляться больше одного)
    // придется допустить, что добавляется только один товар
    // Лучше всего этот конструктор удалить (не удалил, так как это нарушит задание).
    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        this.totalPrice = totalPrice;

        this.totalCount = 1;
        addBasketAmountToAllBasketsTotal(this.totalCount);
        addBasketPriceToAllBasketsTotal(this.totalPrice);
    }

    public static int getCount() {
        return count;
    }

    public static void increaseCount(int count) {
        Basket.count = Basket.count + count;
    }

    public void add(String name, int price) {
        add(name, price, 1, 0);
    }

    public void add(String name, int price, int count) {
        add(name, price, count, 0);
    }

    public void add(String name, int price, int count, double weight) {
        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occured :(");
            return;
        }

        items = items + "\n" + name + " - " +
                count + " шт., цена за шт. - " + price + " руб., вес за шт. - " + weight + " кг.";

        totalCount = totalCount + count;
        totalPrice = totalPrice + count * price;
        // totalWeight c учетом count
        totalWeight = totalWeight + count * weight;

        addBasketAmountToAllBasketsTotal(count);
        addBasketPriceToAllBasketsTotal(count * price);
    }

    // Здесь уже сказывается, что класс Basket спроектирован неверно
    // (или вернее - поставленная задача и предыдушая реализация имеют внутренние противоречия
    // Конкретно по этому методу - у нас нигде не хранится кол-во товаров в данной корзине,
    // Метод add -> кол-во товаров данной корзин добавлено в общее по всем корзинам, затем мы
    // очистили данную корзину и получили неверное общее кол-во
    // Завел переменную totalCount, но это нарушает по факту задание/пред. реализацию
    // 1. В прошлых заданиях не было требования хранить количество товаров в корзине,
    // 2. В этом задании - "Реализуйте статические методы, которые будут УВЕЛИЧИВАТЬ значения этих переменных
    // при добавлении в корзину новых товаров..."
    // Хотя, имхо, проблемы неизбежны, если сущность Basket, каким-то образом знает о других Basket
    public void clear() {
        addBasketAmountToAllBasketsTotal(-totalCount);
        addBasketPriceToAllBasketsTotal(-totalPrice);
        items = "";
        totalCount = 0;
        totalPrice = 0;
        totalWeight = 0;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println(items);
        }
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public static int getAllBasketsTotalGoodsAmount() {
        return allBasketsTotalGoodsAmount;
    }

    public static int getAllBasketsTotalPrice() {
        return allBasketsTotalPrice;
    }
}