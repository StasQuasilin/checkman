package com.example.kvasik.workmanager.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kvasik.workmanager.R;
import com.example.kvasik.workmanager.utils.WorkManagerUtil;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Kvasik on 08.01.2020.
 */

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    HashMap<Integer, Fragment> activityHashMap;
    FragmentManager fragmentTransaction;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHashMap = new HashMap<>();
        activityHashMap.put(R.id.nav_camera, new MainActivity());
        WorkManagerUtil.stopWorker(getApplicationContext());
        fragmentTransaction = getSupportFragmentManager();
        setContentView(R.layout.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setView(R.id.nav_camera);
    }

    void setView(int id){
        if (activityHashMap.containsKey(id)) {
            Fragment fragment = activityHashMap.get(id);

            fragmentTransaction.beginTransaction().replace(R.id.content, fragment).commit();
            getSupportActionBar().setTitle(R.string.app_name);
        } else {
            Toast.makeText(getApplicationContext(), "No such fragment for " + id, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setView(id);

        DrawerLayout drawer = findViewById(R.id.draw_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.draw_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        WorkManagerUtil.stopWorker(getApplicationContext());
    }

    @Override
    public void onPause() {
        Log.i("Parent", "Application paused");
        Toast.makeText(getApplicationContext(), "Application paused", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("Parent", "Application stop");
        Toast.makeText(getApplicationContext(), "Application stop", Toast.LENGTH_SHORT).show();
        WorkManagerUtil.runWorker(getApplicationContext());
        super.onStop();
        
    }
}