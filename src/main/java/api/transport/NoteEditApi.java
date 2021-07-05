package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.transport.DocumentNote;
import entity.transport.Transportation;
import utils.U;
import utils.UpdateUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;
import utils.transport.NoteEditor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(Branches.API.NOTE_EDIT)
public class NoteEditApi extends ServletAPI {

    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject good = parseBodyGood(req);
        if (good != null){
            final Transportation transportation = transportationDAO.getTransportation(good.get(TRANSPORTATION));
            final NoteEditor noteEditor = new NoteEditor(transportation);
            final JsonObject jsonObject = good.getObject(NOTE);
            final Worker worker = getWorker(req);
            final boolean save = noteEditor.saveNote(jsonObject, worker);
            if (save){
                noteEditor.getNotes();
                updateUtil.onSave(transportation);
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
