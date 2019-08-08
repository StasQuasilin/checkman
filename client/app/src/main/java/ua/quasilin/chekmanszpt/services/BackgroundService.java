package ua.quasilin.chekmanszpt.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.UUID;

import ua.quasilin.chekmanszpt.activity.messages.MessageActivity;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.services.socket.Socket;
import ua.quasilin.chekmanszpt.utils.NotificationBuilder;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class BackgroundService extends Service {

    final IBinder binder = new ServiceBinder();
    private Socket socket;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            super.startForeground(NOTIFICATION_ID, NotificationBuilder.build(getBaseContext(), NOTIFICATION_ID));
        } else {
            Toast.makeText(getApplicationContext(),
                    "Служба работает в фоновом режиме", Toast.LENGTH_LONG).show();
        }

        Serializable container = intent.getSerializableExtra("container");
        MessagesHandler messagesHandler = new MessagesHandler(getApplicationContext());

        socket = new Socket(getApplicationContext(), messagesHandler);
        socket.subscribe(Subscriber.MESSAGES);

        return START_STICKY;
    }

    public class ServiceBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
