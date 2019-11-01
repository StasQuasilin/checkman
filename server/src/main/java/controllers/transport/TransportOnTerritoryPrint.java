package controllers.transport;

import constants.Branches;
import controllers.IModal;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 01.11.2019.
 */
@WebServlet(Branches.UI.PRINT_ON_TERRITORY)
public class TransportOnTerritoryPrint extends IModal {
    private static final String _TITLE = "title.transport.on.territory";
    private static final String _CONTENT = "/pages/transport/onTerritoryPrint.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(PRINT, Branches.API.ON_TERRITORY_PRINT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        show(req, resp);
    }
}
