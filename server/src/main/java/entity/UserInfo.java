package entity;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class UserInfo {
    private final User user;
    private final String ip;
    private final String sessionId;
    private long activity;

    public static final long SESSION_LIFE_TIME = 30 * 60 * 1000;

    public UserInfo(User user, String ip, String sessionId) {
        this.user = user;
        this.ip = ip;
        this.sessionId = sessionId;
        updateActivity();
    }

    public User getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean isValid(String ip){
        return System.currentTimeMillis() - activity < SESSION_LIFE_TIME && this.ip.equals(ip);
    }

    public void updateActivity() {
        activity = System.currentTimeMillis();
    }
}
