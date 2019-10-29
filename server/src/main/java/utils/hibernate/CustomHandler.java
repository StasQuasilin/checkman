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
import utils.Parser;
import utils.U;
import utils.calculator.ProductLoadCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {

    static dbDAO dao = dbDAOService.getDAO();

    public static void main(String[] args) {
//        Hibernator instance = Hibernator.getInstance();

//        for (Transportation transportation : instance.query(Transportation.class, null)){
//            System.out.println(transportation.getDate() + ", " + (transportation.getWeight() != null ? transportation.getWeight().getNetto() : 0));
//        }

//        ProductLoadCalculator calculator = new ProductLoadCalculator();
//        HashMap<String, Object> param = new HashMap<>();
//        for (Product p : instance.query(Product.class, null)){
//            param.put("product", p);
//            calculator.setProduct(p);
////            instance.sum(Transportation.class, param, "weight/brutto")
//            System.out.println(p.getName()+ ": " + calculator.readValue());
//        }

//        for (Vehicle vehicle : instance.query(Vehicle.class, null)){
//            String number = vehicle.getNumber();
//            if (U.exist(number)){
//                vehicle.setNumber(Parser.prettyNumber(pretty(number)));
//            }
//            String trailer = vehicle.getTrailer();
//            if (U.exist(trailer)){
//                vehicle.setTrailer(Parser.prettyNumber(pretty(trailer)));
//            }
//            dao.save(vehicle);
//        }

        String value = "ВМ 17-55 АХ";
        StringBuilder builder = new StringBuilder();
        for (char c : value.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c) || Character.isSpaceChar(c)){
                builder.append(c);
            }
        }
        value = builder.toString();
        dao.findVehicle(value);
        HibernateSessionFactory.shutdown();
    }

    static String pretty(String number){
        StringBuilder builder = new StringBuilder();
        for (char c : number.toCharArray()){
            if (Character.isDigit(c) || Character.isLetter(c)){
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
