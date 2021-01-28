package api.storages;

import api.ServletAPI;
import constants.Branches;
import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.StorageStockCorrection;
import entity.storages.Storage;
import entity.storages.StorageStockCorrectionDocument;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.storages.StorageStocks;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 02.01.2020.
 */
@WebServlet(Branches.API.PRODUCT_STOCK_CORRECTION)
public class StorageStockCorrectionAPI extends ServletAPI {

    StorageUtil util = new StorageUtil();
    private final Logger log = Logger.getLogger(StorageStockCorrectionAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            log.info(body);
            StorageStockCorrectionDocument document = dao.getObjectById(StorageStockCorrectionDocument.class, body.get(ID));
            boolean saveDocument = false;
            boolean newDocument = false;
            if (document == null){
                document = new StorageStockCorrectionDocument();
                document.setCreateTime(new ActionTime(getWorker(req)));
                newDocument = true;
            }
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            if (document.getDate() == null || !document.getDate().equals(date)){
                document.setDate(date);
                saveDocument = true;
            }

            final HashMap<Integer, StorageStockCorrection> correctionHashMap = new HashMap<>();
            if (document.getCorrections() != null) {
                for (StorageStockCorrection ssc : document.getCorrections()) {
                    correctionHashMap.put(ssc.getId(), ssc);
                }
            }
            final ArrayList<StorageStockCorrection> corrections = new ArrayList<>();

            for (Object o : (JSONArray)body.get(VALUES)) {
                JSONObject json = (JSONObject) o;
                boolean save = false;
                int id = Integer.parseInt(String.valueOf(json.get(ID)));
                StorageStockCorrection storageStockCorrection;
                if (correctionHashMap.containsKey(id)){
                    storageStockCorrection = correctionHashMap.remove(id);
                } else {
                    storageStockCorrection = new StorageStockCorrection();
                    storageStockCorrection.setDocument(document);
                }
                Storage storage = dao.getObjectById(Storage.class, json.get(STORAGE));
                if (storageStockCorrection.getStorage() == null || storageStockCorrection.getStorage().getId() != storage.getId()) {
                    storageStockCorrection.setStorage(storage);
                    save = true;
                }
                Product product = dao.getObjectById(Product.class, json.get(PRODUCT));
                if (storageStockCorrection.getProduct() == null || storageStockCorrection.getProduct().getId() != product.getId()){
                    storageStockCorrection.setProduct(product);
                    save = true;
                }

                Shipper shipper = dao.getShipperById(json.get(SHIPPER));
                if (storageStockCorrection.getShipper() == null || storageStockCorrection.getShipper().getId() != shipper.getId()){
                    storageStockCorrection.setShipper(shipper);
                    save=true;
                }
                float correction = Float.parseFloat(String.valueOf(json.get(CORRECTION)));
                if (storageStockCorrection.getCorrection() != correction){
                    storageStockCorrection.setCorrection(correction);
                    save = true;
                }
                if (save){
                    corrections.add(storageStockCorrection);
                }
            }
            if (saveDocument && corrections.size() > 0){
                if (newDocument){
                    dao.save(document.getCreateTime());
                }
                dao.save(document);

            }
            for(StorageStockCorrection correction : corrections){
                dao.save(correction);
                util.updateStorageEntry(correction);
            }
            for (StorageStockCorrection correction : correctionHashMap.values()){
                dao.remove(correction);
                util.removeStorageEntry(correction);
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
