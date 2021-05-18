package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Role;
import entity.documents.Deal;
import entity.products.ProductAction;
import entity.transport.DocumentNote;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by szpt_user045 on 18.04.2019.
 */
@WebServlet(Branches.UI.WEIGHT_ADD)
public class WeightAdd extends IModal {

    private static final String _TITLE_EDIT = "title.weight.edit";
    private static final String _TITLE_COPY = "title.weight.copy";
    private static final String _TITLE_ADD = "title.weight.add";
    private static final String _CONTENT = "/pages/weight/weightAdd.jsp";
    private static final String EDIT_TRAILER = "editTrailer";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long id = -1;
        long copy = -1;
        if (body != null) {
            if (body.containsKey(ID)) {
                id = Long.parseLong(String.valueOf(body.get(ID)));
            } else if (body.containsKey(COPY)) {
                copy = Long.parseLong(String.valueOf(body.get(COPY)));
            }
        }
        Transportation transportation = null;
        if (id != -1) {
            transportation = dao.getObjectById(Transportation.class, id);
            req.setAttribute(TRANSPORTATION, transportation);
            req.setAttribute(ADDRESS, dao.getLoadAddress(transportation.getCounterparty()));
            req.setAttribute(TITLE, _TITLE_EDIT);
        } else if (copy != -1) {
            transportation = dao.getObjectById(Transportation.class, copy);
            req.setAttribute(ADDRESS, dao.getLoadAddress(transportation.getCounterparty()));
            transportation.setId(-1);
            transportation.setDate(Date.valueOf(LocalDate.now()));
            Deal deal = transportation.getDeal();
            if (deal.isArchive()){
                deal.setId(-1);
            }
            List<DocumentNote> notes = transportation.getNotes();
            for (int i = 0; i < notes.size();){
                if (notes.get(i).getCreator() == null){
                    notes.remove(i);
                } else {
                    i++;
                }
            }

            req.setAttribute(TRANSPORTATION, transportation);
            req.setAttribute(TITLE, _TITLE_COPY);
        } else {
            req.setAttribute(TITLE, _TITLE_ADD);
        }
        if (transportation != null){
            Deal deal = transportation.getDeal();
            req.setAttribute(DEAL, deal);
        }
        req.setAttribute("managers", dao.getWorkersByRole(Role.manager));
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(FIND_DEALS, Branches.API.FIND_DEALS);
        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        req.setAttribute(Constants.SELECT_COUNTERPARTY, Branches.UI.SELECT_COUNTERPARTY);
        req.setAttribute(Constants.DEAL_EDIT, Branches.UI.Transportation.DEAL_EDIT);
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);

        req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
        req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
        req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);

        req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
        req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
        req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);

        req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
        req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
        req.setAttribute(EDIT_TRAILER, Branches.UI.TRAILER_EDIT);

        req.setAttribute(SAVE, Branches.API.PLAN_LIST_ADD);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(UNITS, dao.getUnitsList());
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(CUSTOMERS, new TransportCustomer[]{TransportCustomer.szpt, TransportCustomer.cont});
        req.setAttribute(EDIT_ADDRESS, Branches.UI.ADDRESS_EDIT);
        req.setAttribute(FIND_LOAD_ADDRESS, Branches.API.References.FIND_LOAD_ADDRESS);

        show(req, resp);
    }
}
