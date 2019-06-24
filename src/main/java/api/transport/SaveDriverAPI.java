package api.transport;

import api.API;
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
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_DRIVER)
public class SaveDriverAPI extends API {

    final Logger logger = Logger.getLogger(SaveDriverAPI.class);
    final TransportationComparator comparator = new TransportationComparator();
    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            logger.info(body);
            Driver driver;

            if (body.containsKey(Constants.ID)) {
                driver = dao.getDriverByID(body.get(Constants.ID));
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
                Organisation organisation = dao.getOrganisationById(body.get(Constants.Vehicle.TRANSPORTER_ID));
                driver.setOrganisation(organisation);
                logger.info("\t...Organisation: \'" + driver.getOrganisation().getValue() + "\'");
            }

            dao.save(driver.getPerson());
            dao.save(driver);

            long transportationId = -1;

            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = Long.parseLong(String.valueOf(body.get(Constants.TRANSPORTATION_ID)));
            }
            if (transportationId != -1) {
                Transportation transportation = dao.getTransportationById(transportationId);
                comparator.fix(transportation);
                transportation.setDriver(driver);
                dao.saveTransportation(transportation);
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
