package ua.quasilin.chekmanszpt.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import ua.quasilin.chekmanszpt.R;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class BackgroundService extends Service {

    final IBinder binder = new ServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(String.valueOf(BackgroundService.class), "BACKGROUND SERVICE CREATE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(String.valueOf(BackgroundService.class), "BACKGROUND SERVICE STARTED");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            super.startForeground(1, buildNotification(getBaseContext()));
        }
        Toast.makeText(getApplicationContext(),
                "Служба работает в фоновом режиме", Toast.LENGTH_LONG).show();
        return START_STICKY;

    }
    private static final String channelId = "ua.quasilin.checkman";

    Notification buildNotification(Context context){
        Intent resultIntent = new Intent(context, BackgroundService.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setOngoing(true)
                        .setContentText(context.getResources().getString(R.string.notify))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(resultPendingIntent)
                ;

        //        notificationManager.notify(id, notification);
        return builder.build();
    }

    public class ServiceBinder extends Binder {
        public BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
