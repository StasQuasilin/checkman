package ua.svasilina.spedition.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.adapters.ReportListAdapter;
import ua.svasilina.spedition.dialogs.LoginDialog;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.utils.ReportsUtil;

public class Reports extends AppCompatActivity {

    private ReportListAdapter adapter;
    private final ArrayList<Report> reports = new ArrayList<>();
    private ReportsUtil reportsUtil;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();

        reportsUtil = new ReportsUtil(context);
        reports.addAll(reportsUtil.readStorage());
        adapter = new ReportListAdapter(context, R.layout.report_list_row, reports);
        ListView view = findViewById(R.id.report_list);
        if (view != null){
            view.setAdapter(adapter);
        }

        reportsUtil.sync();
//        new LocationService(context).checkGranted(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            final MenuItem item = menu.findItem(R.id.versionCode);
            long versionCode;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                versionCode = pInfo.getLongVersionCode();
            } else {
                versionCode = pInfo.versionCode;
            }
            item.setTitle(pInfo.versionName + " ( " + versionCode + " )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.add){
            newItem();
        } else if (itemId == R.id.login){
            showLoginDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSyncList() {
        final Context context = getApplicationContext();
        Intent intent = new Intent(context, SyncListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public void showLoginDialog(){
        new LoginDialog(this, getLayoutInflater()).show(getSupportFragmentManager(), "Login Dialog");
    }

    private void newItem(){
        final Context context = getApplicationContext();
        Intent intent = new Intent(context, ReportEdit.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), R.string.pressBack, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
