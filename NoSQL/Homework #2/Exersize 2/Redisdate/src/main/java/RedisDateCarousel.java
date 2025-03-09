import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.api.SortOrder;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.*;

import static java.lang.System.out;

@SuppressWarnings("unused")
public class RedisDateCarousel {
    private final static String KEY = "TO_DATE_USERS";
    private final String[][] initialUserOrder;
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> usersToDate;

    public RedisDateCarousel(int maxExtraTimesPerTurn) {
        initialUserOrder = new String[maxExtraTimesPerTurn][2];
        Arrays.stream(initialUserOrder).forEach(strings -> {
            strings[0] = "";
            strings[1] = "";
        });
    }

    public void listKeys() {
        Iterable<String> keys = rKeys.getKeys();
        for (String key : keys) {
            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
        }
    }

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        usersToDate = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    public void shutdown() {
        redisson.shutdown();
    }

    public boolean onRegistration(double score, String userId) {
        // добавляем пользователя, score = порядковый номер регистрации
        if (!usersToDate.addIfAbsent(score, String.valueOf(userId))) {
            System.err.println("Ошибка при регистрации пользователя. Пользователь "
                               + userId + " не добавлен!");
            return false;
        }
        return true;
    }

    public boolean bulkRegistration(Map<String, Double> map) {
        if (usersToDate.addAllIfAbsent(map) < map.size()) {
            System.err.println("Ошибка при регистрации пользователей.");
            return false;
        }
        return true;
    }

    public Collection<String> sortUsersList(boolean sortAsc) {
        SortOrder order = sortAsc ? SortOrder.ASC : SortOrder.DESC;
        return usersToDate.readSort(order);
    }

    public String getUserToShow(double score) {
        List<String> user = (ArrayList<String>) usersToDate.valueRange(score, true, score, true);
        if (user.isEmpty()) {
            System.err.println("Пользователь с поз. " + Math.round(score)  + " был перемещен");
            return null;
        }
        return user.get(0);
    }

    public int getUserRank(String userId) {
        return usersToDate.rank(userId);
    }

    public void onRequestShowNow(double score, String userId) {
        for (int i = 0; i < initialUserOrder.length; i++) {
            if (initialUserOrder[i][0].isEmpty()) {
                initialUserOrder[i][0] = userId;
                Double oldScore = usersToDate.getScore(userId);
                initialUserOrder[i][1] = oldScore.toString();
                usersToDate.add(score, userId);
                break;
            }
        }
    }

    public void onTurnFinished() {
        // восстанавливаем веса элементов, показанных вне очереди
        for (int i = 0; i < initialUserOrder.length; i++) {
            if (!initialUserOrder[i][0].isEmpty() && !initialUserOrder[i][1].isEmpty()) {
                usersToDate.add(Double.parseDouble(initialUserOrder[i][1]), String.valueOf(initialUserOrder[i][0]));
                initialUserOrder[i][0] = initialUserOrder[i][1] = "";
            }
        }
    }
}
