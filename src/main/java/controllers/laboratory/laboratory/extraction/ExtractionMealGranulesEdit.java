package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.subdivisions.extraction.MealGranules;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.01.2020.
 */
@WebServlet(Branches.UI.MEAL_GRANULES)
public class ExtractionMealGranulesEdit extends IModal {
    private static final String _CONTENT = "/pages/laboratory/subdivisions/extraction/mealGranules.jsp";
    private static final String _TITLE = "extraction.meal.granules";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            if (body.containsKey(ID)){
                MealGranules granules = dao.getObjectById(MealGranules.class, body.get(ID));
                req.setAttribute(ANALYSES, granules);
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(SAVE, Branches.API.MEAL_GRANULES);
        show(req, resp);
    }
}
