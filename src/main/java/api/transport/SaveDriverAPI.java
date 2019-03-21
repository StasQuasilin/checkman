package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.organisations.Organisation;
import entity.transport.Driver;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.JsonParser;
import utils.PostUtil;
import utils.U;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_DRIVER)
public class SaveDriverAPI extends IAPI{

    final Logger logger = Logger.getLogger(SaveDriverAPI.class);
    final String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Driver driver;
        if(U.exist(body.get(Constants.ID))){
            int id = Integer.parseInt(body.get(Constants.ID));
            driver = hibernator.get(Driver.class, "id", id);
            logger.info("Edit vehicle " + driver.getId() + "...");
        } else {
            driver = new Driver();
            driver.setPerson(new Person());
            logger.info("Create new driver...");
        }

        driver.getPerson().setSurname(body.get(Constants.Person.SURNAME));
        logger.info("\t...Surname:" + driver.getPerson().getSurname());

        driver.getPerson().setForename(body.get(Constants.Person.FORENAME));
        logger.info("\t...Forename:" + driver.getPerson().getForename());

        driver.getPerson().setPatronymic(body.get(Constants.Person.PATRONYMIC));
        logger.info("\t...Patronymic:" + driver.getPerson().getPatronymic());

        if (U.exist(body.get(Constants.Vehicle.TRANSPORTER_ID))) {
            int id = Integer.parseInt(body.get(Constants.Vehicle.TRANSPORTER_ID));
            Organisation organisation = hibernator.get(Organisation.class, "id", id);
            driver.setOrganisation(organisation);
            logger.info("\t...Organisation: \'" + driver.getOrganisation().getValue() + "\'");
        }

        hibernator.save(driver.getPerson(), driver);

        if (U.exist(body.get(Constants.TRANSPORTATION_ID))) {
            int id = Integer.parseInt(body.get(Constants.TRANSPORTATION_ID));
            Transportation transportation = hibernator.get(Transportation.class, "id", id);
            transportation.setDriver(driver);
            hibernator.save(transportation);
            logger.info("Put in transportation " + transportation.getId());
        }

        write(resp, answer);
        body.clear();

    }
}
