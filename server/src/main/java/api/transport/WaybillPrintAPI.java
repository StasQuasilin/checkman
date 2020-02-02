package api.transport;

import api.ServletAPI;
import constants.Branches;
import entity.organisations.Address;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Kvasik on 01.02.2020.
 */
@WebServlet(Branches.API.WAYBILL_PRINT)
public class WaybillPrintAPI extends ServletAPI {

    public static final String WAYBILL_PAGE = "/pages/weight/waybillPage.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            req.setAttribute(NUMBER, body.get(NUMBER));
            req.setAttribute(DATE, body.get(DATE));
            req.setAttribute(TRANSPORTATION, dao.getObjectById(Transportation.class, body.get(TRANSPORTATION)));
            req.setAttribute(ADDRESS, dao.getObjectById(Address.class, body.get(ADDRESS)));
            req.getRequestDispatcher(WAYBILL_PAGE).forward(req, resp);
        }
    }
}
