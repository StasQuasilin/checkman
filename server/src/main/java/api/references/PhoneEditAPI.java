package api.references;

import api.ServletAPI;
import constants.Branches;
import entity.Person;
import entity.PhoneNumber;
import entity.transport.Driver;
import org.json.simple.JSONObject;
import utils.PhoneCreateUtil;
import utils.UpdateUtil;
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

    private final UpdateUtil updateUtil = new UpdateUtil();
    PhoneCreateUtil phoneCreateUtil = new PhoneCreateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            if (body.containsKey(PERSON)){
                PhoneNumber phoneNumber = dao.getObjectById(PhoneNumber.class, body.get(ID));
                Person person = dao.getObjectById(Person.class, body.get(PERSON));
                String number = String.valueOf(body.get(NUMBER));

                phoneCreateUtil.createPhone(phoneNumber, number, person);

                JSONObject json = new SuccessAnswer(RESULT, phoneNumber.toJson()).toJson();
                write(resp, json.toJSONString());
                pool.put(json);
                Driver driver = dao.getDriverByPerson(person);
                if (driver != null){
                    updateUtil.onSave(driver);
                }
            }
        }
    }
}
