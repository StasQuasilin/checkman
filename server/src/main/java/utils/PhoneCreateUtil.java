package utils;

import entity.Person;
import entity.PhoneNumber;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
public class PhoneCreateUtil {

    private dbDAO dao = dbDAOService.getDAO();

    public void createPhone(String phone, Person person){
        createPhone(null, phone, person);
    }

    public void  createPhone(PhoneNumber phoneNumber, String number, Person person){
        if (phoneNumber == null){
            phoneNumber = new PhoneNumber();
            phoneNumber.setPerson(person);
        }

        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || c == '+'){
                builder.append(c);
            }
        }
        phoneNumber.setNumber(builder.toString());

        dao.save(phoneNumber);
    }
}
