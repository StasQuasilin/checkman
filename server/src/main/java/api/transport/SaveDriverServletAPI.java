package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.log.comparators.TransportationComparator;
import entity.organisations.Organisation;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_DRIVER)
public class SaveDriverServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(SaveDriverServletAPI.class);
    final TransportationComparator comparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            log.info(body);
            Driver driver;

            if (body.containsKey(Constants.ID)) {
                driver = dao.getDriverByID(body.get(Constants.ID));
                log.info("Edit driver " + driver.getId() + "...");
            } else {
                driver = new Driver();
                driver.setPerson(new Person());
                log.info("Create new driver...");
            }

            Person person = driver.getPerson();
            person.setSurname(String.valueOf(body.get(Constants.Person.SURNAME)));
            log.info("\t...Surname:" + driver.getPerson().getSurname());

            person.setForename(String.valueOf(body.get(Constants.Person.FORENAME)));
            log.info("\t...Forename:" + driver.getPerson().getForename());

            person.setPatronymic(String.valueOf(body.get(Constants.Person.PATRONYMIC)));
            log.info("\t...Patronymic:" + driver.getPerson().getPatronymic());

            String license = String.valueOf(body.get("license"));
            if (U.exist(license)){
                license = license.trim().toUpperCase().replaceAll("  ", " ");
                if (U.exist(driver.getLicense())){
                    String l = driver.getLicense().trim().toUpperCase().replaceAll("  ", " ");
                    if (!l.equals(license)){
                        log.info("Change license");
                        ActionTime archiveTime = new ActionTime(getWorker(req));
                        driver.setArchive(archiveTime);
                        dao.save(archiveTime);
                        dao.save(driver);
                        Organisation organisation = driver.getOrganisation();
                        driver = new Driver();
                        driver.setLicense(license);
                        driver.setPerson(person);
                        driver.setOrganisation(organisation);
                    }
                } else {
                    driver.setLicense(license);
                }
                log.info("\t...License: " + license);
            } else if (U.exist(driver.getLicense())){
                driver.setLicense(null);
            }

            if (body.containsKey("transporter")) {
                int transporterId = Integer.parseInt(String.valueOf(body.get("transporter")));
                if (transporterId != -1) {
                    Organisation transporter = dao.getOrganisationById(transporterId);
                    driver.setOrganisation(transporter);
                    log.info("\t...Transporter: " + transporter.getValue());
                }
            }

            dao.save(person);
            dao.save(driver);
            updateUtil.onSave(driver);

            long transportationId = -1;

            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = Long.parseLong(String.valueOf(body.get(Constants.TRANSPORTATION_ID)));
            }
            if (transportationId != -1) {
                Transportation transportation = dao.getTransportationById(transportationId);
                comparator.fix(transportation);
                TransportUtil.setDriver(transportation, driver);
                dao.saveTransportation(transportation);
                comparator.compare(transportation, getWorker(req));
                log.info("Put in transportation " + transportation.getId());
            }

            write(resp, parser.toJson(driver).toJSONString());
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
