package utils.access;

import entity.User;

import javax.servlet.http.HttpSession;

/**
 * Created by szpt_user045 on 22.05.2019.
 */
public class UserData {
    private final User user;
    private final HttpSession session;

    public UserData(User user, HttpSession session) {
        this.user = user;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public HttpSession getSession() {
        return session;
    }
}
