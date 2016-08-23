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

    private static Set<PomodoroUser> pomodoroUsers = new HashSet<>();
    private static Map<PomodoroUser, PomodoroLifeCycle> activeUsersMap = new HashMap<>();
    private static final String POMODORO_BOT_TOKEN = "c4814226-86f2-42bd-8dd1-cdb84155a581";

    public static void sendStartSessionMessage(PomodoroUser pomodoroUser, FlockApiClient flockApiClient) {
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
        sendMessage(message, flockApiClient);
    }

    public static void sendEndSessionMessage(PomodoroUser pomodoroUser, FlockApiClient flockApiClient) {
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
        sendMessage(message, flockApiClient);
    }

    public static void sendTerminateSessionMessage(PomodoroUser pomodoroUser, FlockApiClient flockApiClient) {
        String terminateSessionMsg = "Pack your bags. You have been productive today!";
        if (!pomodoroUser.getDistractions().isEmpty()) {
            terminateSessionMsg += "\nAlso remember to complete your tasks: " + pomodoroUser.getDistractions();
        }
        System.out.println(terminateSessionMsg);
        Message message = new Message(pomodoroUser.getUserId(), terminateSessionMsg);
        sendMessage(message, flockApiClient);
    }

    public static void addUser(PomodoroUser pomodoroUser) {
        String message = "New user installed app: " + pomodoroUser;
        System.out.println(message);
        removeUninstalledUserIfExist(pomodoroUser.getUserId());
        pomodoroUsers.add(pomodoroUser);
    }

    private static void removeUninstalledUserIfExist(String userId) {
        pomodoroUsers.remove(getPomodoroUser(userId));
        activeUsersMap.remove(getPomodoroUser(userId));
    }

    public static PomodoroUser addActiveUser(String userId) {
        String message = "User started lifeCycle: " + userId;
        System.out.println(message);
        PomodoroUser pomodoroUser = getPomodoroUser(userId);
        PomodoroLifeCycle pomodoroLifeCycle = new PomodoroLifeCycle(pomodoroUser, new FlockApiClient(POMODORO_BOT_TOKEN, false));
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

    private static void sendMessage(Message message, FlockApiClient flockApiClient) {
        try {
            flockApiClient.chatSendMessage(new FlockMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
