package api.laboratory.reports;

import api.laboratory.ActNumberService;
import constants.Branches;
import controllers.IModal;
import entity.AnalysesType;
import entity.Role;
import entity.laboratory.transportation.ActType;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.PostUtil;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter(TYPE);
        if (parameter != null) {
            JSONObject json = PostUtil.parseBodyJson(req);
            if (json != null){
                req.setAttribute(TITLE, "title.laboratory.print.options");
                req.setAttribute(MODAL_CONTENT, "/pages/laboratory/reports/printOptions.jsp");
                Object id = json.get(ID);
                req.setAttribute(ID, id);

                AnalysesType type = AnalysesType.valueOf(parameter);
                req.setAttribute(TYPE, type.toString());

                Transportation transportation = dao.getTransportationById(id);
                if (transportation.getSunAnalyses() != null && transportation.getSunAnalyses().getAct() > 0) {
                    req.setAttribute(NUMBER, transportation.getSunAnalyses().getAct());
                } else if (transportation.getOilAnalyses() != null && transportation.getOilAnalyses().getAct() > 0){
                    req.setAttribute(NUMBER, transportation.getOilAnalyses().getAct());
                } else if (transportation.getMealAnalyses() != null && transportation.getMealAnalyses().getAct() > 0){
                    req.setAttribute(NUMBER, transportation.getMealAnalyses().getAct());
                } else {
                    int number = dao.getActNumber(ActType.valueOf(type.name() + "Act")).getNumber();
                    req.setAttribute(NUMBER, ++number);
                }


                req.setAttribute("workers", dao.getWorkersByRole(Role.analyser));

                req.setAttribute(PRINT, Branches.API.LABORATORY_OIL_PRINT);
                show(req, resp);
            }
        }
    }
}
