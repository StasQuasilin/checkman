package filters;

import bot.BotFactory;
import constants.Branches;
import utils.hibernate.HibernateSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by quasilin on 13.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j"})
public class ContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        HibernateSessionFactory.init();
        try {
            BotFactory.init();
        } catch (IOException e) {
            e.printStackTrace();
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
        BotFactory.shutdown();
    }
}
