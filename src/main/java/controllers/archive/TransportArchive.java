package controllers.archive;

import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;
import entity.transport.TransportCustomer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.04.2019.
 */
@WebServlet(Branches.UI.TRANSPORT_ARCHIVE)
public class TransportArchive extends IUIServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArchiveType type = ArchiveType.valueOf(req.getParameter("type"));
        req.setAttribute(TITLE, Titles.ARCHIVE + "." + type.toString());

        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(EDIT, Branches.UI.WEIGHT_ADD);

        switch (type){
            case summary:
                req.setAttribute(CONTENT, "/pages/weight/weightList.jsp");
                req.setAttribute(SHOW, Branches.UI.SUMMARY_SHOW);
                break;
            case transportation:

                req.setAttribute(SHOW, Branches.UI.TRANSPORT_SHOW);
                break;
            case weight:{
                req.setAttribute(CONTENT, "/pages/weight/weightList.jsp");
                req.setAttribute(SHOW, Branches.UI.ARCHIVE_WEIGHT_SHOW);
                break;
            }
            case laboratory_buy:
            case laboratory_sell:
                req.setAttribute(CONTENT, "/pages/laboratory/laboratoryList.jsp");
                req.setAttribute(EDIT, Branches.UI.LABORATORY_SHOW);
                break;
            default:
                req.setAttribute(CONTENT, "/pages/archive/archiveTypeErr.jsp");
                req.setAttribute(TYPE, type);
                break;
        }
        req.setAttribute(CONTENT, "/pages/transport/transportList.jsp");
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        req.setAttribute("findOrganisations", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("findDrivers", Branches.API.References.FIND_DRIVER);
        req.setAttribute(FILTER, "/pages/filters/archiveFilter.jsp");
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(FIND, Branches.API.ARCHIVE_FIND);

        show(req, resp);
    }

}
