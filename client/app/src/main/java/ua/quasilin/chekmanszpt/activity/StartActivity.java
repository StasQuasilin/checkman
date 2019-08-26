package ua.quasilin.chekmanszpt.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.utils.AppState;
import ua.quasilin.chekmanszpt.utils.FindServer;
import ua.quasilin.chekmanszpt.utils.Starter;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class StartActivity extends Activity {

    public static boolean active = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Starter.serviceStart(getApplicationContext());
        setContentView(R.layout.loading);
        if (AppState.state == AppState.State.findServer) {
            TextView loadingInfo = findViewById(R.id.loadingInfo);
            loadingInfo.setText(getResources().getString(R.string.findServer));
        } else {
            AppState.openActivity(getApplicationContext());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }
}
