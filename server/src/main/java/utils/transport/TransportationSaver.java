package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.transport.ActionTime;
import entity.transport.TransportCustomer;
import entity.transport.TransportUtil;
import entity.transport.Transportation2;
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
        Date date = DateUtil.parseFromEditor(String.valueOf(body.get(DATE)));
        if (TransportUtil.setDate(transportation, date)){
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
