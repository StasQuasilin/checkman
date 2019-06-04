package controllers.laboratory.laboratory;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@WebServlet(Branches.UI.LABORATORY_TURNS)
public class LaboratoryTurnList extends IUIServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.laboratory.turns");
        req.setAttribute("content", "/pages/laboratory/laboratoryTurns.jsp");
        req.setAttribute("edit", Branches.UI.LABORATORY_TURNS_EDIT);
        show(req, resp);
    }
}
