package ua.quasilin.chekmanszpt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ResourceBundle;

import ua.quasilin.chekmanszpt.R;
import ua.quasilin.chekmanszpt.services.BackgroundService;
import ua.quasilin.chekmanszpt.utils.ServiceStarter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceStarter.start(getApplicationContext(), new Intent(getApplicationContext(), BackgroundService.class));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }
}
