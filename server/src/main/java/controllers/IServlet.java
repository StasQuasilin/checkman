package controllers;

import constants.Constants;
import entity.Worker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
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
    public static final String DATE = Constants.DATE;
    public static final String TURN = Constants.TURN;
    public static final String TURNS = "turns";
    public static final String SUBSCRIBE = "subscribe";
    public static final String FILTER = "filter";
    public static final String CANCEL = "cancel";
    public static final String ADD = "add";
    public static final String PRINT = "print";
    public static final String SHOW = "show";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";
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
    public static final String DIRECTION = "dir";
    public static final String STORAGES = "storages";
    public static final String SHIPPERS = "shippers";
    public static final String PROTEIN = "protein";
    public static final String IN = "in";
    public static final String OUT = "out";
    public static final String BRUTTO = "brutto";
    public static final String TARA = "tara";
    public static final String NETTO = "netto";

    public static final LanguageBase lb = LanguageBase.getBase();
    protected static final String CUSTOMERS = "customers";

    public Worker getWorker(HttpServletRequest req){
        return (Worker)req.getSession().getAttribute(Constants.WORKER);
    }

    public static final JsonParser parser = new JsonParser();
    public JSONObject parseBody(HttpServletRequest req){
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (IOException | ParseException ignore) { }
        return null;
    }

}
