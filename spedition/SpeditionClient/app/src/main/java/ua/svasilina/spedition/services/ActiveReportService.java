package ua.svasilina.spedition.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import ua.svasilina.spedition.R;

public class ActiveReportService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String CHANNEL_NAME = "Foreground Service Channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Voyage in progress")
                .setContentText("Touch for open")
                .setSmallIcon(R.drawable.ic_logo)
//                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //todo location service - fix location
                Log.i("Background", "is alive");
            }
        };
//        timer.schedule(timerTask, 0, 1000);

        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
