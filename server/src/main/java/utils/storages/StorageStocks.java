package utils.storages;

import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageProduct;
import utils.UpdateUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public class StorageStocks {
    public static StorageStocks instance = new StorageStocks();

    private dbDAO dao = dbDAOService.getDAO();
    private HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> stocks = new HashMap<>();
    private StorageUtil storageUtil = new StorageUtil();
    private UpdateUtil updateUtil = new UpdateUtil();
    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
    private StorageStocks(){


        List<Shipper> shippers = dao.getShipperList();

        for (Storage storage : dao.getStorages()) {
            for (StorageProduct storageProduct : dao.getStorageProductByStorage(storage)) {
                Product product = storageProduct.getProduct();
                for (Shipper shipper : shippers){
                    float v = storageUtil.calculateStock(now, storage, product, shipper);
                    updateStock(now, storage, product, shipper, v);
                }
            }
        }
    }

    public synchronized void updateStock(Timestamp time, Storage storage, Product product, Shipper shipper, float value){
        if (time.after(now)) {
            now = time;
            if (!stocks.containsKey(storage)) {
                stocks.put(storage, new HashMap<>());
            }
            HashMap<Product, HashMap<Shipper, Float>> products = stocks.get(storage);
            if (!products.containsKey(product)) {
                products.put(product, new HashMap<>());
            }
            products.get(product).put(shipper, value);
            try {
                updateUtil.onUpdateStocks(storage, product, shipper, value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Storage, HashMap<Product, HashMap<Shipper, Float>>> getStocks() {
        return stocks;
    }
}
