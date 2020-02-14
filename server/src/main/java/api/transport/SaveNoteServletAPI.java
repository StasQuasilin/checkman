package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.DocumentNote;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.NoteUtil;
import utils.U;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.SAVE_NOTE)
public class SaveNoteServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();
    final NoteUtil noteUtil = new NoteUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String noteText = String.valueOf(body.get("note"));
            if (U.exist(noteText)) {
                Object planId = body.get(PLAN);
                Transportation transportation = dao.getObjectById(Transportation.class, planId);
                noteText = noteText.replaceAll("  ", " ").trim().toLowerCase();


                DocumentNote note = new DocumentNote();
                note.setTime(new Timestamp(System.currentTimeMillis()));
                note.setCreator(getWorker(req));
                note.setTransportation(transportation);
                note.setDocument(transportation.getUid());
                note.setNote(noteUtil.checkNote(transportation, noteText));
                dao.save(note);
                updateUtil.onSave(transportation);
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
