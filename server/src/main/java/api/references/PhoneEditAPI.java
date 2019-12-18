package api.references;

import api.ServletAPI;
import constants.Branches;
import entity.Person;
import entity.PhoneNumber;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 17.12.2019.
 */
@WebServlet(Branches.API.PHONE_EDIT)
public class PhoneEditAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            if (body.containsKey(PERSON)){
                PhoneNumber phoneNumber = dao.getObjectById(PhoneNumber.class, body.get(ID));
                if (phoneNumber == null){
                    phoneNumber = new PhoneNumber();
                }
                Person person = dao.getObjectById(Person.class, body.get(PERSON));
                phoneNumber.setPerson(person);

                String number = String.valueOf(body.get(NUMBER));
                StringBuilder builder = new StringBuilder();
                for (char c : number.toCharArray()){
                    if (Character.isDigit(c) || c == '+'){
                        builder.append(c);
                    }
                }
                phoneNumber.setNumber(builder.toString());

                dao.save(phoneNumber);
                JSONObject json = new SuccessAnswer(RESULT, phoneNumber.toJson()).toJson();
                write(resp, json.toJSONString());
                pool.put(json);
            }

        }
    }
}
