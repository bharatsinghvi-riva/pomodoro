import co.flock.www.FlockApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MockPomodoroServer {

    private static final String USER_TOKEN = "4b3f4287-1cb4-448d-bccc-3e7dcd357464";
    private static final FlockApiClient flockApiClient = new FlockApiClient(USER_TOKEN, false);

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, PomodoroUser> map = new HashMap<>();
        String userId = "u:vz6b3697267nn9vv";
        PomodoroUser user = new PomodoroUser(userId, USER_TOKEN);
        map.put(userId, user);
        PomodoroLifeCycle pomodoro = new PomodoroLifeCycle(user, flockApiClient);
        pomodoro.startLife();
        Thread.sleep(10000);
        for (int i = 0; i < 10; i++) {
            user.addToDistractions(getRandomDistraction());
            Thread.sleep(100);
        }
        pomodoro.endLife();
    }

    public static String getRandomDistraction() {
        String distraction = "";
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            distraction += ((char) r.nextInt(26) - 'a');
        }
        return distraction;
    }
}
