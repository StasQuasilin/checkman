package controllers.api.deals;

import constants.Apis;
import constants.Keys;
import controllers.api.API;
import entity.deals.DealDocument;
import entity.deals.DealProduct;
import entity.transportations.TransportCustomer;
import entity.transportations.Transportation;
import entity.transportations.TransportationDocument;
import entity.transportations.TransportationProduct;
import utils.answer.Answer;
import utils.answer.ErrorAnswer;
import utils.answer.SuccessAnswer;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;
import utils.db.dao.transportations.TransportationDAO;
import utils.json.JsonObject;
import utils.savers.TransportationSaver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

@WebServlet(Apis.SAVE_TRANSPORTATION)
public class SaveTransportationAPI extends API {

    private final TransportationDAO transportationDAO = DaoService.getTransportationDAO();
    private final DealDAO dealDAO = DaoService.getDealDAO();
    private final TransportationSaver transportationSaver = new TransportationSaver();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            System.out.println(body);
            Transportation transportation = transportationDAO.getTransportationById(body.get(Keys.ID));
            if (transportation == null){
                transportation = new Transportation();
            }

            Date date = body.getDate(Keys.DATE);
            transportation.setDate(date);

            TransportCustomer customer = TransportCustomer.valueOf(body.getString(Keys.CUSTOMER));
            transportation.setCustomer(customer);

            final HashMap<Integer, TransportationDocument> documentHashMap = new HashMap<>();
            final HashMap<Integer, TransportationProduct> productHashMap = new HashMap<>();

            for (TransportationDocument document : transportation.getDocuments()){
                documentHashMap.put(document.getDealDocument().getId(), document);
                for (TransportationProduct product : document.getProducts()){
                    productHashMap.put(product.getDealProduct().getId(), product);
                }
            }

            final DealProduct dealProduct = dealDAO.getDealProduct(body.get(Keys.DEAL_PRODUCT));
            TransportationProduct product = productHashMap.remove(dealProduct.getId());
            if (product == null){
                product = new TransportationProduct();
                final DealDocument dealDocument = dealProduct.getDocument();
                TransportationDocument transportationDocument = documentHashMap.remove(dealDocument.getId());
                if (transportationDocument == null){
                    transportationDocument = new TransportationDocument();
                    transportationDocument.setTransportation(transportation);
                    transportationDocument.setDealDocument(dealDocument);
                }
                product.setDealProduct(dealProduct);
                product.setDocument(transportationDocument);
            }


            float amount = body.getFloat(Keys.AMOUNT);
            product.setAmount(amount);

            transportationSaver.save(product);

            answer = new SuccessAnswer();
            answer.addAttribute(Keys.KEY, body.getString(Keys.KEY));
            answer.addAttribute(Keys.ID, transportation.getId());
        } else {
            answer = new ErrorAnswer();
        }
        write(resp, answer);
    }
}
