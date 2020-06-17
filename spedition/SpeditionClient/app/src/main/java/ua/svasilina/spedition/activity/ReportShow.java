package ua.svasilina.spedition.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ua.svasilina.spedition.R;

public class ReportShow extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_show);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.show_title);

    }
}
