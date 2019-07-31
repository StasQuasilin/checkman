package ua.quasilin.chekmanszpt.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class NotificationBuilder {
    private static final String CHANNEL_NAME = "Checkman Channel";
    private static final String CHANNEL_ID = "checkman";
    public static Notification build(Context context, int id){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, id, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setOngoing(true)
                        .setContentText(context.getResources().getString(R.string.notification_description))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(resultPendingIntent)
                ;
        return builder.build();
    }
}
