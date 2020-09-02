package ua.s.vasilina.checkman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ua.s.vasilina.checkman.activity.deals.DealsActivity;
import ua.s.vasilina.checkman.client.R;

public abstract class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        checkLoginPassword();
        super.onCreate(savedInstanceState);
        setContentView(getView());
        final ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle(getPageTitle());
        init();
    }

    private void checkLoginPassword() {
        //todo check login-password
    }

    protected abstract void init();

    protected abstract int getPageTitle();

    protected abstract int getView();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.deals){
            showActivity(DealsActivity.class);
        } else if (itemId == R.id.transport){
            showActivity(TransportActivity.class);
        } else if (itemId == R.id.settings){
            showActivity(SettingActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showActivity(Class<? extends ParentActivity> aClass) {
        final Context context = getApplicationContext();
        Intent intent = new Intent(context, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        beforeDestroy();
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Application Close", Toast.LENGTH_LONG).show();
    }

    protected abstract void beforeDestroy();
}
