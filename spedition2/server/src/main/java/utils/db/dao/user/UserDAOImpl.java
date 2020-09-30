package utils.db.dao.user;

import entity.user.User;
import entity.user.UserSettings;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static constants.Keys.ID;
import static constants.Keys.USER;

public class UserDAOImpl implements UserDAO{

    private final Hibernator hibernator = Hibernator.getInstance();
    @Override
    public User getUserById(Object id) {
        return hibernator.get(User.class, ID, id);
    }

    @Override
    public UserSettings getUserSettings(User user) {
        return hibernator.get(UserSettings.class, USER, user);
    }

    @Override
    public void saveSettings(UserSettings settings) {
        hibernator.save(settings);
    }

    @Override
    public void save(User user) {
        hibernator.save(user);
    }

    @Override
    public void removeUser(User user) {
        hibernator.remove(user);
    }

    @Override
    public List<User> findUser(String key, User user) {
        final HashMap<Integer, User> result = new HashMap<>();
        findUser(result, "surname", key, user);
        findUser(result, "forename", key, user);
        return new LinkedList<>(result.values());
    }

    private void findUser(HashMap<Integer, User> result, String key, String value, User user) {
        for (User u : hibernator.find(User.class, key, value)){
            final int id = u.getId();
            if (id != user.getId()) {
                if (!result.containsKey(id)) {
                    result.put(id, u);
                }
            }
        }
    }
}
