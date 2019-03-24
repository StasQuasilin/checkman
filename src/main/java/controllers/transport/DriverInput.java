package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.Person;
import entity.transport.Driver;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.UI.DRIVER_MODAL)
public class DriverInput extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);

        Driver driver;
        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            driver = hibernator.get(Driver.class, "id", id);
        } else {
            driver = new Driver();
            driver.setPerson(new Person());
        }

        List<String> strings = Parser.parsePerson(String.valueOf(body.get(Constants.KEY)));
        if(strings.size() > 0){
            driver.getPerson().setSurname(strings.get(0));
        }
        if (strings.size() > 1){
            driver.getPerson().setForename(strings.get(1));
        }
        if(strings.size() > 2){
            driver.getPerson().setPatronymic(strings.get(2));
        }

        req.setAttribute("driver", driver);
        req.setAttribute("findOrganisationAPI", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveDriverAPI", Branches.API.References.SAVE_DRIVER);
        req.setAttribute("transportation", body.get(Constants.TRANSPORTATION_ID));
        req.setAttribute("modalContent", "/pages/transport/driverInput.jsp");
        req.setAttribute("title", Constants.Titles.DRIVER_INPUT);
        show(req, resp);
    }
}
