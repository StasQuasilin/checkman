package controllers.warehousing;

import constants.Branches;
import controllers.IModal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kvasik on 07.10.2019.
 */

@WebServlet(Branches.UI.WAREHOUSING_EDIT)
public class WarehousingEdit extends IModal{

    public static final String _TITLE = "title.warehousing.edit";
    public static final String _CONTENT = "/pages/warehousing/warehousingEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null) {
            Transportation plan = dao.getObjectById(Transportation.class, body.get(ID));
            req.setAttribute(PLAN, plan);
            req.setAttribute(USED_STORAGES, dao.getUsedStoragesByTransportation(plan));
//            req.setAttribute(STORAGES, dao.getStorageProductByProduct(plan.getProduct()));
            req.setAttribute(SHIPPERS, dao.getShipperList());
            req.setAttribute(SAVE, Branches.API.WAREHOUSING_EDIT);
        }

        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
