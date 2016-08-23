import java.util.ArrayList;
import java.util.List;

public class PomodoroUser {
    private final String userId;
    private final List<String> distractions;
    private final String userToken;

    public PomodoroUser(String userId, String userToken) {
        this.userId = userId;
        this.distractions = new ArrayList<>();
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getDistractions() {
        return distractions;
    }

    public String getUserToken() {
        return userToken;
    }

    public void addToDistractions(String distraction) {
        distractions.add(distraction);
    }

    public void deleteDistractions() {
        distractions.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PomodoroUser that = (PomodoroUser) o;
        if (!userId.equals(that.userId)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return "PomodoroUser{" +
                "userId='" + userId + '\'' +
                ", distractions=" + distractions +
                '}';
    }
}
