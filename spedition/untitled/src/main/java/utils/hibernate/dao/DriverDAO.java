package utils.hibernate.dao;

import constants.Keys;
import entity.Driver;
import utils.hibernate.Hibernator;

public class DriverDAO {
    private final Hibernator hibernator = Hibernator.getInstance();

    public Driver getDriverByUUID(Object uuid){
        return hibernator.get(Driver.class, Keys.UUID, uuid);
    }

    public void save(Driver driver) {
        hibernator.save(driver.getPerson());
        hibernator.save(driver);
    }
}
