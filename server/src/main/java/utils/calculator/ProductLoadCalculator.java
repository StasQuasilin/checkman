package utils.calculator;

import constants.Constants;
import entity.products.Product;
import entity.transport.Transportation;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.GE;
import utils.hibernate.DateContainers.LE;

import java.sql.Date;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 08.10.2019.
 */
public class ProductLoadCalculator extends Calculator {

    public static final String KEY = "S3P36nIiix";
    private static final String TITLE = "calculator.load.products.title";
    public static final String DESCRIPTION = "calculator.load.products.description";

    private Product product;
    private Date from, to;
    HashMap<String, Object> param;

    public static final String PRODUCT = Constants.PRODUCT;
    public static final String DATE = Constants.DATE;
    public static final String WEIGHT_BRUTTO = Constants.WEIGHT_BRUTTO;
    public static final String WEIGHT_TARA = Constants.WEIGHT_TARA;

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public float readValue() {
        param = new HashMap<>();
        if (product != null) {
            param.put(PRODUCT, product);
        }
        if (from != null && to != null){
            param.put(DATE, new BETWEEN(from, to));
        }else if (from != null){
            param.put(DATE, new GE(from));
        } else if (to != null){
            param.put(DATE, new LE(to));
        }

        return dao.sum(Transportation.class, param.size() > 0 ? param : null, WEIGHT_BRUTTO) -
                dao.sum(Transportation.class, param.size() > 0 ? param : null, WEIGHT_TARA);
    }
}
