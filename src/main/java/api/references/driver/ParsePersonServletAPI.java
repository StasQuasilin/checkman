package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.transport.Driver;
import org.json.simple.JSONObject;
import utils.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.PARSE_PERSON)
public class ParsePersonServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            List<String> personData = Parser.parsePerson(key);

            System.out.println("Data: " + personData);

            Driver driver = null;
            Person personByName = dao.getPersonByName(personData.get(0));
            System.out.println("Person " + personByName);
            if (personByName != null){
                driver = dao.getDriverByPerson(personByName);
                System.out.println("Driver "+ driver);
            }
            if (driver == null) {
                driver = new Driver();
                Person person = new Person();
                if(personData.size() > 0) {
                    person.setSurname(personData.get(0));
                    if (personData.size() > 1){
                        person.setForename(personData.get(1));
                        if(personData.size() > 2){
                            person.setPatronymic(personData.get(2));
                        }
                    }
                }
                driver.setPerson(person);
            }

            JSONObject json = parser.toJson(driver);
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
