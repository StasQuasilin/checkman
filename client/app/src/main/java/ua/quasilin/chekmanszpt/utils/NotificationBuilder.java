package ua.quasilin.chekmanszpt.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import io.reactivex.annotations.Nullable;
import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;
import ua.quasilin.chekmanszpt.entity.ChannelSettings;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class NotificationBuilder {
    private static final ChannelSettings DEFAULT_CHANNEL = new ChannelSettings("1", "System");
    private static final ChannelSettings MESSAGE_CHANNEL = new ChannelSettings("2", "Messages");

    public static Notification build(Context context, int id){
        int importance = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_NONE;
        }
        return build(
                context, id,
                true,
                null,
                context.getResources().getString(R.string.notification_description),
                NotificationCompat.PRIORITY_MIN, new Intent(context, ChatsActivity.class),
                android.R.drawable.ic_media_play, DEFAULT_CHANNEL, importance);
    }

    public static Notification build(Context context, int id, String title, String content, Intent intent){
        int importance = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        return build(context, id, false, title, content,
                NotificationCompat.PRIORITY_DEFAULT, intent, android.R.drawable.ic_dialog_email,
                MESSAGE_CHANNEL, importance);
    }

    private static Notification build(Context context, int id, boolean ongoing,
                                      @Nullable String title, @Nullable String contentText,
                                      int priority, Intent intent, int icon, ChannelSettings channelSettings, int importance){

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null){
            NotificationChannel channel = notificationManager.getNotificationChannel(channelSettings.getChannelId());
            if (channel == null) {
                channel = new NotificationChannel(channelSettings.getChannelId(), channelSettings.getChannelName(), importance);
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelSettings.getChannelId())
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
        Log.i("Notificator", "Close notification " + notificationId);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);

        compat.cancel(notificationId);
    }

    public static void stopForegroung(Context context, int notificationId){

    }
}
