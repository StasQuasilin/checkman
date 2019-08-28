package ua.quasilin.chekmanszpt.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import io.reactivex.annotations.Nullable;
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
        return build(
                context, id,
                true,
                null,
                null,
                NotificationCompat.PRIORITY_MAX, null
        );
    }

    public static Notification build(Context context, int id, String title, String content, Intent intent){
        return build(context, id, false, title, content, NotificationCompat.PRIORITY_DEFAULT, intent);
    }

    public static Notification build(Context context, int id, boolean ongoing,
                                     @Nullable String title, @Nullable String contentText,
                                     int priority, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null){
            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setShowWhen(false)
                    .setOngoing(ongoing)
                    .setPriority(priority)
                    .setAutoCancel(true)
                ;

        if (title != null) {
            builder.setContentTitle(title);
        }
        if (contentText != null) {
            builder.setContentText(contentText);
        }
        if (intent != null) {
            builder.setContentIntent(
                    PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            );
        }
        return builder.build();
    }
}
