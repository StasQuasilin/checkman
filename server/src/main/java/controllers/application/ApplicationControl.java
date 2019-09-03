package controllers.application;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IServlet;
import controllers.archive.ArchiveType;
import entity.AnalysesType;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.APPLICATION)
public class ApplicationControl extends IServlet{

    final dbDAO dao = dbDAOService.getDAO();
    final Subscriber[] applicationSubscribes = new Subscriber[]{Subscriber.MESSAGES};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("SUBSCRIBER", Branches.API.SUBSCRIBER);
        req.setAttribute("welcome", Branches.UI.WELCOME);
        req.setAttribute("buyList", Branches.UI.DEAL_BUY);
        req.setAttribute("buyArchive", Branches.UI.BUY_ARCHIVE);
        req.setAttribute("sellList", Branches.UI.DEAL_SELL);
        req.setAttribute("sellArchive", Branches.UI.SELL_ARCHIVE);
        req.setAttribute("railList", Branches.UI.RAIL_LIST);
        req.setAttribute("railArchive", Branches.UI.RAIL_ARCHIVE);
        req.setAttribute("summaryList", Branches.UI.SUMMARY_LIST);
        req.setAttribute("summaryArchive", Branches.UI.SUMMARY_ARCHIVE);
        req.setAttribute("logisticList", Branches.UI.LOGISTIC_LIST);
        req.setAttribute("logisticArchive", Branches.UI.LOGISTIC_ARCHIVE);
        req.setAttribute("transportList", Branches.UI.TRANSPORT_LIST);
        req.setAttribute("transportArchive", Branches.UI.TRANSPORT_ARCHIVE + "?type=" + ArchiveType.transportation.toString());
        req.setAttribute("sealList", Branches.UI.SEAL_LIST);
        req.setAttribute("weightList", Branches.UI.WEIGHT_LIST);
        req.setAttribute("weightArchive", Branches.UI.WEIGHT_ARCHIVE);
        req.setAttribute("probeList", Branches.UI.PROBE_LIST);
        req.setAttribute("subdivisionList", Branches.UI.SUBDIVISION_LIST);
        req.setAttribute("subdivisions", dao.getSubdivisions());
        req.setAttribute("storages", Branches.UI.LABORATORY_STORAGES + "?type=" + AnalysesType.oil);
        req.setAttribute("laboratoryBuyList", Branches.UI.LABORATORY_BUY);
        req.setAttribute("laboratoryBuyArchive", Branches.UI.LABORATORY_BUY_ARCHIVE);
        req.setAttribute("laboratorySellList", Branches.UI.LABORATORY_SELL);
        req.setAttribute("laboratorySellArchive", Branches.UI.LABORATORY_SELL_ARCHIVE);
        req.setAttribute("laboratoryTurns", Branches.UI.LABORATORY_TURNS);
        req.setAttribute("referencesList", Branches.UI.REFERENCES);
        req.setAttribute("admin", Branches.UI.ADMIN);
        req.setAttribute("personal", Branches.UI.PERSONAL);
        req.setAttribute("feedback", Branches.UI.FEEDBACK);
        req.setAttribute("subscribe", applicationSubscribes);
        req.setAttribute("sendMessage", Branches.API.CHAT_SEND);
        req.setAttribute("getMessages", Branches.API.GET_CHAT);
        req.setAttribute("leaveChat", Branches.API.LEAVE_CHAT);
        req.setAttribute("renameChat", Branches.API.RENAME_CHAT);
        req.setAttribute("removeChat", Branches.API.REMOVE_NOTE);

        req.setAttribute("shortCutUpdate", Branches.ShortCuts.UPDATE);
        req.setAttribute("logoutAPI", Branches.Sign.LOGOUT);

        req.getRequestDispatcher("/pages/Application.jsp").forward(req, resp);
    }
}