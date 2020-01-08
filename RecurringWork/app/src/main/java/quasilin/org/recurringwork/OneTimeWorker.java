package quasilin.org.recurringwork;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by szpt_user045 on 08.01.2020.
 */

class OneTimeWorker extends Worker{

    private int count = 0;
    public OneTimeWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public Result doWork() {
        Log.i("One time worker:", "Do work: " + ++count);
        if(count < 10){
            return Result.retry();
        } else {
            return Result.success();
        }
    }
}
