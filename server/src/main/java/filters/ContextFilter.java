package filters;

import api.sockets.ActiveSubscriptions;
import api.sockets.EchoSocket;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.boxes.DealBox;
import utils.boxes.TransportBox;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.dbDAOService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j", Branches.UI.FORGOT})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final Logger log = Logger.getLogger(ContextFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        gcTimer = new Timer(20 * 1000, e -> System.gc());
        gcTimer.start();
        HibernateSessionFactory.init();
        initBot();
        Archivator.init();
    }

    Timer gcTimer;

    public void initBot(){
        log.info("Read bot settings...");
        settings = dbDAOService.getDAO().getBotSettings();
        if (settings != null) {
            log.info("\t...Bot settings read successfully");
            try {
                BotFactory.setSettings(settings);
                settings.setRun(true);
                dbDAOService.getDAO().save(settings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("\t...Settings not found");
        }
    }

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
        gcTimer.stop();
        Archivator.stop();
        ActiveSubscriptions.getInstance().close();
        BotFactory.shutdown();
        HibernateSessionFactory.shutdown();
    }
}
