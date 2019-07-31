package ua.quasilin.chekmanszpt.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class MessagesHandler {

    @SuppressLint("StaticFieldLeak")
    private static MessagesHandler handler = null;

    public static MessagesHandler getHandler() {
        return handler;
    }

    private Context context;
    private MessagesHandler(Context context) {
        this.context = context;
    }

    public static void init(Context context){
        handler  = new MessagesHandler(context);
    }

    private static final String MESSAGE = "message";
    public void handle(String text){
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context, msg.getData().getString(MESSAGE), Toast.LENGTH_SHORT).show();
            }
        };
        new Thread(() -> {
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(MESSAGE, text);
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }
}
