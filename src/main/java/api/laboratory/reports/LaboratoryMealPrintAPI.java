package api.laboratory.reports;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 14.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_MEAL_PRINT)
public class LaboratoryMealPrintAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long id = -1;
            if (body.containsKey("id")) {
                id = (long) body.get("id");
            }
            if (id != -1) {
                LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);

                req.setAttribute("plan", plan);
                req.getRequestDispatcher("/pages/laboratory/reports/mealReport.jsp").forward(req, resp);
            }

        }
    }
}
