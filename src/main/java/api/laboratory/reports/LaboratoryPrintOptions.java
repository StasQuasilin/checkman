package api.laboratory.reports;

import api.laboratory.ActNumberService;
import constants.Branches;
import controllers.IModal;
import entity.AnalysesType;
import entity.DealType;
import entity.Role;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.ActType;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Kvasik on 07.09.2019.
 */
@WebServlet(Branches.UI.LABORATORY_PRINT_OPTIONS)
public class LaboratoryPrintOptions extends IModal {


    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject good = parseBodyGood(req);
        if(good != null){
            req.setAttribute(TITLE, "title.laboratory.print.options");
            req.setAttribute(MODAL_CONTENT, "/pages/laboratory/reports/printOptions.jsp");
            final Object id = good.get(ID);
            final TransportationProduct transportationProduct = transportationDAO.getTransportationProduct(id);
            final AnalysesType analysesType = transportationProduct.getDealProduct().getProduct().getAnalysesType();
            int act = 0;
            final SunAnalyses sunAnalyses = transportationProduct.getSunAnalyses();
            if (sunAnalyses != null){
                act = sunAnalyses.getAct();
            }
            if (act == 0){
                final OilAnalyses oilAnalyses = transportationProduct.getOilAnalyses();
                if(oilAnalyses != null){
                    act = oilAnalyses.getAct();
                }
            }
            if (act == 0){
                act = dao.getActNumber(ActType.valueOf(analysesType.name() + "Act")).getNumber();
                act++;
            }
            req.setAttribute(PRODUCT, transportationProduct);
            req.setAttribute(TYPE, analysesType);
            req.setAttribute(NUMBER, act);
            req.setAttribute(PRINT, Branches.API.LABORATORY_OIL_PRINT);
            req.setAttribute(WORKERS, dao.getWorkersByRole(Role.analyser));
            show(req, resp);
        }
    }
}
