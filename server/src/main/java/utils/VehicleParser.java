package utils;

import entity.Person;
import entity.transport.Driver;
import entity.transport.Trailer;
import entity.transport.Vehicle;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.List;

/**
 * Created by szpt_user045 on 16.07.2019.
 */
public class VehicleParser {

    final static dbDAO dao = dbDAOService.getDAO();
    private Parser parser = new Parser();

    public synchronized Vehicle parse(String data){

        List<String> vehicleData = parser.parseVehicle(data);
        Vehicle vehicle = dao.getVehicleByNumber(vehicleData.get(1));
        if (vehicle == null) {
            vehicle = new Vehicle();
            if (vehicleData.size() > 0) {
                String s = vehicleData.get(0);
                if (U.exist(s)) {
                    vehicle.setModel(s);
                }
                if (vehicleData.size() > 1) {
                    vehicle.setNumber(vehicleData.get(1));
                    if (vehicleData.size() > 2) {
                        String trailerNumber = vehicleData.get(2);
                        if (U.exist(trailerNumber)) {
                            Trailer trailer = new Trailer();
                            trailer.setNumber(trailerNumber);
                            vehicle.setTrailer(trailer);
                        }
                    }
                }
            }
            dao.save(vehicle);
        }
        return vehicle;
    }

    public synchronized Driver parseDriver(String key) {
        List<String> personData = Parser.parsePerson(key);
        System.out.println("Data: " + personData);

        Driver driver;
        Person person;
        String surname = null;
        String forename = null;
        String patronymic = null;
        if(personData.size() > 0) {
            surname = personData.get(0);
            if (personData.size() > 1){
                forename = personData.get(1);
                if(personData.size() > 2){
                    patronymic = personData.get(2);
                }
            }
        }

        person = dao.getPersonByName(surname, forename, patronymic);
        if (person == null){
            driver = new Driver();
            person = new Person();
            person.setSurname(surname);
            if (forename != null) {
                person.setForename(forename);
            }
            if (patronymic != null) {
                person.setPatronymic(patronymic);
            }
            driver.setPerson(person);
            dao.save(person, driver);
        } else {
            driver = dao.getDriverByPerson(person);
            if (driver == null){
                driver = new Driver();
                driver.setPerson(person);
                dao.save(driver);
            }
        }

        return driver;
    }

    public void parseTrailer(String key) {
        List<String> strings = parser.parseVehicle(key);
        if (strings.size() > 0){

        }
    }
}
