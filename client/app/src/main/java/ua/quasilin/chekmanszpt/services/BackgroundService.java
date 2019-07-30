package ua.quasilin.chekmanszpt.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class BackgroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(String.valueOf(BackgroundService.class), "Background service create");
    }
}
