package controllers.api;

import constants.Keys;
import controllers.Servlet;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Api extends Servlet {

    public void write(HttpServletResponse response, JsonAble jsonAble) throws IOException {
        write(response, jsonAble.toJson());
    }

    private void write(HttpServletResponse response, JSONObject json) throws IOException {
        write(response, json.toJSONString());
    }

    public void write(HttpServletResponse response, String text) throws IOException {
        response.setCharacterEncoding(Keys.CHARSET);
        response.getWriter().write(text);
    }

}
