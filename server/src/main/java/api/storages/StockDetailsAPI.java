package api.storages;

import api.ServletAPI;
import constants.Branches;
import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StorageEntry;
import entity.storages.StoragePeriodPoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.storages.PointScale;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 03.01.2020.
 */

@WebServlet(Branches.API.STORAGE_STOCKS_DETAILS)
public class StockDetailsAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            PointScale scale = PointScale.valueOf(String.valueOf(body.get(SCALE)));
            PointScale prev = StorageUtil.prevScale(scale);
            Date from = Date.valueOf(String.valueOf(body.get(DATE)));
            Date to = Date.valueOf(StorageUtil.getEndDate(from.toLocalDate(), scale));
            Storage storage = dao.getObjectById(Storage.class, body.get(STORAGE));
            Product product = dao.getObjectById(Product.class, body.get(PRODUCT));

            JSONArray array = pool.getArray();
            if (prev == scale){
                to = Date.valueOf(to.toLocalDate().plusDays(1));
                for (Shipper shipper : dao.getShipperList()){
                    for (StorageEntry entry : dao.getStorageEntries(from, to, storage, product, shipper)){
                        JSONObject json = entry.toJson();
                        array.add(json);
                    }
                }
            } else {
                for (Shipper shipper : dao.getShipperList()){
                    for (StoragePeriodPoint point : dao.getStoragePoints(from, to, storage, product, shipper, prev)){
                        array.add(point.toJson());
                    }
                }
            }

            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
