package controllers.transport;

import constants.Branches;
import controllers.IModal;
import controllers.IUIServlet;
import entity.transport.DocumentNote;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.NOTE_EDIT)
public class NoteEdit extends IModal {

    private static final String _TITLE = "titles.note.edit";
    private static final String _CONTENT = "/pages/transport/noteEdit.jsp";
    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject good = parseBodyGood(req);
        if(good != null){
            req.setAttribute(NOTE, dao.getObjectById(DocumentNote.class, good.get(NOTE)));
            req.setAttribute(TRANSPORTATION, transportationDAO.getTransportation(good.get(TRANSPORTATION)));
            req.setAttribute(SAVE, Branches.API.NOTE_EDIT);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            show(req, resp);
        }
    }
}
