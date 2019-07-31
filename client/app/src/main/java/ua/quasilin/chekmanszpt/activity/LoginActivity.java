package ua.quasilin.chekmanszpt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.services.SignService;
import ua.quasilin.chekmanszpt.utils.LoginStatus;

public class LoginActivity extends Activity {

    private static final String STATUS = "status";
    private String uid;
    final String[] data = {"Головченко А.М.", "Барабань В.К.", "Болоболко В.В."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AutoCompleteTextView loginInput = findViewById(R.id.loginInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotButton = findViewById(R.id.forgotButton);
        TextView answer = findViewById(R.id.answer);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        loginInput.setAdapter(adapter);

        loginButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString();
            Handler handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    LoginStatus loginStatus = LoginStatus.valueOf(msg.getData().getString(STATUS));
                    switch (loginStatus){
                        case error:
                            answer.setText(R.string.sing_in_error);
                            break;
                        case error405:
                            answer.setText(R.string.sing_in_error_405);
                            break;
                        case success:
                            Log.i("LOGIN", "SUCCESS");
                            break;
                        case incorrectLogin:
                            answer.setText(R.string.sing_in_incorect_login);
                            break;
                        case incorrectPassword:
                            answer.setText(R.string.sing_in_incorect_password);
                            break;
                    }
                }
            };
            new Thread(() -> {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString(STATUS, SignService.signIn(getApplicationContext(), uid, password, true).toString());
                message.setData(bundle);
                handler.sendMessage(message);
            }).start();


        });


        forgotButton.setOnClickListener(v -> {
            Log.i("LOGIN", "FORGOT PASSWORD");
        });
    }
}
