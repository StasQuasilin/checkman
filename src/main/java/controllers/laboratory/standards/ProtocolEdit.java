package controllers.laboratory.standards;

import constants.Branches;
import controllers.IModal;
import entity.laboratory.Protocol;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.12.2019.
 */
@WebServlet(Branches.UI.PROTOCOL_EDIT)
public class ProtocolEdit extends IModal{

    private static final String _TITLE = "title.standard.edit";
    private static final String _CONTEXT = "/pages/laboratory/standards/standardEdit.jsp";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            Protocol standard = dao.getObjectById(Protocol.class, body.get(ID));
            if(standard != null){
                req.setAttribute(STANDARD, standard);
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTEXT);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(SAVE, Branches.API.PROTOCOL_EDIT);
        req.setAttribute(UPDATE, Branches.UI.PROTOCOL_LIST);
        show(req, resp);
    }
}
