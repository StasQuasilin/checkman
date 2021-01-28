package controllers.transport;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.Person;
import entity.transport.Driver;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
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
@WebServlet(Branches.UI.EDIT_DRIVER)
public class EditDriver extends IModal {

    static final Logger log = Logger.getLogger(EditDriver.class);
    private static final String ORGANISATION_EDIT = "organisationEdit";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        Driver driver = null;
        if (body != null) {
            if (body.containsKey(Constants.ID)) {
                driver = dao.getObjectById(Driver.class, body.get(Constants.ID));
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

        req.setAttribute(DRIVER, driver);
        req.setAttribute(SAVE, Branches.API.References.SAVE_DRIVER);
        req.setAttribute(MODAL_CONTENT, "/pages/transport/driverInput.jsp");
        req.setAttribute(FIND, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
        req.setAttribute("vehicleEdit", Branches.UI.EDIT_VEHICLE);
        req.setAttribute("findTrailer", Branches.API.References.FIND_TRAILER);
        req.setAttribute("parse", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(ORGANISATION_EDIT, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(PHONE_EDIT, Branches.API.PHONE_EDIT);
        req.setAttribute(PHONE_REMOVE, Branches.API.PHONE_REMOVE);

        req.setAttribute(TITLE, Titles.DRIVER_INPUT);
        show(req, resp);
    }
}
