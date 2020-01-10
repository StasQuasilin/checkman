package com.example.kvasik.workmanger.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.kvasik.workmanger.R;
import com.example.kvasik.workmanger.utils.WorkManagerUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Kvasik on 08.01.2020.
 */

public abstract class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Parent", "Create");
        WorkManagerUtil.stopWorker(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        WorkManagerUtil.stopWorker(getApplicationContext());
    }

    @Override
    protected void onPause() {
        Log.i("Parent", "Application paused");
        Toast.makeText(getApplicationContext(), "Application paused", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("Parent", "Application stop");
        Toast.makeText(getApplicationContext(), "Application stop", Toast.LENGTH_SHORT).show();
        WorkManagerUtil.runWorker(getApplicationContext());
        super.onStop();
        
    }
}
