package stanislav.vasilina.speditionclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.ReportFieldAdapter;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.ReportField;
import stanislav.vasilina.speditionclient.utils.StorageUtil;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;

public class ReportEdit extends AppCompatActivity {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private StorageUtil storageUtil;
    private Report report;
    private ReportFieldAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        storageUtil = new StorageUtil(context);
        setContentView(R.layout.activity_report_edit);
        adapter = new ReportFieldAdapter(context, R.layout.field_list_row, getSupportFragmentManager());

        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra(ID);
        if (uuid != null) {
            report = storageUtil.openReport(uuid);
            adapter.addAll(report.getFields());
        } else {
            report = new Report();
            report.setLeaveTime(Calendar.getInstance().getTime());
        }

        ListView reports = findViewById(R.id.fields);
        reports.setAdapter(adapter);

        FloatingActionButton addField = findViewById(R.id.addField);

        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReportField field = new ReportField(Calendar.getInstance());
                adapter.add(field);
                report.addField(field);
            }
        });

        final Button dateButton = findViewById(R.id.dateButton);

        simpleDateFormat.applyPattern("dd.MM.yy");
        final Date date = new Date();
        final String format1 = simpleDateFormat.format(date);
        dateButton.setText(format1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if(itemId == R.id.save){
            save();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.i("Edit", "Back");
        super.onBackPressed();
    }

    void save(){
        storageUtil.saveReport(report);

        final Context context = getApplicationContext();
        Intent intent = new Intent(context, Reports.class);
        context.startActivity(intent);
    }
}
