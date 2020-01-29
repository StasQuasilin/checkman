package utils;

import entity.Person;
import entity.PhoneNumber;
import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

/**
 * Created by szpt_user045 on 29.01.2020.
 */
public class PhoneCreateUtil {

    private final Logger log = Logger.getLogger(PhoneCreateUtil.class);
    private dbDAO dao = dbDAOService.getDAO();
    public void createPhone(String phone, Person person){
        createPhone(null, phone, person);
    }

    public PhoneNumber createPhone(PhoneNumber phoneNumber, String number, Person person){
        if (phoneNumber == null){
            log.info("Create new Phone number");
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
        return phoneNumber;
    }
}
