package controllers.retail;

import constants.Branches;
import controllers.IModal;
import entity.transport.Transportation2;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 25.12.2019.
 */
@WebServlet(Branches.UI.RETAIL_REMOVE)
public class RetailRemove extends IModal {
    private static final String _TITLE = "title.retail.remove";
    private static final String _CONTENT = "/pages/retail/retailRemove.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
            req.setAttribute(TRANSPORTATION, transportation);
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(REMOVE, Branches.API.RETAIL_REMOVE);
        req.setAttribute(ARCHIVE, Branches.API.RETAIL_ARCHIVE);
        show(req, resp);
    }
}
