package controllers.transport;

import constants.Keys;
import constants.Urls;
import controllers.Modal;
import entity.transportations.Transportation;
import utils.db.dao.DaoService;
import utils.db.dao.transportations.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Urls.RETAIL_EDIT)
public class TransportEdit extends Modal {
    private static final String _TITLE = "title.transportation.edit";
    private static final String _CONTENT = "/pages/transport/transportEdit.jsp";

    private final TransportationDAO transportationDAO = DaoService.getTransportationDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final Object id = body.get(Keys.ID);
            final Transportation transportation = transportationDAO.getTransportationById(id);
            req.setAttribute(Keys.TRANSPORTATION, transportation);
        }
        req.setAttribute(Keys.TITLE, _TITLE);
        req.setAttribute(Keys.CONTENT, _CONTENT);
        show(req, resp);
    }
}
