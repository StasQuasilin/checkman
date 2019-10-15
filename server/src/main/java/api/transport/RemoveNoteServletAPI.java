package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.TransportationNote;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.REMOVE_NOTE)
public class RemoveNoteServletAPI extends ServletAPI {
    private static final long serialVersionUID = -4832633512044339450L;
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Object id = body.get(Constants.ID);
            TransportationNote note = dao.getTransportationNotesById(id);
            dao.remove(note);
            updateUtil.onSave(note.getTransportation());
        }
    }
}
