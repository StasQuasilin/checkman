package ua.quasilin.chekmanszpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.services.SignService;
import ua.quasilin.chekmanszpt.utils.ServiceStarter;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class StartActivity extends AppCompatActivity {

    private static final String ANSWER = "answer";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Log.i(String.valueOf(StartActivity.class), "Application Start");

        //todo find active server

        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (msg.getData().getBoolean(ANSWER)){

                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        };

        new Thread(() -> {
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putBoolean(ANSWER, SignService.trySignIn(getApplicationContext()));
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }
}