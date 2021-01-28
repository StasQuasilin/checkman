package api.statistic;

import api.ServletAPI;
import constants.Branches;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.statistic.StatisticPeriodPoint;
import entity.storages.StoragePeriodPoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.storages.PointScale;
import utils.storages.StatisticUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 25.02.2020.
 */
@WebServlet(Branches.API.GET_STATISTIC)
public class GetUnloadStatisticAPI extends ServletAPI {

    private final StatisticUtil statisticUtil = new StatisticUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            ArrayList<StatisticPeriodPoint> points = new ArrayList<>();

            Date from = Date.valueOf(String.valueOf(body.get(FROM)));
            Date to = Date.valueOf(String.valueOf(body.get(TO)));

            Object organisation = null;
            if (body.containsKey(ORGANISATION)){
                organisation = body.get(ORGANISATION);
            }
            Product product = null;
            if (body.containsKey(PRODUCT)){
                product = dao.getObjectById(Product.class, body.get(PRODUCT));
            }
            for (Shipper shipper : dao.getShipperList()){
                statisticUtil.getStocks(from, to, organisation, product, shipper, PointScale.day, points);
            }

            JSONArray array = pool.getArray();
            for (StatisticPeriodPoint point : points){
                array.add(point.toJson());
            }
            write(resp, array.toJSONString());
            pool.put(array);
        }
    }
}
