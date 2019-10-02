package utils.hibernate;

import entity.organisations.Organisation;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.transport.Vehicle;
import utils.U;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();
        for (Vehicle vehicle : instance.query(Vehicle.class, null)){
            vehicle.calculateHash();
            instance.save(vehicle);
        }

        HibernateSessionFactory.shutdown();
    }
}
