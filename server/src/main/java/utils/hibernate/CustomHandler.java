package utils.hibernate;

import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import utils.U;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
        ManufactureReport manufactureReport = dao.getLimitManufactureReports().get(0);
        U.sort(manufactureReport.getFields());
        ReportFieldCategory category = null;
        for (ReportField reportField : manufactureReport.getFields()){
            if ((category != reportField.getCategory())){
                category = reportField.getCategory();
                if (category != null) {
                    System.out.println("<---> " + category.getTitle() + " <--->");
                }
            }
            if (U.exist(reportField.getTitle())) {
                System.out.print(reportField.getTitle());
            }
            if (reportField.getStorage() != null){
                System.out.print(reportField.getStorage().getName());
            }

            System.out.println(reportField.getValue());
        }
        HibernateSessionFactory.shutdown();
    }
}
