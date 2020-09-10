package controllers;

import constants.Apis;
import constants.Keys;
import constants.Urls;
import entity.deals.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(Urls.HOME)
public class ApplicationHome extends Servlet{
    private static final String APPLICATION = "/pages/application.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(DEALS, Urls.DEALS);
        req.setAttribute(DEAL_TYPES, DealType.values());
        req.setAttribute(SUBSCRIBER, Apis.SUBSCRIBES);
        req.getRequestDispatcher(APPLICATION).forward(req, resp);
    }
}
