package controllers.weight.printing;

import api.IAPI;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import constants.Branches;
import entity.documents.LoadPlan;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * Created by szpt_user045 on 24.04.2019.
 */
@WebServlet(Branches.UI.PRINT_DOCUMENT)
public class DocumentPrinter extends IAPI {

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
            LoadPlan plan = hibernator.get(LoadPlan.class, "id", id);
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
