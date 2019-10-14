package controllers;

import constants.Constants;
import entity.Worker;
import utils.LanguageBase;
import utils.PostUtil;
import utils.hibernate.Hibernator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class IServlet extends HttpServlet {

    public static final String ID = Constants.ID;
    public static final String TITLE = Constants.TITLE;
    public static final String SUBSCRIBE = "subscribe";
    public static final String FILTER = "filter";
    public static final String EDIT = "edit";
    public static final String EDIT_STORAGE = "editStorage";
    public static final String TYPES = "types";
    public static final String CONTENT = "content";
    public static final String WEIGHT_SHOW = "";
    public static final String MODAL_CONTENT = "modalContent";
    public static final String STORAGE = Constants.STORAGE;
    public static final String STORAGE_PRODUCTS = Constants.STORAGE_PRODUCTS;
    public static final String PRODUCT = "product";
    public static final String PRODUCTS = "products";
    public static final String WEIGHT = Constants.WEIGHT;
    public static final String SAVE = Constants.SAVE;
    public static final String NUMBER = "number";
    public static final String TYPE = "type";
    public static final String MANAGER = "manager";
    public static final String CALCULATORS = "calculators";
    public static final String AMOUNT = "amount";
    public static final String PLAN = "plan";
    public static final String STORAGES = "storages";
    public static final String SHIPPERS = "shippers";

    public static final LanguageBase lb = LanguageBase.getBase();
    public Worker getWorker(HttpServletRequest req){
        return (Worker)req.getSession().getAttribute(Constants.WORKER);
    }

}
