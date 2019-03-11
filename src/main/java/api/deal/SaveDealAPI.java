package api.deal;

import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import utils.PostUtil;
import utils.hibernate.Hibernator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_SAVE)
public class SaveDealAPI extends HttpServlet{

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Deal deal;
        if (body.containsKey(Constants.ID)){
            int id = Integer.parseInt(body.get(Constants.ID));
            deal = hibernator.get(Deal.class, "id", id);
        } else {
            deal = new Deal();
        }




    }
}
