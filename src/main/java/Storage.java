import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.Date;

public class Storage {
    private RedissonClient redissonClient;

    private final static String KEY = "users";
    private final static Double SCORE_INC = 0.1;

    private RQueue<String> users;

    public void init() {
        Config config = new Config();
        config
                .useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(Exc.getMessage());
        }
        users = redissonClient.getQueue(KEY, StringCodec.INSTANCE);
        users.clear();
        add();
    }

    public String donate(String elem) {
        System.out.println("> Пользователь " + elem + " оплатил платную услугу");

        return elem;
    }

    public RQueue<String> getOnlineUsers() {
        return users;
    }

    void shutdown() {
        redissonClient.shutdown();
    }

    void add() {
        double r = 1;
        for (int i = 1; i <= 20; i++) {
            users.add(String.valueOf(i));
        }
    }
}


