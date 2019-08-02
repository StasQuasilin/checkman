package entity.log.comparators;

import entity.Worker;
import entity.log.IChangeComparator;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import utils.ChangeLogUtil;
import utils.DateUtil;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 26.04.2019.
 */
public class TransportationComparator extends IChangeComparator<Transportation> {

    private Integer vehicleId;
    private Vehicle vehicle;
    private Integer driverId;
    private Driver driver;
    private String timeIn;
    private String timeOut;
    private String archive;
    boolean isNew;

    private final HashMap<Boolean, String> archiveData = new HashMap<>();
    {
        archiveData.put(Boolean.TRUE, ".in");
        archiveData.put(Boolean.FALSE, ".out");
    }

    @Override
    public void fix(Transportation oldObject) {
        isNew = oldObject == null;
        if (!isNew) {
            if (oldObject.getVehicle() != null) {
                vehicle = oldObject.getVehicle();
                vehicleId = vehicle.getId();
            }
            if (oldObject.getDriver() != null) {
                driver = oldObject.getDriver();
                driverId = driver.getId();
            }
            if(oldObject.getTimeIn() != null) {
                timeIn = DateUtil.prettyDate(oldObject.getTimeIn().getTime());
            }
            if (oldObject.getTimeOut() != null) {
                timeOut = DateUtil.prettyDate(oldObject.getTimeOut().getTime());
            }
            archive = archiveData.get(oldObject.isArchive());
        } else{
            archive = archiveData.get(false);
        }
    }

    @Override
    public void compare(Transportation newObject, Worker worker) {
        Integer newVehicle = null;
        if (newObject.getVehicle() != null) {
            newVehicle = newObject.getVehicle().getId();
        }
        compare(vehicleId, vehicle, newVehicle, newObject.getVehicle(), "transportation.vehicle");
        Integer newDriver = null;
        if (newObject.getDriver() != null) {
            newDriver = newObject.getDriver().getId();
        }
        compare(driverId, driver, newDriver, newObject.getDriver(), "transportation.driver");
        if (newObject.getTimeIn() != null) {
            compare(timeIn, DateUtil.prettyDate(newObject.getTimeIn().getTime()), "transportation.in");
        }
        if (newObject.getTimeOut() != null) {
            compare(timeOut, DateUtil.prettyDate(newObject.getTimeOut().getTime()), "transportation.out");
        }
        compare(archive, archiveData.get(newObject.isArchive()), "transportation.archive" + archiveData.get(newObject.isArchive()));
        ChangeLogUtil.writeLog(newObject.getUid(), getTitle(), worker, changes);
    }

    @Override
    public String getTitle() {
        return isNew ? "transportation.new" : "transportation.edit";
    }
}
