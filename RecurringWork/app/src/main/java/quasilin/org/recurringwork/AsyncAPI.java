package quasilin.org.recurringwork;

import android.util.Log;

import androidx.work.ListenableWorker;

/**
 * Created by szpt_user045 on 08.01.2020.
 */

class AsyncAPI {
    interface OnResult {
        void onSuccess(ListenableWorker.Result result);
        void onError(ListenableWorker.Result.Failure failure);
    }

    void load(OnResult onResult) {
        Log.i("", "load");
        ListenableWorker.Result.retry();
    }
}
