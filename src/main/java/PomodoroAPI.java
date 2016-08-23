import attachments.AttachmentAssets;
import co.flock.www.FlockApiClient;
import co.flock.www.model.messages.Attachments.Attachment;
import co.flock.www.model.messages.Attachments.Image;
import co.flock.www.model.messages.Attachments.ImageView;
import co.flock.www.model.messages.Attachments.View;
import co.flock.www.model.messages.FlockMessage;
import co.flock.www.model.messages.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PomodoroAPI {

    private final FlockApiClient flockApiClient;
    private static Set<PomodoroUser> pomodoroUsers;
    private static Map<PomodoroUser, PomodoroLifeCycle> activeUsersMap;

    public PomodoroAPI(FlockApiClient flockApiClient) {
        this.flockApiClient = flockApiClient;
        pomodoroUsers = new HashSet<>();
        activeUsersMap = new HashMap<>();
    }

    public void sendStartSessionMessage(PomodoroUser pomodoroUser) {
        String startSessionMsg = "Your pomodoro session has begun. Focus!";
        System.out.println(startSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), startSessionMsg);
        Attachment attachment = new Attachment();
        View view = new View();
        Image image = new Image();
        image.setSrc(AttachmentAssets.getFocusAttachment());
        ImageView imageView = new ImageView();
        imageView.setOriginal(image);
        view.setImage(imageView);
        attachment.setViews(view);
        Attachment[] attachments = new Attachment[1];
        attachments[0] = attachment;
        message.setAttachments(attachments);
        sendMessage(message);
    }

    public void sendEndSessionMessage(PomodoroUser pomodoroUser) {
        String endSessionMsg = "Its time to take a break. Chill!";
        endSessionMsg += pomodoroUser.getDistractions();
        System.out.println(endSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), endSessionMsg);
        Attachment attachment = new Attachment();
        View view = new View();
        Image image = new Image();
        image.setSrc(AttachmentAssets.getBreakAttachment());
        ImageView imageView = new ImageView();
        imageView.setOriginal(image);
        view.setImage(imageView);
        attachment.setViews(view);
        Attachment[] attachments = new Attachment[1];
        attachments[0] = attachment;
        message.setAttachments(attachments);
        sendMessage(message);
    }

    public void terminateSession(PomodoroUser pomodoroUser) {
        String terminateSessionMsg = "Pack your bags. You have been productive today!";
        System.out.println(terminateSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), terminateSessionMsg);
        sendMessage(message);
    }

    public static void addUser(PomodoroUser pomodoroUser) {
        String message = "New user installed app: " + pomodoroUser;
        System.out.println(message);
        pomodoroUsers.add(pomodoroUser);
    }

    public static PomodoroUser addActiveUser(String userId) {
        String message = "User started lifeCycle: " + userId;
        System.out.println(message);
        PomodoroUser pomodoroUser = getPomodoroUser(userId);
        PomodoroLifeCycle pomodoroLifeCycle = new PomodoroLifeCycle(pomodoroUser, new FlockApiClient(pomodoroUser.getUserToken()));
        pomodoroLifeCycle.startLife();
        activeUsersMap.put(pomodoroUser, pomodoroLifeCycle);
        return pomodoroUser;
    }

    public static PomodoroUser removeActiveUser(String userId) {
        String message = "User ended lifeCycle: " + userId;
        System.out.println(message);
        PomodoroUser pomodoroUser = getPomodoroUser(userId);
        if (pomodoroUser != null) {
            activeUsersMap.get(pomodoroUser).endLife();
            activeUsersMap.remove(pomodoroUser);
        }
        return pomodoroUser;
    }

    public static PomodoroUser getPomodoroUser(String userId) {
        PomodoroUser pomodoroUser = null;
        for (PomodoroUser currPomodororUser : pomodoroUsers) {
            if (currPomodororUser.getUserId().equals(userId)) {
                pomodoroUser = currPomodororUser;
            }
            System.out.print("Found pomodoro user:" + pomodoroUser + " for id:" + userId);
        }
        return pomodoroUser;
    }

    private void sendMessage(Message message) {
        try {
            flockApiClient.chatSendMessage(new FlockMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
