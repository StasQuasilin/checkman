package api.deal;

import api.ServletAPI;
import constants.Branches;
import entity.answers.Answer;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DealUtil;
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

    final DealUtil dealUtil = new DealUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Answer answer = new SuccessAnswer();
            JSONArray array = dealUtil.dealsToArray(body.get(ORGANISATION));
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
