package entity;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class UserInfo {
    private User user;
    private String ip;

    public UserInfo(User user, String ip) {
        this.user = user;
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }
}
