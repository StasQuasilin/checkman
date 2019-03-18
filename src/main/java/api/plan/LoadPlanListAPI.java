package api.plan;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Product;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST)
public class LoadPlanListAPI extends IAPI{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        int deal = Integer.parseInt(body.get(Constants.DEAL_ID));

        List<LoadPlan> plans = hibernator.query(LoadPlan.class, "deal", deal);
        JSONArray array = plans.stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
        PostUtil.write(resp, array.toJSONString());
    }
}
