package filters;

import constants.Branches;
import entity.UserInfo;
import utils.LoginBox;
import utils.access.UserBox;
import utils.hibernate.Hibernator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//230*3=690
/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebFilter(value = {Branches.UI.APPLICATION, "*.j", Branches.API.API + "/*"})
public class SignInFilter implements Filter{

    private static final String WORKER = "worker";
    private final Hibernator hibernator = Hibernator.getInstance();
    UserBox userBox;
//    final Logger log = Logger.getLogger(SignInFilter.class);
//    final SessionTimer sessionTimer = SessionTimer.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {
        userBox = UserBox.getInstance();
    }

    public static final String TOKEN = "token";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final Object t = request.getSession().getAttribute(TOKEN);
        String token = t != null ? t.toString() : null;
        if (token == null){
            token = request.getHeader(TOKEN);
        }

        boolean valid = true;

        if (token == null){
            valid = false;
        } else if (!userBox.containsKey(token)){
            request.getSession().removeAttribute(TOKEN);
            valid = false;
        }

        if (valid) {
//            Worker worker = (Worker) request.getSession().getAttribute(WORKER);
//            sessionTimer.update(worker);
//            String updateToken = userBox.updateToken(token);
//            if (inAttribute){
//                response.setHeader(TOKEN, updateToken);
//            } else {
//                request.getSession().setAttribute(TOKEN, updateToken);
//            }

            filterChain.doFilter(request, response);
        } else if (LoginBox.getInstance().trySignIn(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String uri = request.getRequestURI();
            String path = request.getContextPath();
            uri = uri.substring(path.length());
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
        int i = 0;
        for (UserInfo info : userBox.getUsers().values()){
            info.setId(i++);
            hibernator.save(info);
        }
    }
}
