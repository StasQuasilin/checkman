package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import ua.quasilin.chekmanszpt.services.BackgroundService;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class ServiceStarter {
    public static void start(Context context, Intent intent) {
        if (!RunChecker.isRun(BackgroundService.class, context)) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(context, new Intent(context, BackgroundService.class));
                } else {
                    context.startService(new Intent(context, BackgroundService.class));
                }
            }
        }

    }
}
