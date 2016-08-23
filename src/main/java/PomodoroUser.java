import java.util.List;

public class PomodoroUser {
    private final String userId;
    private final List<String> distractions;
    private final String startTime;
    private String endTime;

    public PomodoroUser(String userId, List<String> distractions, String startTime) {
        this.userId = userId;
        this.distractions = distractions;
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getDistractions() {
        return distractions;
    }

    public String getStartTime() {
        return startTime;
    }

    public void addToDistractions(String distraction) {
        distractions.add(distraction);
    }

    public void deleteDistractions() {
        distractions.clear();
    }

    @Override
    public String toString() {
        return "PomodoroUser{" +
                "userId='" + userId + '\'' +
                ", distractions=" + distractions +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
