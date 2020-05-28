package stanislav.vasilina.speditionclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.ReportListAdapter;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.utils.StorageUtil;

public class Reports extends AppCompatActivity {

    private ReportListAdapter adapter;
    private final ArrayList<Report> reports = new ArrayList<>();
    private StorageUtil storageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        storageUtil = new StorageUtil(context);

        setContentView(R.layout.activity_main);

        reports.addAll(storageUtil.readStorage());
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
            addItem();
        } else if (itemId == R.id.clear){
            storageUtil.clearStorage();
            adapter.clear();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItem(){
//        final Context context = getApplicationContext();
//        Intent intent = new Intent(context, ReportEdit.class);
//        context.startActivity(intent);

        Report report = new Report();
        report.setUuid(UUID.randomUUID().toString());
        storageUtil.saveReport(report);
        adapter.add(report);
    }


}
