package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONObject;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class SignInPacket extends Packet {

    private static final String LOGIN = "uid";
    private static final String PASSWORD = "password";
    private final String login;
    private final String password;

    public SignInPacket(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toJson() {
        JSONObject json = pool.getObject();
        json.put(PASSWORD, password);
        json.put(LOGIN, login);
        System.out.println("<" + password);
        String result = json.toJSONString();
        System.out.println(result);
        pool.put(json);
        return result;
    }
}
