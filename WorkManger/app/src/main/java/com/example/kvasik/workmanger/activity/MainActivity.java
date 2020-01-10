package com.example.kvasik.workmanger.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.kvasik.workmanger.R;

import androidx.appcompat.widget.Toolbar;


public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Child", "Create");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
    }

}
