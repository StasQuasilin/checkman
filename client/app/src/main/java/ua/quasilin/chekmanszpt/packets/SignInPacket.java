package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONObject;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class SignInPacket extends Packet {

    private static final String LOGIN = "login";
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
        json.put(LOGIN, login);
        json.put(PASSWORD, password);
        String result = json.toJSONString();
        pool.put(json);
        return result;
    }
}
