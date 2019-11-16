package utils.transport;

import entity.Person;
import entity.transport.*;
import org.apache.log4j.Logger;
import utils.U;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szpt_user045 on 16.11.2019.
 */
public class CollapseUtil {

    private static final dbDAO dao = dbDAOService.getDAO();
    private static final Logger log = Logger.getLogger(CollapseUtil.class);

    public static synchronized void collapse(List<Driver> drivers){
        Driver driver = null;
        Person person = null;
        ArrayList<Driver> removeDrivers = new ArrayList<>();
        ArrayList<Vehicle> removeVehicles = new ArrayList<>();
        ArrayList<Transportation> transportations = new ArrayList<>();

        for (Driver d : drivers){
            Person p = d.getPerson();
            if (driver == null){
                driver = d;
                person = driver.getPerson();
                log.info("Compare drivers " + person.getValue());
            } else {
                removeDrivers.add(d);
                log.info("Compare " + person.getValue() + ", id: " + driver.getId() + " to " + p.getValue() + ", id: " + d.getId());
            }

            driver.setLicense(compare(driver.getLicense(), d.getLicense()));
            person.setForename(compare(person.getForename(), p.getForename()));
            person.setPatronymic(compare(person.getPatronymic(), p.getPatronymic()));
            driver.setVehicle(compare(driver.getVehicle(), d.getVehicle(), removeVehicles));
            transportations.addAll(dao.getTransportationsByDriver(d));
        }
        if (driver != null) {
            dao.save(driver);
            log.info("Save driver : " + driver.getPerson().getValue());
            for (Transportation transportation : transportations) {
                TransportUtil.setDriver(transportation, driver);
                Vehicle vehicle = driver.getVehicle();
                if (vehicle != null) {

                    dao.save(vehicle);
                    TransportUtil.setVehicle(transportation, vehicle);
                    Trailer trailer = vehicle.getTrailer();
                    if (trailer != null){
                        dao.save(trailer);
                        TransportUtil.setTrailer(transportation, trailer);
                    }
                }
                log.info("Save transportation " + transportation.getId() + ", driver " + transportation.getDriver().getId());
                dao.save(transportation);

            }
        }
        for(Driver d : removeDrivers){
            log.info("Remove driver " + d.getPerson().getValue() + ", id:" + d.getId());
            try{
                dao.remove(d);
                dao.remove(d.getPerson());
            } catch (Exception ignore){}

        }
    }

    public static synchronized Vehicle compare(Vehicle v1, Vehicle v2, ArrayList<Vehicle> removeVehicles){
        Vehicle v;
        boolean compare = true;
        if (v1 == null && v2 == null){
            return null;
        } else if (v1 == null){
            v = v2;
            compare = false;
        } else if (v2 == null){
            v = v1;
            compare = false;
        } else {
            v = v1;
            removeVehicles.add(v2);
        }
        if  (compare) {
            v.setModel(compare(v1.getModel(), v2.getModel()));
            v.setNumber(compare(v1.getNumber(), v2.getNumber()));
            v.setTrailerNumber(compare(v1.getTrailerNumber(), v2.getTrailerNumber()));
            v.setTrailer(compare(v1.getTrailer(), v2.getTrailer()));
        }

        return v;
    }

    private static Trailer compare(Trailer t1, Trailer t2) {
        Trailer t;
        boolean compare = true;
        if (t1 == null && t2 == null){
            return null;
        } else if (t1 == null){
            t = t2;
            compare = false;
        } else if (t2 == null){
            t = t1;
            compare = false;
        } else {
            t = t1;
        }

        if (compare){
            t.setNumber(compare(t1.getNumber(), t2.getNumber()));
        }

        return t;
    }

    public static synchronized String compare(String s1, String s2){
        if (s1 == null){
            return s2;
        } else if (s2 == null){
            return s1;
        } else if (s1.length() > s2.length()){
            return s1;
        } else {
            return s2;
        }
    }


}
