package entity.log.comparators;

import entity.Worker;
import entity.documents.DocumentOrganisation;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.log.IChangeComparator;
import entity.seals.Seal;
import entity.transport.ActionTime;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import utils.ChangeLogUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 26.04.2019.
 */
public class TransportationComparator extends IChangeComparator<Transportation> {

    private Vehicle vehicle;
    private Driver driver;
    private ActionTime timeIn;
    private ActionTime timeOut;
    boolean isNew;

    @Override
    public void fix(Transportation oldObject) {
        isNew = oldObject == null;
        if (!isNew) {
            vehicle = oldObject.getVehicle();
            driver = oldObject.getDriver();
            timeIn = oldObject.getTimeIn();
            timeOut = oldObject.getTimeOut();
        }
    }

    @Override
    public void compare(Transportation newObject, Worker worker) {
        compare(vehicle, newObject.getVehicle(), "vehicle");
        compare(driver, newObject.getDriver(), "driver");
        compare(timeIn, newObject.getTimeIn(), "in");
        compare(timeOut, newObject.getTimeOut(), "out");
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
    }

    @Override
    public String getTitle() {
        return isNew ? "new" : "edit";
    }
}
