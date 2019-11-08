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
    public static final String FROM = Constants.FROM;
    public static final String TO = Constants.TO;
    public static final String NAME = Constants.NAME;
    public static final String TYPE = Constants.TYPE;
    public static final String COUNTERPARTY = Constants.COUNTERPARTY;
    public static final String NUMBER = Constants.NUMBER;
    public static final String PRODUCT = Constants.PRODUCT;
    public static final String PRODUCTS = Constants.PRODUCTS;
    public static final String SHIPPER = Constants.SHIPPER;
    public static final String ARCHIVE = Constants.ARCHIVE;
    public static final String DONE = Constants.DONE;
    public static final String AMOUNT = Constants.AMOUNT;
    public static final String PRICE = Constants.PRICE;
    public static final String ANALYSES = Constants.ANALYSES;
    public static final String VALUE = Constants.VALUE;

    public static JsonPool pool = JsonPool.getPool();
    public abstract JSONObject toJson();
    public JSONArray toJson(Set<JsonAble> set){
        JSONArray array = pool.getArray();
        array.addAll(set.stream().map(JsonAble::toJson).collect(Collectors.toList()));
        return array;
    }
}
