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

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.ReportFieldAdapter;
import stanislav.vasilina.speditionclient.dialogs.DateDialog;
import stanislav.vasilina.speditionclient.dialogs.DateDialogState;
import stanislav.vasilina.speditionclient.dialogs.DriverEditDialog;
import stanislav.vasilina.speditionclient.entity.Driver;
import stanislav.vasilina.speditionclient.entity.Person;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.ReportField;
import stanislav.vasilina.speditionclient.utils.CustomListener;
import stanislav.vasilina.speditionclient.utils.ReportsUtil;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;

public class ReportEdit extends AppCompatActivity {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private ReportsUtil storageUtil;
    private Report report;
    private ReportFieldAdapter adapter;

    private Button driverButton;
    private Button dateButton;
    private Button timeButton;

    void initDriverButton(){
        final Driver driver = report.getDriver();
        if (driver != null){
            final Person person = driver.getPerson();
            final String value = person.getForename() + " " + person.getSurname();
            driverButton.setText(value);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_edit);
        final Context context = getApplicationContext();
        adapter = new ReportFieldAdapter(context, R.layout.field_list_row, getSupportFragmentManager());
        storageUtil = new ReportsUtil(context);
        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra(ID);
        if (uuid != null) {
            report = storageUtil.openReport(uuid);
            adapter.addAll(report.getFields());
        } else {
            report = new Report();
            report.setLeaveTime(Calendar.getInstance());
        }

        driverButton = findViewById(R.id.driverButton);
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver driver = report.getDriver();
                if (driver == null){
                    driver = new Driver();
                    driver.setPerson(new Person());
                    report.setDriver(driver);
                }
                CustomListener cl = new CustomListener() {
                    @Override
                    public void onChange() {
                        initDriverButton();
                    }
                };
                DriverEditDialog driverEditDialog = new DriverEditDialog(driver, getLayoutInflater(), cl);
                driverEditDialog.show(getSupportFragmentManager(), "DriverEdit");
            }
        });
        initDriverButton();

        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar leaveTime = report.getLeaveTime();
                if (leaveTime == null){
                    leaveTime = Calendar.getInstance();
                    report.setLeaveTime(leaveTime);
                }
                DateDialog dateDialog = new DateDialog(leaveTime, getLayoutInflater(), DateDialogState.date, new CustomListener() {
                    @Override
                    public void onChange() {
                        initDateButton();
                    }
                });
                dateDialog.show(getSupportFragmentManager(), "DateEdit");
            }
        });
        timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar leaveTime = report.getLeaveTime();
                if (leaveTime == null){
                    leaveTime = Calendar.getInstance();
                    report.setLeaveTime(leaveTime);
                }
                DateDialog dateDialog = new DateDialog(leaveTime, getLayoutInflater(), DateDialogState.time, new CustomListener() {
                    @Override
                    public void onChange() {
                        initDateButton();
                    }
                });
                dateDialog.show(getSupportFragmentManager(), "DateEdit");
            }
        });
        initDateButton();
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
    }

    private void initDateButton() {
        final Calendar leaveTime = report.getLeaveTime();
        if (leaveTime != null){
            simpleDateFormat.applyPattern("dd.MM.yy");
            dateButton.setText(simpleDateFormat.format(leaveTime.getTime()));
            simpleDateFormat.applyPattern("hh.mm");
            timeButton.setText(simpleDateFormat.format(leaveTime.getTime()));
        }
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
