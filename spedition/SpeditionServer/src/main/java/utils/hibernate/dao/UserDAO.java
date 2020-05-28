package utils.hibernate.dao;

import entity.Phone;
import entity.User;
import entity.UserAccess;
import utils.hibernate.Hibernator;

import static constants.Keys.PHONE;
import static constants.Keys.USER_PERSON;

public class UserDAO {
    private final Hibernator hibernator = new Hibernator();

    public UserAccess getUserAccessByPhone(String number) {
        final Phone phone = hibernator.get(Phone.class, PHONE, number);
        if (phone != null){
            return hibernator.get(UserAccess.class, USER_PERSON, phone.getPerson());
        }

        return null;
    }
}
