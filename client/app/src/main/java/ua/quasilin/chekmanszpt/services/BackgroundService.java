package ua.quasilin.chekmanszpt.services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.activity.LoginActivity;
import ua.quasilin.chekmanszpt.activity.NoConnectionActivity;
import ua.quasilin.chekmanszpt.activity.StartActivity;
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;
import ua.quasilin.chekmanszpt.activity.messages.MessageActivity;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.services.socket.Socket;
import ua.quasilin.chekmanszpt.utils.*;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class BackgroundService extends Service {

    final IBinder binder = new ServiceBinder();
    private Socket socket;
    private static final String STATUS = "status";
    private static final String ANSWER = "answer";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private static final int NOTIFICATION_ID = UUID.randomUUID().hashCode();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = getApplicationContext();
        createNotification(context);
        MessagesHandler messagesHandler = new MessagesHandler(context, BackgroundService.this);
        socket = new Socket(messagesHandler);
        findServer(context);
        return START_STICKY;
    }

    protected void findServer() {
        findServer(getApplicationContext());
    }

    Timer findTimer = new Timer();
    boolean timerRunning = false;
    boolean findServer = false;
    private void findServer(Context context){
        if (!findServer) {
            findServer = true;
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    findServer = false;
                    boolean serverFound = msg.getData().getBoolean(STATUS);
                    if (serverFound) {
                        Preferences preferences = new Preferences(context);
                        preferences.put("address", URL.currentAddress);
                        login();
                        if (timerRunning) {
                            timerRunning = false;
                            findTimer.cancel();
                        }
                    } else {
                        if (StartActivity.active) {
                            AppState.state = AppState.State.noServer;
                            startActivity(new Intent(context, NoConnectionActivity.class));
                        }
                        if (!timerRunning) {
                            timerRunning = true;
                            findTimer.schedule(new FindServerTask(), 0, 5000);
                        }
                    }
                }
            };
            new Thread(() -> {
                Preferences preferences = new Preferences(context);
                String address = preferences.get("address", null);
                boolean found = false;
                if (address != null) {
                    found = FindServer.checkAddress(address);
                }
                if (!found){
                    found = FindServer.find();
                }
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putBoolean(STATUS, found);
                message.setData(bundle);
                handler.sendMessage(message);
            }).start();
        }
    }

    private void login() {
        Context context = getApplicationContext();
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if (msg.getData().getBoolean(ANSWER)){
                    socket.connect();
                    socket.subscribe(Subscriber.MESSAGES);
                    context.startActivity(new Intent(context, ChatsActivity.class));
                    AppState.state = AppState.State.chats;
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    AppState.state = AppState.State.login;
                }
            }
        };
        new Thread(() -> {
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putBoolean(ANSWER, SignService.trySignIn(context));
            message.setData(bundle);
            handler.sendMessage(message);
        }).start();
    }

    private void createNotification(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Notification notification = NotificationBuilder.build(context, NOTIFICATION_ID);
            startForeground(NOTIFICATION_ID, notification);
        } else {
            Toast.makeText(context, getResources().getString(R.string.serviceRun), Toast.LENGTH_SHORT).show();
        }
    }

    public class ServiceBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timerRunning){
            findTimer.cancel();
        }
        socket.disconnect();
    }

    public class FindServerTask extends TimerTask {
        @Override
        public void run() {
            Log.i("Find task", "Start auto");
            findServer();
        }
    }
}
