package api.retail;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Transportation2;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.12.2019.
 */
@WebServlet(Branches.API.RETAIL_WAYBILL_PRINT)
public class WaybillPrintAPI extends ServletAPI {
    private static final String _CONTENT = "/pages/retail/retailWaybillPrint.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Transportation2 transportation = dao.getObjectById(Transportation2.class, body.get(ID));
            req.setAttribute(TRANSPORTATION, transportation);
            req.getRequestDispatcher(_CONTENT).forward(req, resp);
        }
    }
}
