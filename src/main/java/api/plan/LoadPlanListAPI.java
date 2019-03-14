package api.plan;

import constants.Constants;
import entity.Product;
import utils.LanguageBase;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import java.util.UUID;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
public class LoadPlanListAPI {
    public static void main(String[] args) {
        HibernateSessionFactory.init();
        for (Product p : Hibernator.getInstance().query(Product.class, null)){
            System.out.println(p);
        }
        HibernateSessionFactory.shutdown();
    }
}
