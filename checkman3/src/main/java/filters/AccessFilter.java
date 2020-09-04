package filters;

import constants.Apis;
import constants.Urls;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constants.Keys.*;

//@WebFilter(value = {Urls.HOME, ASTERISK + Urls.SUFFIX, Apis.API + SLASH + ASTERISK})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        final HttpSession session = req.getSession();
        final Object attribute = session.getAttribute(USER);
        chain.doFilter(request, response);
        if (attribute != null){
            chain.doFilter(request, response);
        } else {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(req.getContextPath() + Urls.LOGIN);
        }

    }

    @Override
    public void destroy() {

    }
}
