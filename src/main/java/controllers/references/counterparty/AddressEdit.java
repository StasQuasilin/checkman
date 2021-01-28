package controllers.references.counterparty;

import constants.Branches;
import controllers.IModal;
import entity.organisations.Address;
import entity.organisations.AddressType;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@WebServlet(Branches.UI.ADDRESS_EDIT)
public class AddressEdit extends IModal {
    private static final String _TITLE = "title.address.edit";
    private static final String _CONTENT = "/pages/references/counterparty/addressEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            if (body.containsKey(COUNTERPARTY)){
                req.setAttribute(COUNTERPARTY, dao.getObjectById(Organisation.class, body.get(COUNTERPARTY)));
            }
            if (body.containsKey(TYPE)){
                AddressType type = AddressType.valueOf(String.valueOf(body.get(TYPE)));
                req.setAttribute(TYPE, type);
            }else {
                req.setAttribute(TYPE, AddressType.load);
            }
            req.setAttribute(ADDRESS, dao.getObjectById(Address.class, body.get(ID)));
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            req.setAttribute(ADDRESS_TYPES, AddressType.values());
            req.setAttribute(SAVE, Branches.API.References.ADDRESS_EDIT);
            show(req, resp);
        }
    }
}
