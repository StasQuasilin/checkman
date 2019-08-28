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
@WebFilter(value = {Branches.UI.APPLICATION, "*.j", Branches.API.API + "/*"})
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
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final Object t = request.getSession().getAttribute(TOKEN);
        String token = t != null ? t.toString() : null;
        boolean inAttribute = false;
        if (token == null){
            inAttribute= true;
            token = request.getHeader(TOKEN);
        }

        if (token != null && userBox.containsKey(token)) {
            String updateToken = userBox.updateToken(token);
            if (inAttribute){
                response.setHeader(TOKEN, updateToken);
            } else {
                request.getSession().setAttribute(TOKEN, updateToken);
            }

            filterChain.doFilter(request, response);
        } else if (LoginBox.getInstance().trySignIn(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String uri = request.getRequestURI();
            String path = request.getContextPath();
            uri = uri.substring(path.length(), uri.length());
            int length = Branches.API.API.length();
            if (uri.length() >= length && uri.substring(0, length).equals(Branches.API.API)){
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
