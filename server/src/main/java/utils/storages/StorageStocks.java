package utils.storages;

import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.UpdateUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public class StorageStocks {
    private static StorageStocks instance = new StorageStocks();

    public static StorageStocks getInstance() {
        return instance;
    }

    HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> stocks = new HashMap<>();
    private StorageStocks(){}

    public synchronized void updateStock(Timestamp time, Storage storage, Product product, Shipper shipper, float value){
        if (!stocks.containsKey(storage)) {
            stocks.put(storage, new HashMap<>());
        }
        HashMap<Product, HashMap<Shipper, Float>> products = stocks.get(storage);
        if (!products.containsKey(product)) {
            products.put(product, new HashMap<>());
        }
        products.get(product).put(shipper, value);
    }



}
