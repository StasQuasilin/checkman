package ua.quasilin.chekmanszpt.services;

import android.content.Context;
import android.util.Log;

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

        return login != null && password != null && signIn(login, password) == LoginStatus.success;
    }

    public static LoginStatus signIn(Context context, String login, String password, boolean save){
        LoginStatus answer = signIn(login, password);
        if (answer == LoginStatus.success && save){
            Preferences preferences = Preferences.getPreferences(context);
            preferences.put(LOGIN_KEY, login);
            preferences.put(PASSWORD_KEY, password);
        }
        return answer;
    }

    private static LoginStatus signIn(String login, String password){
        String answer = connector.request(URL.buildAddress(URL.LOGIN), new SignInPacket(login, password));
        Log.i("ANSWER", answer);
        JSONObject json = JsonParser.parse(answer);
        LoginStatus status = LoginStatus.error;
        if (json != null){
            String s = String.valueOf(json.get("status"));
            if (s != null && !s.isEmpty()){
                if (s.equals("success")){
                    status = LoginStatus.success;
                } else if(s.equals("405")){
                    status = LoginStatus.error405;
                }
            }

        }
        return status;
    }
}
