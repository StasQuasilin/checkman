package api.laboratory;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.AnalysesType;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import entity.transport.TransportUtil;
import utils.U;
import utils.UpdateUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;
import utils.laboratory.AnalysesEditor;
import utils.laboratory.SunAnalysesEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_SUN)
public class EditSunAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditSunAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final AnalysesEditor sunEditor = new SunAnalysesEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBodyGood(req);
        if (body != null) {
//            System.out.println(body);
            final Worker worker = getWorker(req);
            for (Object o : body.getArray(PRODUCTS)){
                final JsonObject json = new JsonObject(o);
                final TransportationProduct transportationProduct = transportationDAO.getTransportationProduct(json.get(ID));
                final Product product = transportationProduct.getDealProduct().getProduct();
                final AnalysesType analysesType = product.getAnalysesType();
                boolean update = false;
                switch (analysesType){
                    case sun:
                        update = sunEditor.editAnalyses(transportationProduct, json.getObject(SUN), worker);
                        break;
                    case oil:
                        break;
                }
                if (update){
                    updateUtil.onSave(transportationProduct.getTransportation());
                }
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
