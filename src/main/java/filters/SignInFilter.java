package filters;

import constants.Branches;
import utils.LoginBox;
import utils.access.UserBox;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j", Branches.API.API + "*"})
public class SignInFilter implements Filter{

    final UserBox userBox = UserBox.getUserBox();
//    final String apiAnswer = JsonParser.toJson(new ErrorAnswer("msg", "Restricted area. Authorized personnel only")).toJSONString();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public static final String TOKEN = "token";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final Object token = request.getSession().getAttribute(TOKEN);

        if (token != null && userBox.containsKey(token.toString())) {
            request.getSession().setAttribute(TOKEN, userBox.updateToken(token.toString()));
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (LoginBox.getInstance().trySignIn(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String uri = request.getRequestURI();
            String path = request.getContextPath();
            uri = uri.substring(path.length(), uri.length());
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            if (uri.substring(0, Branches.API.API.length()).equals(Branches.API.API)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.sendRedirect(request.getContextPath() + Branches.UI.SING_IN);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
