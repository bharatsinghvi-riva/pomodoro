import co.flock.www.FlockApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MockPomodoroServer {

    private static final FlockApiClient flockApiClient = new FlockApiClient("de0e7de2-727a-4930-a3c4-f24be9056b12", false);

    public static void main(String[] args) throws Exception {
        HashMap<String, PomodoroUser> map = new HashMap<>();
        String userId = "u:fhm6yka0mms6yffx";
        PomodoroUser user = new PomodoroUser(userId, new ArrayList<>(), String.valueOf(System.currentTimeMillis()));
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
