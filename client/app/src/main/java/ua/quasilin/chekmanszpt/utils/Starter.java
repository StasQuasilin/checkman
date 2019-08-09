package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import ua.quasilin.chekmanszpt.activity.LoginActivity;
import ua.quasilin.chekmanszpt.activity.StartActivity;
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.services.BackgroundService;
import ua.quasilin.chekmanszpt.services.SignService;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class Starter {

    public static void serviceStart(Context context) {
        if (!RunChecker.isRun(BackgroundService.class, context)) {

            Intent intent = new Intent(context, BackgroundService.class);
            intent.putExtra("container", new ChatContainer());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(context, new Intent(context, BackgroundService.class));
            } else {
                context.startService(new Intent(context, BackgroundService.class));
            }
        }

    }
    private static void serviceStop(Context context){
        context.stopService(new Intent(context, BackgroundService.class));
    }

    public static void applicationStop(Context context) {
        serviceStop(context);
    }
}
