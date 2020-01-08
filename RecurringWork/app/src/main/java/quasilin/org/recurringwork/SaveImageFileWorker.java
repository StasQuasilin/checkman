package quasilin.org.recurringwork;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

/**
 * Created by szpt_user045 on 08.01.2020.
 */

class SaveImageFileWorker extends ListenableWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public SaveImageFileWorker(Context appContext, WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    private AsyncAPI asyncAPI = new AsyncAPI();
    @Override
    public ListenableFuture<Result> startWork() {

        return CallbackToFutureAdapter.getFuture(completer -> {
            asyncAPI.load(new AsyncAPI.OnResult(){

                @Override
                public void onSuccess(Result result) {
                    Log.i("", "success");
                    completer.set(result);
                }

                @Override
                public void onError(Result.Failure failure) {
                    Log.i("", "error");
                }
            });
            Log.i("SAFW", "Do");

            Toast.makeText(getApplicationContext(), "Do Work1!", Toast.LENGTH_LONG).show();
            return "AsyncAPI.load result";
        });
    }
}
