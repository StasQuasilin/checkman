package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.DocumentUIDGenerator;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.sql.Date;

/**
 * Created by szpt_user045 on 04.12.2019.
 */
public class TransportationSaver implements Constants{

    private final dbDAO dao = dbDAOService.getDAO();
    private final Logger log = Logger.getLogger(TransportationSaver.class);

    public synchronized Transportation2 saveTransportation(JSONObject body, Worker worker) {
        Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
        if (transportation == null){
            log.info("New transportation");
            transportation = new Transportation2();
            transportation.setUid(DocumentUIDGenerator.generateUID());
            transportation.setCreateTime(new ActionTime(worker));
            transportation.setManager(worker);
        } else {
            log.info("Edit transportation: " + transportation.getId());
        }
        boolean save = false;
        Date date = Date.valueOf(String.valueOf(body.get(DATE)));
        if (TransportUtil.setDate(transportation, date)){
            save = true;
        }

        Trailer trailer = dao.getObjectById(Trailer.class, body.get(TRAILER));
        if (TransportUtil.setTrailer(transportation, trailer)){
            save = true;
        }

        Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE));
        if (TransportUtil.setVehicle(transportation, vehicle)){
            save = true;
        }

        Driver driver = dao.getObjectById(Driver.class, body.get(DRIVER));
        if (TransportUtil.setDriver(transportation, driver)){
            save = true;
        }

        Organisation transporter = dao.getObjectById(Organisation.class, body.get(TRANSPORTER));
        if (TransportUtil.setTransporter(transportation, transporter)){
            save = true;
        }


        TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(body.get(CUSTOMER)));
        if (TransportUtil.setCustomer(transportation, customer)){
            save = true;
        }
        if (save){
            dao.save(transportation.getCreateTime(), transportation);
        }
        return transportation;
    }
}
