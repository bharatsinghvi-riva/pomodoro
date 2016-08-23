import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;

public class PomodoroAPI {

    private final FlockApiClient flockApiClient;

    public PomodoroAPI(FlockApiClient flockApiClient) {
        this.flockApiClient = flockApiClient;
    }

    public void sendStartSessionMessage(PomodoroUser pomodoroUser) {
        String startSessionMsg = "Your pomodoro session has begun. Focus!";
        System.out.println(startSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), startSessionMsg);
        sendMessage(message);
    }

    public void sendEndSessionMessage(PomodoroUser pomodoroUser) {
        String endSessionMsg = "Its time to take a break. Chill!\n";
        endSessionMsg += pomodoroUser.getDistractions();
        System.out.println(endSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), endSessionMsg);
        sendMessage(message);
    }

    public void terminateSession(PomodoroUser pomodoroUser) {
        String terminateSessionMsg = "Pack your bags. You have been productive today!";
        System.out.println(terminateSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), terminateSessionMsg);
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        try {
            flockApiClient.chatSendMessage(new FlockMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
