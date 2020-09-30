package utils.db.dao.user;

import entity.user.User;
import entity.user.UserSettings;

import java.util.List;

public interface UserDAO {
    User getUserById(Object token);

    UserSettings getUserSettings(User user);

    void saveSettings(UserSettings settings);

    void save(User user);

    void removeUser(User user);

    List<User> findUser(String key, User user);
}
