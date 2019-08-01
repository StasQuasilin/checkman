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
    private String uid;
    final String[] data = {"Головченко А.М.", "Барабань В.К.", "Болоболко В.В."};

    private boolean isUserInput = false;
    private Thread findUserThread;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        EditText loginInput = findViewById(R.id.loginInput);
        Spinner usersSpinner = findViewById(R.id.usersSpinner);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotButton = findViewById(R.id.forgotButton);
        TextView answer = findViewById(R.id.answer);

        loginInput.setOnFocusChangeListener((v, hasFocus) -> {
            isUserInput = hasFocus;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            usersSpinner.setAdapter(adapter);
            usersSpinner.setVisibility(View.VISIBLE);
            usersSpinner.performClick();
        });

        loginInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (findUserThread != null && findUserThread.isAlive()){
                    findUserThread.interrupt();
                }

                findUserThread = new Thread(() -> {
                    if (timer != null){
                        timer.cancel();
                    }
                    timer = new Timer();
                    timer.schedule(new FindUserTask(s.toString()), 0, 5000);
                });
                findUserThread.start();

            }
        });



        usersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICK on " + position);
                usersSpinner.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                usersSpinner.setVisibility(View.INVISIBLE);
            }
        });

//        loginInput.setAdapter(adapter);

        loginButton.setOnClickListener((View v) -> {

            String login = loginInput.getText().toString();
            String password = passwordInput.getText().toString();

            Handler handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    LoginStatus loginStatus = LoginStatus.valueOf(msg.getData().getString(STATUS));
                    switch (loginStatus){
                        case error:
                            String message = msg.getData().getString(MESSAGE);
                            if (message != null && !message.isEmpty()){
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
                if (loginAnswer.getMessage() != null){
                    bundle.putString(MESSAGE, loginAnswer.getMessage());
                }

                message.setData(bundle);
                handler.sendMessage(message);
            }).start();


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
