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

    final String CONTENT = "content";
    final String SUBSCRIBE = "subscribe";
    final String EDIT = "edit";
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArchiveType type = ArchiveType.valueOf(req.getParameter("type"));
        req.setAttribute("title", Titles.ARCHIVE + "." + type.toString());
        req.setAttribute(EDIT, Branches.UI.ARCHIVE_SHOW + type.toString() + ".j");
        req.setAttribute("types", DealType.values());
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
                break;
            }
            default:
                req.setAttribute("content", "/pages/archive/archiveTypeErr.jsp");
                req.setAttribute("type", type);
                break;
        }

        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("products", dao.getProductList());

        show(req, resp);
    }

}
