package controllers.deals;

import constants.Keys;
import constants.Urls;
import controllers.Page;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(Urls.DEALS)
public class DealsList extends Page {

    private static final String _CONTENT = "/pages/deals/dealsList.jsp";
    private static final String _TITLE = "title.deals";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String type = req.getParameter(TYPE);
        req.setAttribute(TITLE, _TITLE + DOT + type);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(EDIT, Urls.DEAL_EDIT + "?type=" + type);
        req.setAttribute(Keys.SHOW, Urls.DEAL_SHOW);
        req.setAttribute(SUBSCRIBER, DEALS + "_" + type.toLowerCase());
        show(req, resp);
    }
}
