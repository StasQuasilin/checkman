package controllers;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.json.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Servlet extends HttpServlet {
    private JSONParser parser = new JSONParser();
    public JsonObject parseBody(HttpServletRequest req){
        try {
            final BufferedReader reader = req.getReader();
            final Object parse = parser.parse(reader);
            return new JsonObject(parse);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
