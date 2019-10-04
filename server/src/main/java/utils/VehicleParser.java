package utils;

import entity.Person;
import entity.transport.Driver;
import entity.transport.Vehicle;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.List;

/**
 * Created by szpt_user045 on 16.07.2019.
 */
public class VehicleParser {

    final static dbDAO dao = dbDAOService.getDAO();

    public static synchronized Vehicle parse(String data){
        List<String> vehicleData = Parser.parseVehicle(data);
        Vehicle vehicle = new Vehicle();
        if (vehicleData.size() > 0) {
            vehicle.setModel(vehicleData.get(0));
            if (vehicleData.size() > 1) {
                vehicle.setNumber(vehicleData.get(1));
                if (vehicleData.size() > 2){
                    vehicle.setTrailer(vehicleData.get(2));
                }
            }
        }
        if (U.exist(vehicle.getNumber())){
            Vehicle vehicleByNumber = dao.getVehicleByNumber(vehicle.getNumber());
            if (vehicleByNumber != null) {
                vehicle = vehicleByNumber;
            } else {
                vehicleByNumber = dao.getVehicleByHash(vehicle.getHash());
                if (vehicleByNumber != null){
                    vehicle = vehicleByNumber;
                }
            }
        }
        return vehicle;
    }

    public static synchronized Driver parseDriver(String key) {
        List<String> personData = Parser.parsePerson(key);
        System.out.println("Data: " + personData);
        Driver driver = null;
        Person personByName = dao.getPersonByName(personData.get(0));
        System.out.println("Person " + personByName);
        if (personByName != null){
            driver = dao.getDriverByPerson(personByName);
            System.out.println("Driver "+ driver);
        }
        if (driver == null) {
            driver = new Driver();
            Person person = new Person();
            if(personData.size() > 0) {
                person.setSurname(personData.get(0));
                if (personData.size() > 1){
                    person.setForename(personData.get(1));
                    if(personData.size() > 2){
                        person.setPatronymic(personData.get(2));
                    }
                }
            }
            driver.setPerson(person);
        }
        return driver;
    }
}
