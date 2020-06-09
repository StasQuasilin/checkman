package stanislav.vasilina.speditionclient.dialogs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import stanislav.vasilina.speditionclient.utils.LoginUtil;

import static stanislav.vasilina.speditionclient.constants.Keys.TOKEN;

public class LoginHandler extends Handler {

    private final LoginUtil loginUtil;

    public LoginHandler(LoginUtil loginUtil) {
        this.loginUtil = loginUtil;
    }

    @Override
    public void dispatchMessage(@NonNull Message msg) {
        final Bundle data = msg.getData();
        final String token = data.getString(TOKEN);
        loginUtil.saveToken(token);
    }
}
