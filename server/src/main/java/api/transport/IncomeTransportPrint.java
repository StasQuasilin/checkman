package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.products.Product;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.hibernate.DateContainers.BETWEEN;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 01.11.2019.
 */
@WebServlet(Branches.API.INCOME_TRANSPORT_PRINT)
public class IncomeTransportPrint extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            Time begin = TurnBox.getTurnByNumber(1).getBegin();
            LocalDateTime beginDate = LocalDateTime.of(date.toLocalDate(), begin.toLocalTime());
            LocalDateTime endDate = beginDate.plusDays(1);
            Timestamp from = Timestamp.valueOf(beginDate);
            Timestamp to = Timestamp.valueOf(endDate);
            HashMap<String, Object> param = new HashMap<>();
            param.put("timeIn/time", new BETWEEN(from, to));

            HashMap<Product, ArrayList<Transportation>> transportations = new HashMap<>();
            for (Transportation t : dao.getObjectsByParams(Transportation.class, param)){
                Product product = t.getProduct();
                if (!transportations.containsKey(product)){
                    transportations.put(product, new ArrayList<>());
                }
                transportations.get(product).add(t);
            }

            for (ArrayList<Transportation> t : transportations.values()){
                t.sort((o1, o2) -> o1.getTimeIn().getTime().compareTo(o2.getTimeIn().getTime()));
            }
            req.setAttribute("transportations", transportations);
            req.setAttribute("from", from);
            req.setAttribute("to", to);
            req.getRequestDispatcher("/pages/transport/print/incomeTransport.jsp").forward(req, resp);
        }
    }
}
