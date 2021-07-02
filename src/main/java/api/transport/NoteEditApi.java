package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.transport.DocumentNote;
import utils.U;
import utils.UpdateUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(Branches.API.NOTE_EDIT)
public class NoteEditApi extends ServletAPI {

    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject good = parseBodyGood(req);
        if (good != null){
            final JsonObject jsonObject = good.getObject(NOTE);
            final String note = good.getString(NOTE);
            DocumentNote documentNote = transportationDAO.getNote(jsonObject.get(ID));
            if(documentNote == null){
                documentNote = new DocumentNote();
                documentNote.setTime(Timestamp.valueOf(LocalDateTime.now()));
            } else if(!U.exist(note)){
                dao.remove(note);
            }
            final String n = documentNote.getNote();
            if (n == null || !n.equals(note)){
                documentNote.setNote(note);
            }
        }
    }
}
