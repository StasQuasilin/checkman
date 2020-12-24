package filters;

import constants.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebFilter(value = {Constants.ASTERISK})
public class NowFilter implements Filter {

    private long now;

    @Override
    public void init(FilterConfig filterConfig) {
        now = Timestamp.valueOf(LocalDateTime.now()).getTime();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(Constants.NOW, now);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
