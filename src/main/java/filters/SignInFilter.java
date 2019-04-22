package filters;

import constants.Branches;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.LoginBox;
import utils.PostUtil;
import utils.UserBox;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j", "/api/*"})
public class SignInFilter implements Filter{

    final UserBox userBox = UserBox.getUserBox();
    String apiAnswer;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        JSONObject json = JsonParser.toJson(new ErrorAnswer("msg", "Restricted area. Authorized personnel only"));
        apiAnswer = json.toJSONString();
        json.clear();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Object token = request.getSession().getAttribute("token");

        if (token != null && userBox.containsKey(token.toString())) {
            request.getSession().setAttribute("token", userBox.updateToken(token.toString()));
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (LoginBox.getInstance().trySignIn(request, response)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String uri = request.getRequestURI();
            String path = request.getContextPath();
            uri = uri.substring(path.length(), uri.length());
            if (uri.substring(0, Branches.API.API.length()).equals(Branches.API.API)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                PostUtil.write(response, apiAnswer);
            } else {
                response.sendRedirect(request.getContextPath() + Branches.UI.SING_IN);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
