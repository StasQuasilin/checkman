package ua.quasilin.chekmanszpt.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class RunChecker {
    public static boolean isRun(Class<?> serviceClass, Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
