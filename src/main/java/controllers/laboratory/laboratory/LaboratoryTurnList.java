package controllers.laboratory.laboratory;

import api.sockets.Subscribe;
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

    final Subscribe[] subscribes = new Subscribe[]{Subscribe.LABORATORY_TURNS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.laboratory.turns");
        req.setAttribute("content", "/pages/laboratory/laboratoryTurns.jsp");
        req.setAttribute("edit", Branches.UI.LABORATORY_TURNS_EDIT);
        req.setAttribute("subscribe", subscribes);
        show(req, resp);
    }
}
