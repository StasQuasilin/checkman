package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.services.BackgroundService;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class Starter {

    private static final String tag = "Starter";
    public static final String FLAG = "flag";

    public static void serviceStart(Context context, int flag) {
        if (!RunChecker.isRun(BackgroundService.class, context)) {
            Intent intent = new Intent(context, BackgroundService.class);
            intent.putExtra(FLAG, flag);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                ContextCompat.startForegroundService(context, intent);
//            } else {
//
//            }
            context.startService(intent);

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
