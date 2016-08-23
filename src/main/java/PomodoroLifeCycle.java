import co.flock.www.FlockApiClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PomodoroLifeCycle {

    private static final int POMODORO_SESSION_TIME = 5;
    private static final int POMODORO_BREAK_TIME = 1;
    private static final TimeUnit POMODORO_TIME_UNIT = TimeUnit.SECONDS;
    private final PomodoroUser pomodoroUser;
    private ScheduledExecutorService executor;
    private final PomodoroAPI pomodoroAPI;

    public PomodoroLifeCycle(PomodoroUser pomodoroUser, FlockApiClient flockApiClient) {
        this.pomodoroUser = pomodoroUser;
        pomodoroAPI = new PomodoroAPI(flockApiClient);
    }

    public void startLife() {
        pomodoroAPI.sendStartSessionMessage(pomodoroUser);
        if (executor != null && !executor.isShutdown()) executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = getRunnableOnEndOfPomodoroSession();
        executor.schedule(runnable, POMODORO_SESSION_TIME, POMODORO_TIME_UNIT);
    }

    private Runnable getRunnableOnEndOfPomodoroSession() {
        return () -> {
            pomodoroAPI.sendEndSessionMessage(pomodoroUser);
            pomodoroUser.deleteDistractions();
            restartPomodoroSession();
        };
    }

    private void restartPomodoroSession() {
        if (!executor.isShutdown()) executor.shutdown();
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                startLife();
            }
        }, POMODORO_BREAK_TIME, POMODORO_TIME_UNIT);
    }

    public void endLife() {
        pomodoroAPI.terminateSession(pomodoroUser);
        if (!executor.isShutdown()) executor.shutdown();
    }
}

