package ua.quasilin.chekmanszpt.services;

import android.content.Context;
import android.util.Log;

import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.LoginAnswer;
import ua.quasilin.chekmanszpt.entity.Worker;
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
        Preferences preferences = new Preferences(context);
        String login = preferences.get(LOGIN_KEY, null);
        String password = preferences.get(PASSWORD_KEY, null);

        return login != null && password != null && signIn(login, password).getStatus() == LoginStatus.success;
    }

    public static LoginAnswer signIn(Context context, String login, String password, boolean save){
        LoginAnswer answer = signIn(login, password);
        if (answer.getStatus() == LoginStatus.success && save){
            Preferences preferences = new Preferences(context);
            preferences.put(LOGIN_KEY, login);
            preferences.put(PASSWORD_KEY, password);

        }
        return answer;
    }


    private static LoginAnswer signIn(String login, String password){
        String answer = connector.request(URL.buildHttpAddress(URL.LOGIN), new SignInPacket(login, password));
        JSONObject json = JsonParser.parse(answer);
        LoginAnswer loginAnswer = new LoginAnswer(LoginStatus.error);
        if (json != null){
            String s = String.valueOf(json.get("status"));

            if (s != null && !s.isEmpty()){
                if (s.equals("success")){
                    loginAnswer = new LoginAnswer(LoginStatus.success);
                    ChatContainer.worker = new Worker(json.get("user"));
                } else {
                    Object msg = json.get("msd");
                    if (msg == null) {
                        msg = json.get("msg");
                    }
                    loginAnswer = new LoginAnswer(LoginStatus.error, msg != null ? msg.toString() : null);
                }
            }

        }

        return loginAnswer;
    }
}
