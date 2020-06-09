package stanislav.vasilina.speditionclient.dialogs;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.constants.ApiLinks;
import stanislav.vasilina.speditionclient.utils.LoginUtil;
import stanislav.vasilina.speditionclient.utils.NetworkUtil;

import static stanislav.vasilina.speditionclient.constants.Keys.EMPTY;
import static stanislav.vasilina.speditionclient.constants.Keys.PASSWORD;
import static stanislav.vasilina.speditionclient.constants.Keys.PHONE;
import static stanislav.vasilina.speditionclient.constants.Keys.REASON;
import static stanislav.vasilina.speditionclient.constants.Keys.STATUS;
import static stanislav.vasilina.speditionclient.constants.Keys.SUCCESS;
import static stanislav.vasilina.speditionclient.constants.Keys.TOKEN;

public class LoginDialog extends DialogFragment {

    private EditText phoneEdit;
    private EditText passwordEdit;
    private ProgressBar progressBar;
    private TextView statusView;
    private final LoginUtil loginUtil;
    private NetworkUtil networkUtil;
    private final LayoutInflater inflater;
    private boolean isAuthorize;
    private boolean waitAnswer = false;

    public LoginDialog(Context context, LayoutInflater inflater) {
        this.inflater = inflater;
        loginUtil = new LoginUtil(context);
        networkUtil = new NetworkUtil();
        isAuthorize = loginUtil.getToken() != null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i("Login", "Create dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (isAuthorize){
            final View view = inflater.inflate(R.layout.already_login, null);

            builder.setView(view);
        } else {
            final View view = inflater.inflate(R.layout.activity_login, null);

            String phoneNumber = EMPTY;
            final Context context = getContext();
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tMgr != null) {
                    phoneNumber = tMgr.getLine1Number();
                }
            }
            phoneEdit = view.findViewById(R.id.editTextPhone);
            phoneEdit.setText(phoneNumber);

            passwordEdit = view.findViewById(R.id.editPassword);
            final Button loginButton = view.findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Login", "Press login button");
                    if (!waitAnswer) {
                        login();
                    }
                }
            });
            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            statusView = view.findViewById(R.id.statusText);

            builder.setView(view);
        }

        return builder.create();
    }

    private void login() {
        progressBar.setVisibility(View.INVISIBLE);
        waitAnswer = true;
        progressBar.setVisibility(View.VISIBLE);
        final String login = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        final JSONObject json = new JSONObject();
        json.put(PHONE, login);
        json.put(PASSWORD, Base64.encodeToString(password.getBytes(), Base64.NO_WRAP));

        final StatusHandler statusHandler = new StatusHandler(statusView);
        final LoginHandler loginHandler = new LoginHandler(loginUtil);

        new Thread(){
            @Override
            public void run() {
                try {
                    final String post = networkUtil.post(ApiLinks.LOGIN, json.toJSONString(), null);
                    waitAnswer = false;
                    Log.i("Login", post);
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject json = (JSONObject) parser.parse(post);
                        String status = String.valueOf(json.get(STATUS));
                        if (status.equals(SUCCESS)){
                            //todo close dialog
                            String token = String.valueOf(json.get(TOKEN));
                            sendMessage(loginHandler, TOKEN, token);
                            statusHandler.removeCallbacksAndMessages(null);
                        } else {
                            String reason = String.valueOf(json.get(REASON));
                            sendMessage(statusHandler, REASON, reason);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void sendMessage(Handler handler, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
