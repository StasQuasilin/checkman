package controllers.contracts;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.UI.CONTRACT_LIST)
public class ContractList extends IUIServlet {
    private static final String _TITLE = "title.deals.";
    private static final String _CONTENT = "/pages/contracts/contractList.jsp";
    public static final String SUBSCRIBE_PART = "CONTRACTS_";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter(TYPE);
        if (parameter != null){
            DealType type = DealType.valueOf(parameter);
            req.setAttribute(SUBSCRIBE, SUBSCRIBE_PART + type.toString().toUpperCase());
            req.setAttribute(TITLE, _TITLE + type.toString());
        }

        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(EDIT, Branches.UI.CONTRACT_EDIT);

        show(req, resp);
    }
}
