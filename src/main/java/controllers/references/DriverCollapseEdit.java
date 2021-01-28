package controllers.references;

import constants.Branches;
import controllers.IModal;
import entity.transport.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 16.11.2019.
 */
@WebServlet(Branches.UI.DRIVER_COLLAPSE)
public class DriverCollapseEdit extends IModal {
    private static final String _TITLE = "title.drivers.collapse";
    private static final String _CONTENT = "/pages/references/drivers/driversCollapse.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            ArrayList<Driver> drivers = new ArrayList<>();
            for (Object o : (JSONArray)body.get("selected")){
                JSONObject driver = (JSONObject) o;
                drivers.add(dao.getObjectById(Driver.class, driver.get(ID)));
            }
            req.setAttribute("drivers", drivers);
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(SAVE, Branches.API.DRIVERS_COLLAPSE);
            show(req, resp);
        }
    }
}
