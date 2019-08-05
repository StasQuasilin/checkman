package ua.quasilin.chekmanszpt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.LoginAnswer;
import ua.quasilin.chekmanszpt.packets.FindUserPacket;
import ua.quasilin.chekmanszpt.services.HttpConnector;
import ua.quasilin.chekmanszpt.services.SignService;
import ua.quasilin.chekmanszpt.utils.LoginStatus;

public class LoginActivity extends Activity {

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private boolean isUserInput = false;
    private Thread findUserThread;
    private Timer timer;
    private boolean alreadyPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        EditText loginInput = findViewById(R.id.loginInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        ProgressBar signProgress = findViewById(R.id.signProgress);
        TextView forgotButton = findViewById(R.id.forgotButton);
        TextView answer = findViewById(R.id.answer);

        signProgress.setVisibility(View.INVISIBLE);
        loginInput.setOnFocusChangeListener((v, hasFocus) -> {
            isUserInput = hasFocus;
        });

        loginButton.setOnClickListener((View v) -> {
            if (!alreadyPress) {
                alreadyPress = true;
                signProgress.setVisibility(View.VISIBLE);
                loginButton.setActivated(false);
                String login = loginInput.getText().toString();
                String password = passwordInput.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        alreadyPress = false;
                        signProgress.setVisibility(View.INVISIBLE);
                        loginButton.setActivated(true);
                        LoginStatus loginStatus = LoginStatus.valueOf(msg.getData().getString(STATUS));
                        switch (loginStatus) {
                            case error:
                                String message = msg.getData().getString(MESSAGE);
                                if (message != null && !message.isEmpty()) {
                                    answer.setText(message);
                                } else {
                                    answer.setText(R.string.sing_in_error);
                                }
                                break;
                            case success:
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                break;
                        }
                    }
                };
                new Thread(() -> {
                    Message message = new Message();
                    Bundle bundle = new Bundle();

                    String encodedPassword = password;

                    try {
                        encodedPassword = Base64.encodeToString(password.getBytes("UTF-8"), Base64.NO_WRAP);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    LoginAnswer loginAnswer = SignService.signIn(getApplicationContext(), login, encodedPassword, true);
                    bundle.putString(STATUS, loginAnswer.getStatus().toString());
                    if (loginAnswer.getMessage() != null) {
                        bundle.putString(MESSAGE, loginAnswer.getMessage());
                    }

                    message.setData(bundle);
                    handler.sendMessage(message);
                }).start();
            }

        });


        forgotButton.setOnClickListener(v -> {
//            startActivity(new Intent(getApplicationContext(), PasswordRestoreActivity.class));
            loginInput.setText("FORGOT");
        });
    }

    class FindUserTask extends TimerTask{

        String key;

        FindUserTask(String key) {
            try {
                this.key = URLEncoder.encode(key, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                this.key = key;
            }
        }

        @Override
        public void run() {
            String request = HttpConnector.getConnector().request(URL.buildHttpAddress(URL.FIND_WORKER), new FindUserPacket(key));
            System.out.println(request);
            cancel();
        }
    }
}
