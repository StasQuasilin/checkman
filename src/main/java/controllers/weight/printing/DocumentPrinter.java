package controllers.weight.printing;

import api.ServletAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by szpt_user045 on 24.04.2019.
 */
@WebServlet(Branches.UI.PRINT_DOCUMENT)
public class DocumentPrinter extends ServletAPI {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        long id = -1;

        if (body != null) {
            id = (long) body.get("id");
        } else {
            id = Long.parseLong(req.getParameter("id"));
        }

        if (id != -1) {
            LoadPlan plan = dao.getLoadPlanById(id);
            req.setAttribute("organisation", plan.getDeal().getOrganisation().getValue());
            req.setAttribute("product", plan.getDeal().getProduct().getName());
            req.setAttribute("weight", plan.getTransportation().getWeight());
            req.setAttribute("driver", plan.getTransportation().getDriver());
            req.setAttribute("vehicle", plan.getTransportation().getVehicle());
            req.setAttribute("from", plan.getDeal().getDocumentOrganisation());
            req.getRequestDispatcher("/pages/documents/waybill.jsp").forward(req, resp);
        }
    }
}
