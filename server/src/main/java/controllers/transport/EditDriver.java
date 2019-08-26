package controllers.transport;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.Person;
import entity.transport.Driver;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.UI.EDIT_DRIVER)
public class EditDriver extends IModal {

    static final Logger log = Logger.getLogger(EditDriver.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        Driver driver = null;
        int size = 0;
        if (body != null) {
            if (body.containsKey(Constants.ID)) {
                driver = dao.getDriverByID(body.get(Constants.ID));
                size = dao.getTransportationsByDriver(driver).size();
                log.info("Driver: " + driver.getId());
            } else if (body.containsKey(Constants.KEY)){
                driver = new Driver();
                driver.setPerson(new Person());
                List<String> strings = Parser.parsePerson(String.valueOf(body.get(Constants.KEY)));
                if (strings.size() > 0) {
                    driver.getPerson().setSurname(strings.get(0));
                }
                if (strings.size() > 1) {
                    driver.getPerson().setForename(strings.get(1));
                }
                if (strings.size() > 2) {
                    driver.getPerson().setPatronymic(strings.get(2));
                }
            }
        }

        req.setAttribute("driver", driver);
        req.setAttribute("transportations", size);
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveDriverAPI", Branches.API.References.SAVE_DRIVER);
        req.setAttribute("modalContent", "/pages/transport/driverInput.jsp");
        req.setAttribute("title", Titles.DRIVER_INPUT);
        show(req, resp);
    }
}
