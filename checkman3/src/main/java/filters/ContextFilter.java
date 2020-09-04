package filters;

import constants.Keys;
import constants.Urls;
import entity.Language;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constants.Keys.*;

@WebFilter(value = {Urls.LOGIN, Urls.HOME, Keys.ASTERISK + Urls.SUFFIX})
public class ContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute(CONTEXT, req.getContextPath());
        final HttpSession session = req.getSession();
        final Object attribute = session.getAttribute(LOCALE);
        if (attribute == null){
            session.setAttribute(LOCALE, Language.values()[0].toString());
        }
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {

    }
}
