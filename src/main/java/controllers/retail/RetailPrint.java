package controllers.retail;

import constants.Branches;
import controllers.IModal;
import entity.deal.Contract;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.12.2019.
 */
@WebServlet(Branches.UI.RETAIL_PRINT)
public class RetailPrint extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Contract contract = dao.getObjectById(Contract.class, body.get(CONTRACT));
            if (contract != null){

            }
        }
    }
}
