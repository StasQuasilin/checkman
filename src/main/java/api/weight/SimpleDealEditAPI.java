package api.weight;

import api.ServletAPI;
import api.deal.DealEditor;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.answers.Answer;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.weight.Unit;
import org.json.simple.JSONObject;
import utils.DealUtil;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.DealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(Branches.API.Transportation.DEAL_EDIT)
public class SimpleDealEditAPI extends ServletAPI {

    private final DealEditor dealEditor = new DealEditor();
    private final DealUtil dealUtil = new DealUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            Deal deal = dealEditor.editDeal(body, getWorker(req));
            Answer answer = new SuccessAnswer();
            answer.add(ID, deal.getId());
            answer.add(DEAL, deal.toJson());
            answer.add(DEALS, dealUtil.dealsToArray(deal.getOrganisation()));

            write(resp, answer);
        }

    }
}
