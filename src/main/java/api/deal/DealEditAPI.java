package api.deal;

import api.EditAPI;
import constants.Branches;
import entity.answers.Answer;
import entity.documents.Deal;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class DealEditAPI extends EditAPI {

    private final DealEditor dealEditor = new DealEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Deal deal = dealEditor.editDeal(body, getWorker(req));
            if (deal != null){
                Answer resultAnswer = new SuccessAnswer();
                resultAnswer.add(ID, deal.getId());
                write(resp, resultAnswer);
            }
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
