package filters;

import constants.Links;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.ApiLinks.API;
import static constants.Keys.TOKEN;
import static constants.Links.LOGIN;

//@WebFilter(value = {"/*", "*" + Links.SUFFIX})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean useHeader = true;
        String token = request.getHeader(TOKEN);
        if (token == null){
            useHeader = false;
            final Object attribute = request.getSession().getAttribute(TOKEN);
            if (attribute != null){
                token = attribute.toString();
            }
        }
        if (token != null){
            filterChain.doFilter(request, response);
        } else {
            if (useHeader){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.sendRedirect(LOGIN);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
