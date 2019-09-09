package api.laboratory.reports;

import api.laboratory.ActNumberService;
import constants.Branches;
import controllers.IModal;
import entity.AnalysesType;
import entity.Role;
import entity.laboratory.transportation.ActType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 07.09.2019.
 */
@WebServlet(Branches.UI.LABORATORY_PRINT_OPTIONS)
public class LaboratoryPrintOptions extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("type");
        if (parameter != null) {
            req.setAttribute("title", "title.laboratory.print.options");
            req.setAttribute("modalContent", "/pages/laboratory/reports/printOptions.jsp");

            AnalysesType type = AnalysesType.valueOf(parameter);
            req.setAttribute("workers", dao.getWorkersByRole(Role.analyser));
            req.setAttribute("number", ActNumberService.getActNumber(ActType.valueOf(type.name() + "Act")));
            switch (type) {
                case sun:
                    req.setAttribute("print", Branches.API.LABORATORY_SUN_PRINT);
                    break;
                case oil:
                    req.setAttribute("print", Branches.API.LABORATORY_OIL_PRINT);
                    break;
                case cake:
                    req.setAttribute("print", Branches.API.LABORATORY_MEAL_PRINT);
                    break;
            }


            show(req, resp);
        }
    }
}
