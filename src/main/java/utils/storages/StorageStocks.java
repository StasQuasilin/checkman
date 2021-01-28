package utils.storages;

import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.UpdateUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public class StorageStocks {

    private final Logger log = Logger.getLogger(StorageStocks.class);
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

    public HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> getStocks() {
        log.info("<--------------Stocks-------------->");
        for (Map.Entry<Storage, HashMap<Product, HashMap<Shipper, Float>>> storageEntry : stocks.entrySet()){
            log.info(storageEntry.getKey().getName());
            for (Map.Entry<Product, HashMap<Shipper, Float>> productEntry : storageEntry.getValue().entrySet()){
                log.info("\t" + productEntry.getKey().getName());
                for (Map.Entry<Shipper, Float> entry : productEntry.getValue().entrySet()){
                    log.info("\t\t" + entry.getKey().getValue() + ":\t" + entry.getValue());
                }
            }
        }
        return stocks;
    }
}
