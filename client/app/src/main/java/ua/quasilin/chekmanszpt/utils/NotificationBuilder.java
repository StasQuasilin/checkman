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
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class NotificationBuilder {
    private static final String CHANNEL_NAME = "Checkman Messages";
    private static final String CHANNEL_ID = "checkman";
    private static final String MESSAGE_CHANNEL = "Checkman messages";

    public static Notification build(Context context, int id){
        int importance = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_MIN;
        }
        return build(
                context, id,
                true,
                null,
                context.getResources().getString(R.string.notification_description),
                NotificationCompat.PRIORITY_MIN, new Intent(context, ChatsActivity.class),
                android.R.drawable.ic_media_play, CHANNEL_ID, importance);
    }

    public static Notification build(Context context, int id, String title, String content, Intent intent){
        int importance = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_DEFAULT;
        }
        return build(context, id, false, title, content,
                NotificationCompat.PRIORITY_DEFAULT, intent, android.R.drawable.ic_dialog_email,
                MESSAGE_CHANNEL, importance);
    }

    public static Notification build(Context context, int id, boolean ongoing,
                                     @Nullable String title, @Nullable String contentText,
                                     int priority, Intent intent, int icon, String channelId, int importance){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null){
            NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
            if (channel == null) {
                channel = new NotificationChannel(channelId, CHANNEL_NAME, importance);
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(icon)
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

    public static void closeNotification(Context context, int notificationId){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(notificationId);
        }
    }
}
