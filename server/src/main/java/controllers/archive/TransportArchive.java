package controllers.archive;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;

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

    static final String CONTENT = "content";
    static final String SUBSCRIBE = "subscribe";
    static final String EDIT = "edit";

    final Subscriber[] summaryArchiveSubscriber = new Subscriber[]{
            Subscriber.LOAD_PLAN_ARCHIVE
    };
    final Subscriber[] transportArchiveSubscriber = new Subscriber[]{
            Subscriber.TRANSPORT_BUY_ARCHIVE,
            Subscriber.TRANSPORT_SELL_ARCHIVE
    };
    final Subscriber[] weightArchiveSubscribe = new Subscriber[]{
            Subscriber.LOAD_PLAN_ARCHIVE
    };
    final Subscriber[] laboratoryBuyArchiveSubscriber = new Subscriber[]{
            Subscriber.TRANSPORT_BUY_ARCHIVE,
    };
    final Subscriber[] laboratorySellArchiveSubscribe = new Subscriber[]{
            Subscriber.TRANSPORT_SELL_ARCHIVE
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArchiveType type = ArchiveType.valueOf(req.getParameter("type"));
        req.setAttribute(TITLE, Titles.ARCHIVE + "." + type.toString());

        req.setAttribute(TYPES, DealType.values());
        req.setAttribute("copy", Branches.UI.WEIGHT_ADD);
        req.setAttribute("haveMenu", false);

        switch (type){
            case summary:
                req.setAttribute(CONTENT, "/pages/weight/weightList.jsp");
                req.setAttribute(SUBSCRIBE, summaryArchiveSubscriber);
                break;
            case transportation:
                req.setAttribute(CONTENT, "/pages/transport/transportList.jsp");
                req.setAttribute(SUBSCRIBE, transportArchiveSubscriber);
                break;
            case weight:{
                req.setAttribute(CONTENT, "/pages/weight/weightList.jsp");
                req.setAttribute(SUBSCRIBE, weightArchiveSubscribe);
                req.setAttribute(EDIT, Branches.UI.ARCHIVE_WEIGHT_SHOW);
                break;
            }
            case laboratory_buy:
                req.setAttribute(CONTENT, "/pages/laboratory/laboratoryList.jsp");
                req.setAttribute(SUBSCRIBE, laboratoryBuyArchiveSubscriber);
                break;
            case laboratory_sell:
                req.setAttribute(CONTENT, "/pages/laboratory/laboratoryList.jsp");
                req.setAttribute(SUBSCRIBE, laboratorySellArchiveSubscribe);
                break;
            default:
                req.setAttribute(CONTENT, "/pages/archive/archiveTypeErr.jsp");
                req.setAttribute("type", type);
                break;
        }
        req.setAttribute("findOrganisations", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("findDrivers", Branches.API.References.FIND_DRIVER);
        req.setAttribute(FILTER, "/pages/filters/archiveFilter.jsp");
        req.setAttribute("products", dao.getProductList());
        req.setAttribute("find", Branches.API.ARCHIVE_FIND);

        show(req, resp);
    }

}
