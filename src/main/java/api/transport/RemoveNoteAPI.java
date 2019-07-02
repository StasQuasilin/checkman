package api.transport;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.transport.TransportationNote;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.REMOVE_NOTE)
public class RemoveNoteAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Object id = body.get(Constants.ID);
            TransportationNote note = dao.getTransportationNotesById(id);
            dao.remove(note);
        }
    }
}
