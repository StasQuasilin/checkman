package filters;

import api.sockets.ActiveSubscriptions;
import api.sockets.handlers.SessionTimer;
import bot.TelegramBotFactory;
import constants.Branches;
import constants.Constants;
import entity.bot.BotSettings;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.products.Product;
import entity.transport.Transportation;
import entity.transport.TransportationProduct;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.DocumentUIDGenerator;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;
import utils.transport.TransportReplaceUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;

import static constants.Constants.*;
import static utils.hibernate.State.notNull;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j" ,Branches.UI.FORGOT})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final static Logger log = Logger.getLogger(ContextFilter.class);
    TransportReplaceUtil tru;
    static final Hibernator hibernator = Hibernator.getInstance();
    static boolean interrupt = false;

    @Override
    public void init(FilterConfig filterConfig) {
        gcTimer = new Timer(10 * 60 * 1000, e -> System.gc());
//        gcTimer.start();
        TelegramBotFactory.init(filterConfig.getServletContext().getContextPath());
        Archivator.init();
        tru = new TransportReplaceUtil();
    }

    Timer gcTimer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        req.setAttribute(Constants.CONTEXT, req.getContextPath());
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Expires", "0");
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        interrupt = true;
        gcTimer.stop();
        Archivator.stop();
        ActiveSubscriptions.getInstance().close();
        TelegramBotFactory.shutdown();
        HibernateSessionFactory.shutdown();
        tru.shutdown();
        SessionTimer.getInstance().stop();
    }
    private static void initList(LinkedList<Transportation> transportations, HashMap<String, Object> args) {
        transportations.clear();
        transportations.addAll(ContextFilter.hibernator.limitQuery(Transportation.class, args, 20));
    }
}


