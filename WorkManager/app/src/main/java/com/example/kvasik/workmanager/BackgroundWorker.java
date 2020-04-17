package com.example.kvasik.workmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.kvasik.workmanager.utils.WorkManagerUtil;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by Kvasik on 08.01.2020.
 */

public class BackgroundWorker extends Worker {

    private Handler handler;
    public static final String TAG = "BACKGROUND WORKER";

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                Date currentTime = Calendar.getInstance().getTime();
                String msg = "Do work at " + currentTime;
                Log.i("Worker", msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        };
        handler.sendMessage(new Message());
        WorkManagerUtil.runWorker(getApplicationContext());
        return Result.success();
    }
}
