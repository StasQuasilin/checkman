package quasilin.org.recurringwork;

import android.content.Context;

import com.google.common.util.concurrent.ListenableFuture;

import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

/**
 * Created by szpt_user045 on 08.01.2020.
 */

class UploadWorker extends ListenableWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public UploadWorker(Context appContext, WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @Override
    public ListenableFuture<Result> startWork() {
        return null;
    }
}
