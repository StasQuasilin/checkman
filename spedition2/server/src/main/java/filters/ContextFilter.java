package filters;

import constants.Keys;
import constants.Urls;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(value = {Urls.HOME, Keys.ASTERISK + Urls.SUFFIX})
public class ContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute(Keys.CONTEXT, req.getContextPath());
        chain.doFilter(req, response);
    }

    @Override
    public void destroy() {}
}
