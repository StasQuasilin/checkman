package filters;

import api.sockets.ActiveSubscriptions;
import api.sockets.handlers.SessionTimer;
import bot.TelegramBotFactory;
import constants.Branches;
import constants.Constants;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import utils.Archivator;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.dbDAOService;
import utils.notifications.Notificator;
import utils.transport.TransportReplaceUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j" ,Branches.UI.FORGOT})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final Logger log = Logger.getLogger(ContextFilter.class);
    TransportReplaceUtil tru;

    @Override
    public void init(FilterConfig filterConfig) {
        gcTimer = new Timer(10 * 60 * 1000, e -> System.gc());
        gcTimer.start();
        HibernateSessionFactory.init();
        TelegramBotFactory.init();
        initBot();
        Archivator.init();
        tru = new TransportReplaceUtil();
    }

    Timer gcTimer;


    public void initBot(){
        log.info("Read bot settings...");
        settings = dbDAOService.getDAO().getBotSettings();
        if (settings != null) {
            log.info("\t...Bot settings read successfully");
            try {
                TelegramBotFactory.setSettings(settings);
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
        TelegramBotFactory.shutdown();
        HibernateSessionFactory.shutdown();
        tru.shutdown();
        SessionTimer.getInstance().stop();
    }
}
