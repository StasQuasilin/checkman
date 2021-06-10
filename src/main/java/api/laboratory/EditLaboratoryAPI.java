package api.laboratory;

import api.ServletAPI;
import constants.Branches;
import entity.AnalysesType;
import entity.Worker;
import entity.products.Product;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import utils.UpdateUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;
import utils.laboratory.OilAnalysesEditor;
import utils.laboratory.SunAnalysesEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_SUN)
public class EditLaboratoryAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditLaboratoryAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final SunAnalysesEditor sunEditor = new SunAnalysesEditor();
    private final OilAnalysesEditor oilEditor = new OilAnalysesEditor();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBodyGood(req);
        if (body != null) {
            System.out.println(body);
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
                        update = oilEditor.editAnalyses(transportationProduct, json.getObject(OIL), worker);
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
