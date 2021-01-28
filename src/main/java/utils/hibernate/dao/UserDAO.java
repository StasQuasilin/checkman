package utils.hibernate.dao;

import constants.Constants;
import entity.User;

public class UserDAO extends HibernateDAO {
    public User getUserByUUID(String uuid){
        return hibernator.get(User.class, Constants.UID, uuid);
    }
}
