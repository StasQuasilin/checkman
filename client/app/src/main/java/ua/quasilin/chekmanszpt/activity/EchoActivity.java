package ua.quasilin.chekmanszpt.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.services.socket.Socket;

public class EchoActivity extends AppCompatActivity {

    private static final String DATA = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echo);
        EditText editText = findViewById(R.id.echoText);
        Button button = findViewById(R.id.echoButton);
        TextView view = findViewById(R.id.echoView);
        button.setOnClickListener(v -> {
            String text = editText.getText().toString();
            editText.clearFocus();
            if (!text.isEmpty()){
                Handler handler = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        String string = msg.getData().getString(DATA);
                        view.setText(string);
                        editText.getText().clear();

                    }
                };
                new Thread(() -> {
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString(DATA, Socket.send(text));
                    message.setData(bundle);
                    handler.sendMessage(message);
                }).start();
            }
        });


    }
}
