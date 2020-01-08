package quasilin.org.recurringwork;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Calendar;
import java.util.Date;

import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by szpt_user045 on 08.01.2020.
 */

class CompressWorker extends Worker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public CompressWorker(Context appContext, WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @Override
    public Result doWork() {
        Log.i("--", "Do work!");
        return Result.retry();
    }
}
