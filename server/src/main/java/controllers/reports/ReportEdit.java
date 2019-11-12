package controllers.reports;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.calculator.Calculator;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT_EDIT)
public class ReportEdit extends IModal {
    ;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;
        String parameter = req.getParameter(Constants.ID);
        if (parameter != null){
            id = Integer.parseInt(parameter);
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null && body.containsKey(Constants.ID)){
                id = Integer.parseInt((String) body.get(Constants.ID));
            }
        }

        if (id != -1){
            req.setAttribute("report", dao.getManufactureReport(id));
        }

        ArrayList<ReportFieldSettings> settings = new ArrayList<>(dao.getObjects(ReportFieldSettings.class));
        settings.sort((o1, o2) -> o1.getIndex() - o2.getIndex());

        req.setAttribute(TITLE, "title.manufacture.reports.edit");
        req.setAttribute(MODAL_CONTENT, "/pages/reports/manufactureReportEdit.jsp");
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(FIELDS, settings);
        req.setAttribute("units", dao.getWeightUnits());
        req.setAttribute("storages", dao.getStorages());
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(CALCULATORS, Calculator.calculatorList());
        req.setAttribute("categories", dao.getReportCategories());
        req.setAttribute(SAVE, Branches.API.SAVE_MANUFACTURE_REPORT);
        req.setAttribute(PREVIEW, Branches.UI.MANUFACTURE_REPORT_PREVIEW);
        show(req, resp);
    }
}
