package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.log.comparators.TransportationComparator;
import entity.organisations.Organisation;
import entity.transport.Driver;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
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
    final TransportationComparator comparator = new TransportationComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        System.out.println(body);
        Driver driver;

        if (body != null) {
            if (body.containsKey(Constants.ID)) {
                long id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
                driver = hibernator.get(Driver.class, "id", id);
                logger.info("Edit vehicle " + driver.getId() + "...");
            } else {
                driver = new Driver();
                driver.setPerson(new Person());
                logger.info("Create new driver...");
            }

            driver.getPerson().setSurname(String.valueOf(body.get(Constants.Person.SURNAME)));
            logger.info("\t...Surname:" + driver.getPerson().getSurname());

            driver.getPerson().setForename(String.valueOf(body.get(Constants.Person.FORENAME)));
            logger.info("\t...Forename:" + driver.getPerson().getForename());

            driver.getPerson().setPatronymic(String.valueOf(body.get(Constants.Person.PATRONYMIC)));
            logger.info("\t...Patronymic:" + driver.getPerson().getPatronymic());

            if ((body.containsKey(Constants.Vehicle.TRANSPORTER_ID))) {
                long id = (long) body.get(Constants.Vehicle.TRANSPORTER_ID);
                Organisation organisation = hibernator.get(Organisation.class, "id", id);
                driver.setOrganisation(organisation);
                logger.info("\t...Organisation: \'" + driver.getOrganisation().getValue() + "\'");
            }

            hibernator.save(driver.getPerson(), driver);

            long transportationId = -1;

            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = Long.parseLong(String.valueOf(body.get(Constants.TRANSPORTATION_ID)));
            }
            if (transportationId != -1) {
                Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
                comparator.fix(transportation);
                transportation.setDriver(driver);
                hibernator.save(transportation);
                comparator.compare(transportation, getWorker(req));
                logger.info("Put in transportation " + transportation.getId());
            }

            write(resp, JsonParser.toJson(driver).toJSONString());
            body.clear();
        } else {
            write(resp, emptyBody);
        }

    }
}
