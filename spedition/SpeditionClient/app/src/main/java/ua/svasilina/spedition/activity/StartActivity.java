package ua.svasilina.spedition.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.services.ActiveReportService;
import ua.svasilina.spedition.utils.db.DBUtil;
import ua.svasilina.spedition.utils.db.OnSyncDone;

public class StartActivity extends AppCompatActivity {

    DBUtil dbUtil;
    TextView textInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextCompat.startForegroundService(getApplicationContext(), new Intent(getApplicationContext(), ActiveReportService.class));
        setContentView(R.layout.start_activity);
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.hide();
        }

        dbUtil = new DBUtil(getApplicationContext());

        dbUtil.syncDB(this, new OnSyncDone() {
            @Override
            public void done() {
                Intent intent = new Intent(getApplicationContext(), Reports.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "'Back' not support yet", Toast.LENGTH_SHORT).show();
    }
}
