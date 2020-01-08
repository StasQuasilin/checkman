package quasilin.org.recurringwork;

import android.os.Bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WorkManager.initialize( getApplicationContext(),
//                new Configuration.Builder()
//                        .setExecutor(Executors.newFixedThreadPool(8))
//                        .build());

        OneTimeWorkRequest build = new OneTimeWorkRequest.Builder(OneTimeWorker.class).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(build);
//        Constraints constraints = new Constraints.Builder()
//                .setRequiresCharging(true)
//                .build();
//
//        PeriodicWorkRequest saveRequest =
//                new PeriodicWorkRequest.Builder(SaveImageFileWorker.class, 5, TimeUnit.SECONDS)
//                        .setConstraints(constraints)
//                        .build();
//
//        WorkManager.getInstance(getApplicationContext())
//                .enqueue(saveRequest);
    }
}
