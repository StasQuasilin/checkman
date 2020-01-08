package com.example.kvasik.workmanger.utils;

import android.content.Context;
import android.util.Log;

import com.example.kvasik.workmanger.BackgroundWorker;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkManager;

/**
 * Created by Kvasik on 08.01.2020.
 */

public class WorkManagerUtil {

    private static final int DURATION = 10;

    public static void runWorker(final Context context){
        OneTimeWorkRequest one = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                .addTag(BackgroundWorker.TAG)
                .setInitialDelay(DURATION, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(one);
    }

    public static void stopWorker(final Context context) {
        Log.i("WorkManagerUtil", "Stop Worker" + BackgroundWorker.TAG);
        WorkManager.getInstance(context).cancelAllWorkByTag(BackgroundWorker.TAG);
    }
}
