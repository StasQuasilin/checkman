package com.example.kvasik.workmanager.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kvasik.workmanager.R;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

/**
 * Created by szpt_user045 on 14.01.2020.
 */

public class Navigator extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.navigation_view, container, false);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i("Navigator", item.getTitle().toString());
        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
