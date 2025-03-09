import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RedisTest {
    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis
    private static final int USERS_COUNT = 40;
    // блок показов, в котором допустима одна покупка (Extra)
    private static final int SHOWS_BLOCK_SIZE = 10;
    private static final int SLEEP_ON_REGULAR = 600;
    private static final int SLEEP_ON_EXTRA = 1000;
    // тур - это завершенный круг показ фото всех USERS/USERS_COUNT
    private static final int TOTAL_TURNS_COUNT = 3; // 2000;

    public static void main(String[] args) throws InterruptedException {
        RedisDateCarousel redis = new RedisDateCarousel(USERS_COUNT / SHOWS_BLOCK_SIZE);
        redis.init();

        Map<String, Double> usersMap = new HashMap<>();
        double orderNum = 0D;
        while (orderNum < USERS_COUNT) {
            String userId = "uid_" + Math.round(orderNum);// generateRandomInt(0, USERS_COUNT);
            if (!usersMap.containsKey(userId)) {
                usersMap.put(userId, orderNum);
                System.out.println("Пользователь id = " + userId +
                                   " зарегистрирован. Позиция = " + usersMap.get(userId));
                orderNum++;
            }
        }

        if (redis.bulkRegistration(usersMap)) {
            // redis.sortUsersList(true);
            startCarousel(redis);
            redis.shutdown();
        }
    }

    static void startCarousel(RedisDateCarousel redis) throws InterruptedException {
        for (int i = 0; i < TOTAL_TURNS_COUNT; i++) {
            // для каждого тура, определяется случайны базис срабатывания ExtraShow
            int base = generateRandomInt(1, SHOWS_BLOCK_SIZE / 2 + 1);
            System.out.println("Тур #" + (i + 1) + " base = " + base);
            int multiply = 0;
            // pos отражает score, т.е. базис сортировки
            for (int pos = 0; pos < USERS_COUNT; pos++) {
                if (pos != 0 && pos % SHOWS_BLOCK_SIZE == 0) {
                    multiply++;
                }
                // регулярный пользователь
                String userId = redis.getUserToShow(pos);
                if (userId == null) {
                    continue;
                }
                System.out.println("Пользователь " + userId + "(поз. " + redis.getUserRank(userId)
                                   + ") показывается.");
                Thread.sleep(SLEEP_ON_REGULAR);

                if (pos == (base + SHOWS_BLOCK_SIZE * multiply)) {
                    // подбираем номер пользователя, купившего услугу
                    int forcedPos = (SHOWS_BLOCK_SIZE - base) / 2 + base + SHOWS_BLOCK_SIZE * multiply;
                    userId = redis.getUserToShow(forcedPos);
                    System.out.println("Пользователь " + userId + "(поз. " + redis.getUserRank(userId)
                                       + ") запросил немедленный показ.");
                    redis.onRequestShowNow(pos + 0.001, userId);
                    System.out.println("Пользователь " + userId + "(новая поз. " + redis.getUserRank(userId)
                                       + ") показывается (вне очереди).");
                    Thread.sleep(SLEEP_ON_EXTRA);
                }
            }
            redis.onTurnFinished();
        }
    }

    static int generateRandomInt(int lowerBound, int upperBound) {
        Random rand = new Random();
        return rand.nextInt(lowerBound, upperBound);
    }
}