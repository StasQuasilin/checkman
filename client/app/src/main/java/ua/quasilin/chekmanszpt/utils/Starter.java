package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.services.BackgroundService;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class Starter {

    private static final String tag = "Starter";

    public static void serviceStart(Context context) {
        Log.i(tag, "Service start");
        if (!RunChecker.isRun(BackgroundService.class, context)) {
            Intent intent = new Intent(context, BackgroundService.class);
//            intent.putExtra("container", new ChatContainer());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                ContextCompat.startForegroundService(context, new Intent(context, BackgroundService.class));
//            } else {
//                context.startService(new Intent(context, BackgroundService.class));
//            }
            context.startService(new Intent(context, BackgroundService.class));
        }
    }
    private static void serviceStop(Context context){
        Log.i(tag, "Service stop");
        context.stopService(new Intent(context, BackgroundService.class));
    }

    public static void applicationStop(Context context) {
        Log.i(tag, "Application stop");
        serviceStop(context);
    }
}
