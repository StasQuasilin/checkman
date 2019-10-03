package utils.hibernate;

import entity.documents.LoadPlan;
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
        for (LoadPlan plan : instance.query(LoadPlan.class, null)){
            plan.getTransportation().setManager(plan.getDeal().getCreator());
            instance.save(plan.getTransportation());
        }

        HibernateSessionFactory.shutdown();
    }
}
