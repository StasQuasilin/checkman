package quasilin.org.recurringwork;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneTimeWorkRequest build = new OneTimeWorkRequest.Builder(OneTimeWorker.class).build();
        WorkManager.getInstance(getApplicationContext()).enqueue(build);
    }
}