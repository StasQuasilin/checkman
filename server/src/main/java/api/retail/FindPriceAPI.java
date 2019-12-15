package api.retail;

import api.ServletAPI;
import constants.Branches;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.12.2019.
 */
@WebServlet(Branches.API.FIND_PRICE)
public class FindPriceAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(FindPriceAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){

            Object counterparty = body.get(COUNTERPARTY);
            Object product = body.get(PRODUCT);

            log.info("Find price: counterparty=" + counterparty + ", product=" + product);
            float price = dao.findPrice(counterparty, product);
            JSONObject json = new SuccessAnswer(PRICE, price).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
