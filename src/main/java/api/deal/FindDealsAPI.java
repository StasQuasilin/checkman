package api.deal;

import api.ServletAPI;
import constants.Branches;
import entity.answers.Answer;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.FIND_DEALS)
public class FindDealsAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            List<Deal> deals = dao.getDealsByOrganisation(body.get(ORGANISATION));
            Answer answer = new SuccessAnswer();

            JSONArray array = pool.getArray();
            for (Deal deal : deals){
                array.add(deal.toJson());
            }
            answer.add(RESULT, array);
            for (Object key : body.keySet()){
                answer.add(key.toString(), body.get(key));
            }
            write(resp, answer);
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
