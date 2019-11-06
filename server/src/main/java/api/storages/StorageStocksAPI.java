package api.storages;

import api.ServletAPI;
import constants.Branches;
import entity.documents.Shipper;
import entity.products.Product;
import entity.storages.Storage;
import entity.storages.StoragePeriodPoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.storages.PointScale;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 05.11.2019.
 */
@WebServlet(Branches.API.STORAGE_STOCKS)
public class StorageStocksAPI extends ServletAPI {

    private StorageUtil storageUtil = new StorageUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            String replace = String.valueOf(body.get(DATE)).replace('T', ' ').replace("Z", "");
            Timestamp date = Timestamp.valueOf(replace);

            PointScale scale;
            if (body.containsKey(SCALE)){
                scale = PointScale.valueOf(String.valueOf(body.get(SCALE)));
            } else {
                scale = PointScale.year;
            }

            Storage storage = dao.getStorageById(body.get(STORAGE));
            Product product = dao.getProductById(body.get(PRODUCT));

            ArrayList<Shipper> shippers = new ArrayList<>();
            if (body.containsKey(SHIPPER)){
                shippers.add(dao.getShipperById(body.get(SHIPPERS)));
            } else {
                shippers.addAll(dao.getShipperList());
            }
            ArrayList<StoragePeriodPoint> points = new ArrayList<>();
            for (Shipper shipper : shippers) {
                storageUtil.getStocks(
                        null,
                        date,
                        storage,
                        product,
                        shipper,
                        scale,
                        points
                );
            }
            JSONArray array = pool.getArray();
            for (StoragePeriodPoint point : points){
                array.add(parser.toJson(point));
            }
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
