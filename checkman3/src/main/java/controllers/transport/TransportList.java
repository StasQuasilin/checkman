package controllers.transport;

import constants.Keys;
import constants.Urls;
import controllers.Page;
import controllers.api.subscribes.Subscriber;
import controllers.api.subscribes.Subscribes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Urls.TRANSPORTATIONS)
public class TransportList extends Page {
    private static final String _TITLE = "title.transport";
    private static final String _CONTENT = "/pages/transport/transportList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Keys.TITLE, _TITLE);
        req.setAttribute(Keys.CONTENT, _CONTENT);
        req.setAttribute(Keys.EDIT, Urls.RETAIL_EDIT);
        req.setAttribute(Keys.SUBSCRIBE, Subscribes.transportations);
        show(req, resp);
    }
}
