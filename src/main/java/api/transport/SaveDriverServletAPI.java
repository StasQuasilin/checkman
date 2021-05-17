package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.log.comparators.TransportComparator;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

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
    final TransportComparator comparator = new TransportComparator();
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
            person.setSurname(String.valueOf(body.get(Constants.SURNAME)));
            log.info("\t...Surname:" + driver.getPerson().getSurname());

            Object fore = body.get(Constants.FORENAME);
            if (fore != null) {
                person.setForename(String.valueOf(fore));
                log.info("\t...Forename:" + driver.getPerson().getForename());
            }

            Object patron = body.get(Constants.PATRONYMIC);
            if (patron != null) {
                person.setPatronymic(String.valueOf(patron));
                log.info("\t...Patronymic:" + driver.getPerson().getPatronymic());
            }

            String license = String.valueOf(body.get("license"));
            if (U.exist(license)){
                license = license.trim().toUpperCase().replaceAll("  ", " ");
                if (driver.getLicense() == null || !driver.getLicense().equals(license)){
                    driver.setLicense(license);
                }
                log.info("\t...License: " + license);
            } else if (U.exist(driver.getLicense())){
                driver.setLicense(null);
            }

            if (body.containsKey("transporter")) {
                Organisation transporter = dao.getObjectById(Organisation.class, body.get("transporter"));
                driver.setOrganisation(transporter);
            } else {
                driver.setOrganisation(null);
            }

            if (body.containsKey(VEHICLE)){
                Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE));
                if (vehicle != null) {
                    if (body.containsKey(TRAILER)) {
                        Trailer trailer = dao.getObjectById(Trailer.class, body.get(TRAILER));
                        vehicle.setTrailer(trailer);
                    } else {
                        vehicle.setTrailer(null);
                    }
                    dao.save(vehicle);
                }
                driver.setVehicle(vehicle);
            } else {
                driver.setVehicle(null);
            }
            dao.save(person);
            dao.save(driver);
            updateUtil.onSave(driver);

            long transportationId = -1;

            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = Long.parseLong(String.valueOf(body.get(Constants.TRANSPORTATION_ID)));
            }
            if (transportationId != -1) {
                Transportation transportation = dao.getObjectById(Transportation.class, transportationId);
                comparator.fix(transportation);
                TransportUtil.setDriver(transportation, driver, false);
                dao.saveTransportation(transportation);
                comparator.compare(transportation, getWorker(req));
                log.info("Put in transportation " + transportation.getId());
            }

            JSONObject json = new SuccessAnswer(RESULT, driver.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
