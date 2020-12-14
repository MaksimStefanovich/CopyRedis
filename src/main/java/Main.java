import org.redisson.api.RQueue;
import org.redisson.api.RScoredSortedSet;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {
    private final static Double SCORE_INC = 0.1;
    private static final int SLEEP = 3000; // 1 миллисекунда

    public static void main(String[] args) throws InterruptedException {

        Storage redis = new Storage();
        redis.init();

        Random r1 = new Random();


        RQueue<String> users = redis.getOnlineUsers();
        while (true) {

            int i = 0;
            for (String user : users) {
                String removeUser = users.remove();
                users.add(removeUser);
                System.out.println("- На главной странице показываем пользователя " + removeUser);
                i++;
                if (i % 10 == 0) {
                    String lucky = redis.donate(String.valueOf(r1.nextInt(19) + 1));
                    users.remove(lucky);
                    users.add(lucky);
                    System.out.println("- На главной странице показываем оплатившего пользователя " + lucky);
                    Thread.sleep(SLEEP);
                    break;
                }

            }

        }
    }

}
