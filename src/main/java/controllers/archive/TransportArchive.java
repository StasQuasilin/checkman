package controllers.archive;

import api.sockets.Subscriber;
import constants.Branches;
import constants.Constants;
import controllers.IUIServlet;
import entity.DealType;
import entity.products.Product;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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

    final Subscriber[] summarySubscriber = new Subscriber[]{Subscriber.LOAD_PLAN_ARCHIVE};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArchiveType type = ArchiveType.valueOf(req.getParameter("type"));
        req.setAttribute("show", Branches.UI.ARCHIVE_SHOW + type.toString() + ".j");
        req.setAttribute("title", Constants.Titles.TRANSPORT_ARCHIVE + "." + type.toString());
        switch (type){
            case summary:
                req.setAttribute("content", "/pages/weight/weightList.jsp");
                req.setAttribute("subscribe", summarySubscriber);
                req.setAttribute("types", DealType.values());
                break;
            default:
                req.setAttribute("content", "/pages/archive/transportArchive.jsp");
                break;
        }

        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("products", dao.getProductList());

        show(req, resp);
    }

}
