package utils.hibernate;

import entity.documents.LoadPlan;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.Weight;
import utils.U;
import utils.calculator.ProductLoadCalculator;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
        Hibernator instance = Hibernator.getInstance();

//        for (Transportation transportation : instance.query(Transportation.class, null)){
//            System.out.println(transportation.getDate() + ", " + (transportation.getWeight() != null ? transportation.getWeight().getNetto() : 0));
//        }

        ProductLoadCalculator calculator = new ProductLoadCalculator();
        HashMap<String, Object> param = new HashMap<>();
        for (Product p : instance.query(Product.class, null)){
            param.put("product", p);
            calculator.setProduct(p);
//            instance.sum(Transportation.class, param, "weight/brutto")
            System.out.println(p.getName()+ ": " + calculator.readValue());
        }

        HibernateSessionFactory.shutdown();
    }
}
