package controllers.api;


import constants.Keys;
import controllers.Servlet;
import utils.json.JsonAble;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class API extends Servlet {
    public void write(HttpServletResponse response, JsonAble jsonAble) throws IOException {
        response.setCharacterEncoding(Keys.ENCODING);
        final PrintWriter writer = response.getWriter();
        writer.write(jsonAble.toJson().toJSONString());
    }

}
