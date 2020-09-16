package controllers.api.deals;

import constants.Apis;
import constants.Keys;
import controllers.api.API;
import entity.transportations.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.transportations.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(Apis.DEAL_DETAILS)
public class DealDetailsAPI extends API {

    private final TransportationDAO transportationDAO = DaoService.getTransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            System.out.println(body);
            final Object product = body.get(Keys.DEAL_PRODUCT);
            JSONArray array = new JSONArray();
            for (Transportation transportation : transportationDAO.getTransportationsByDealProduct(product)){
                array.add(transportation.toJson());
            }
            JSONObject result = new JSONObject();
            result.put(Keys.TRANSPORTATIONS, array);

            answer = new SuccessAnswer();
            answer.addAttribute(Keys.RESULT, result);
        } else {
            answer = new ErrorAnswer();
        }
        write(resp, answer);
    }
}
