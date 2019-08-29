package api.laboratory.extraction;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 22.08.2019.
 */
@WebServlet(Branches.API.EXTRACTION_CRUDE_REMOVE)
public class ExtractionCrudeRemoveServletAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Object id = body.get(Constants.ID);
            ExtractionCrude crude = dao.getExtractionCrudeById(id);
            dao.remove(crude);
            write(resp, SUCCESS_ANSWER);
            updateUtil.onRemove(crude);
        }
    }
}
