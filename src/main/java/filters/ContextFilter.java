package filters;

import bot.BotFactory;
import constants.Branches;
import entity.bot.BotSettings;
import org.apache.log4j.Logger;
import utils.hibernate.HibernateSessionFactory;
import utils.hibernate.Hibernator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j"})
public class ContextFilter implements Filter {

    public static BotSettings settings;
    final Logger log = Logger.getLogger(ContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        HibernateSessionFactory.init();
        initBot();
    }

    public void initBot(){
        log.info("Read bot settings...");
        settings = Hibernator.getInstance().get(BotSettings.class, null);
        if (settings != null) {
            log.info("\t...Bot settings read successfully");
            try {
                BotFactory.setSettings(settings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info("\t...Settings not found");
            settings = new BotSettings();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        req.setAttribute("context", req.getContextPath());
        req.setAttribute("lang", "ru");
        filterChain.doFilter(req, servletResponse);
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
