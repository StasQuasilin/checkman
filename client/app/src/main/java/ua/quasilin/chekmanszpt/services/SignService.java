package ua.quasilin.chekmanszpt.services;

import android.content.Context;

import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.packets.SignInPacket;
import ua.quasilin.chekmanszpt.utils.JsonParser;
import ua.quasilin.chekmanszpt.utils.LoginStatus;
import ua.quasilin.chekmanszpt.utils.Preferences;

/**
 * Created by Kvasik on 30.07.2019.
 */

public final class SignService {

    private static final HttpConnector connector = HttpConnector.getConnector();
    private static final String LOGIN_KEY = "login";
    private static final String PASSWORD_KEY = "password";

    public static boolean trySignIn(Context context) {
        Preferences preferences = Preferences.getPreferences(context);

        String login = preferences.get(LOGIN_KEY, null);
        String password = preferences.get(PASSWORD_KEY, null);

        return login != null && password != null && SignIn(login, password) == LoginStatus.success;
    }

    public boolean SignIn(Context context, String login, String password, boolean save){
        boolean answer = SignIn(login, password) == LoginStatus.success;
        if (answer && save){
            Preferences preferences = Preferences.getPreferences(context);
            preferences.put(LOGIN_KEY, login);
            preferences.put(PASSWORD_KEY, password);
        }
        return answer;
    }

    private static LoginStatus SignIn(String login, String password){
        String answer = connector.request(URL.LOGIN, new SignInPacket(login, password));
        JSONObject json = JsonParser.parse(answer);
        LoginStatus status = LoginStatus.error;
        if (json != null){
            String s = String.valueOf(json.get("status"));
            if (s != null && !s.isEmpty()){
                if (s.equals("success")){
                    status = LoginStatus.success;
                }
            }

        }
        return status;
    }
}
