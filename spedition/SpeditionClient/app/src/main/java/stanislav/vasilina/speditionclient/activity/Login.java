package stanislav.vasilina.speditionclient.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class Login extends AppCompatActivity {

    private LoginUtil loginUtil;
    private EditText phoneEdit;
    private EditText passwordEdit;
    private ProgressBar progressBar;
    private TextView statusView;
    private NetworkUtil networkUtil;
    private boolean waitAnswer = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        loginUtil = new LoginUtil(context);
        networkUtil = new NetworkUtil();

        final boolean login = loginUtil.checkLogin();
        if (login) {
            Intent intent = new Intent(context, Reports.class);
            context.startActivity(intent);
        } else {
            setContentView(R.layout.activity_login);
            String phoneNumber = EMPTY;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tMgr != null) {
                    phoneNumber = tMgr.getLine1Number();
                }
            }
            phoneEdit = findViewById(R.id.editTextPhone);
            phoneEdit.setText(phoneNumber);

            passwordEdit = findViewById(R.id.editPassword);
            final Button loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!waitAnswer) {
                        login();
                    }
                }
            });
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            statusView = findViewById(R.id.statusText);
        }
    }


    private void login() {

        waitAnswer = true;
        progressBar.setVisibility(View.VISIBLE);
        final String login = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        final JSONObject json = new JSONObject();
        json.put(PHONE, login);
        json.put(PASSWORD, Base64.encodeToString(password.getBytes(), Base64.NO_WRAP));

        final LoginHandler handler = new LoginHandler(statusView);

        new Thread(){
            @Override
            public void run() {
                try {
                    final String post = networkUtil.post(ApiLinks.LOGIN, json.toJSONString(), null);
                    waitAnswer = false;
                    progressBar.setVisibility(View.INVISIBLE);
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject json = (JSONObject) parser.parse(post);
                        String status = String.valueOf(json.get(STATUS));
                        if (status.equals(SUCCESS)){
                            //todo save token and redirect
                            String token = String.valueOf(json.get(TOKEN));
                            loginUtil.saveToken(token);
                            handler.removeCallbacksAndMessages(null);
                        } else {
                            String reason = String.valueOf(json.get(REASON));
                            Bundle bundle = new Bundle();
                            bundle.putString(REASON, reason);
                            Message message = new Message();
                            message.setData(bundle);
                            handler.handleMessage(message);
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
}
class LoginHandler extends Handler{

    private final TextView statusView;

    public LoginHandler(TextView statusView) {
        this.statusView = statusView;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        final Bundle data = msg.getData();
        final String string = data.getString(REASON);
        statusView.setText(string);
    }


}
