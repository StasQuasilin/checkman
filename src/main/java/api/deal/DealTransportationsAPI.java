package api.deal;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.Answer;
import entity.documents.DealProduct;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.answers.SuccessAnswer;
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dao.TransportationDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.DEAL_TRANSPORTATIONS)
public class DealTransportationsAPI extends ServletAPI {

    private final DealDAO dealDAO = new DealDAO();
    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            JSONArray array = new JSONArray();
            for (TransportationProduct transportationProduct : transportationDAO.getTransportationsByDealProduct(body.get(PRODUCT))){
                final Transportation transportation = transportationProduct.getTransportation();
                transportation.getProducts().remove(transportationProduct);
                final JSONObject object = transportation.toJson();
                object.putAll(transportationProduct.toJson());
                array.add(object);
            }
            Answer answer = new SuccessAnswer();
            answer.add(Constants.RESULT, array);
            write(resp, answer);
        }
    }
}
