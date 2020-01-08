package com.example.kvasik.workmanger.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.kvasik.workmanger.R;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Child", "Create");
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);


    }

}
