package entity;

import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonPool;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
public abstract class JsonAble {
    public static final String ID = Constants.ID;
    public static final String DATE = Constants.DATE;
    public static final String FROM = Constants.FROM;
    public static final String TO = Constants.TO;
    public static final String NAME = Constants.NAME;
    public static final String TYPE = Constants.TYPE;
    public static final String COUNTERPARTY = Constants.COUNTERPARTY;
    public static final String NUMBER = Constants.NUMBER;
    public static final String PRODUCT = Constants.PRODUCT;
    public static final String PRODUCTS = Constants.PRODUCTS;
    public static final String SHIPPER = Constants.SHIPPER;
    public static final String CUSTOMER = Constants.CUSTOMER;
    public static final String REGISTERED = Constants.REGISTERED;
    public static final String TIME_IN = Constants.TIME_IN;
    public static final String TIME_OUT = Constants.TIME_OUT;
    public static final String MANAGER = Constants.MANAGER;
    public static final String PERSON = Constants.PERSON;
    public static final String SURNAME = Constants.SURNAME;
    public static final String FORENAME = Constants.FORENAME;
    public static final String PATRONYMIC = Constants.PATRONYMIC;
    public static final String PHONES = Constants.PHONES;
    public static final String NOTE = Constants.NOTE;
    public static final String NOTES = Constants.NOTES;
    public static final String CREATOR = Constants.CREATOR;
    public static final String ARCHIVE = Constants.ARCHIVE;
    public static final String DONE = Constants.DONE;
    public static final String AMOUNT = Constants.AMOUNT;
    public static final String PRICE = Constants.PRICE;
    public static final String ANALYSES = Constants.ANALYSES;
    public static final String VALUE = Constants.VALUE;
    public static final String LICENSE = Constants.LICENSE;
    public static final String VEHICLE = Constants.VEHICLE;
    public static final String DRIVER = Constants.DRIVER;
    public static final String TRUCK = Constants.TRUCK;
    public static final String MODEL = Constants.MODEL;
    public static final String TRAILER = Constants.TRAILER;
    public static final String OWNER = Constants.OWNER;
    public static final String TRANSPORTER = Constants.TRANSPORTER;
    public static final String STATUS = Constants.STATUS;
    public static final String PLAN = Constants.PLAN;
    public static final String WEIGHT = Constants.WEIGHT;
    public static final String EMPTY_JSON = Constants.EMPTY_JSON;
    public static final String BRUTTO = Constants.BRUTTO;
    public static final String TARA = Constants.TARA;
    public static final String NETTO = Constants.NETTO;
    public static final String ANALYSES_TYPE = Constants.ANALYSES_TYPE;


    public static JsonPool pool = JsonPool.getPool();
    public abstract JSONObject toJson();
    public JSONArray toJson(Set<JsonAble> set){
        JSONArray array = pool.getArray();
        array.addAll(set.stream().map(JsonAble::toJson).collect(Collectors.toList()));
        return array;
    }
}
