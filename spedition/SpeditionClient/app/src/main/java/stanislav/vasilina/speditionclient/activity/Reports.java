package stanislav.vasilina.speditionclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.ReportListAdapter;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.utils.ReportsUtil;
import stanislav.vasilina.speditionclient.utils.SyncUtil;

public class Reports extends AppCompatActivity {

    private ReportListAdapter adapter;
    private final ArrayList<Report> reports = new ArrayList<>();
    private ReportsUtil reportsUtil;
    private SyncUtil syncUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();
        reportsUtil = new ReportsUtil(context);
        syncUtil = new SyncUtil(reportsUtil);
//        syncUtil.sync();

        reports.addAll(reportsUtil.readStorage());
        Collections.sort(reports);
        adapter = new ReportListAdapter(context, R.layout.report_list_row, reports);
        ListView view = findViewById(R.id.report_list);
        view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.add){
            newItem();
        } else if (itemId == R.id.clear){
            reportsUtil.clearStorage();
            adapter.clear();
        }
        return super.onOptionsItemSelected(item);
    }

    private void newItem(){
        final Context context = getApplicationContext();
        Intent intent = new Intent(context, ReportEdit.class);
        context.startActivity(intent);
    }
}
