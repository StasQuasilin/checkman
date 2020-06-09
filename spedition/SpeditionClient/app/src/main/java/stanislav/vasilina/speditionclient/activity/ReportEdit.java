package stanislav.vasilina.speditionclient.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.adapters.ReportFieldAdapter;
import stanislav.vasilina.speditionclient.dialogs.DateDialog;
import stanislav.vasilina.speditionclient.dialogs.DateDialogState;
import stanislav.vasilina.speditionclient.dialogs.DriverEditDialog;
import stanislav.vasilina.speditionclient.dialogs.ExpensesDialog;
import stanislav.vasilina.speditionclient.dialogs.NoteEditDialog;
import stanislav.vasilina.speditionclient.dialogs.RouteEditDialog;
import stanislav.vasilina.speditionclient.entity.Driver;
import stanislav.vasilina.speditionclient.entity.Expense;
import stanislav.vasilina.speditionclient.entity.Person;
import stanislav.vasilina.speditionclient.entity.Product;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.ReportField;
import stanislav.vasilina.speditionclient.entity.Route;
import stanislav.vasilina.speditionclient.utils.CustomListener;
import stanislav.vasilina.speditionclient.utils.ProductsUtil;
import stanislav.vasilina.speditionclient.utils.ReportsUtil;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;

public class ReportEdit extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private ReportsUtil reportsUtil;
    private Report report;
    private ReportFieldAdapter adapter;

    private Button driverButton;
    private Button dateButton;
    private Button timeButton;
    private Button routeButton;
    private Button doneDateButton;
    private Button doneTimeButton;
    private EditText fareEdit;
    private Button expensesButton;

    private ProductsUtil productsUtil = new ProductsUtil();

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
        final ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setTitle(R.string.edit);

        adapter = new ReportFieldAdapter(context, R.layout.field_list_row, getSupportFragmentManager(), new CustomListener() {
            @Override
            public void onChange() {
                save(false);
            }
        });
        reportsUtil = new ReportsUtil(context);
        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra(ID);
        if (uuid != null) {
            report = reportsUtil.openReport(uuid);
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
                    driver.setUuid(UUID.randomUUID().toString());
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

        routeButton = findViewById(R.id.routeButton);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Route route = report.getRoute();
                if (route == null){
                    route = new Route();
                    report.setRoute(route);
                }
                RouteEditDialog routeEditDialog = new RouteEditDialog(route, getLayoutInflater(), new CustomListener() {
                    @Override
                    public void onChange() {
                        buildRoute();
                    }
                });
                routeEditDialog.show(getSupportFragmentManager(), "Route Edit");
            }
        });
        buildRoute();

        final Spinner productList = findViewById(R.id.productSpinner);
        final ArrayAdapter<Product> productAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item);
        productAdapter.addAll(productsUtil.getProducts());
        productList.setAdapter(productAdapter);
        Product product = report.getProduct();
        if (product == null){
            product = productAdapter.getItem(0);
            report.setProduct(product);
        }
        productList.setSelection(productAdapter.getPosition(product));
        productList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Product product = productAdapter.getItem(position);
                report.setProduct(product);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        doneDateButton = findViewById(R.id.doneDateButton);
        doneDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dd = report.getDoneDate();
                if (dd == null){
                    dd = Calendar.getInstance();
                    report.setDoneDate(dd);
                }
                DateDialog doneDialog = new DateDialog(dd, getLayoutInflater(), DateDialogState.date, new CustomListener() {
                    @Override
                    public void onChange() {
                        initDoneButton();
                    }
                });
                doneDialog.show(getSupportFragmentManager(), "Done Date");
            }
        });
        doneTimeButton = findViewById(R.id.doneTimeButton);
        doneTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dd = report.getDoneDate();
                if (dd == null){
                    dd = Calendar.getInstance();
                    report.setDoneDate(dd);
                }
                DateDialog doneDialog = new DateDialog(dd, getLayoutInflater(), DateDialogState.time, new CustomListener() {
                    @Override
                    public void onChange() {
                        initDoneButton();
                    }
                });
                doneDialog.show(getSupportFragmentManager(), "Done Time");
            }
        });

        if (report.getDoneDate() != null){
            initDoneButton();
        }

        fareEdit = findViewById(R.id.fareEdit);
        fareEdit.setText(String.valueOf(report.getFare()));

        expensesButton = findViewById(R.id.expensesEdit);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesDialog expensesDialog = new ExpensesDialog(report.getExpenses(), getLayoutInflater(), new CustomListener() {
                    @Override
                    public void onChange() {
                        calculateExpenses();
                        save(false);
                    }
                });
                expensesDialog.show(getSupportFragmentManager(), "Expenses Dialog");
            }
        });
        calculateExpenses();

        final Button noteButton = findViewById(R.id.notesButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteEditDialog noteEditDialog = new NoteEditDialog(getApplicationContext(), report.getNotes(), getLayoutInflater(), new CustomListener() {
                    @Override
                    public void onChange() {
                        save(false);
                    }
                });
                noteEditDialog.show(getSupportFragmentManager(), "Notes Dialog");
            }
        });

        ListView reports = findViewById(R.id.fields);
        reports.setAdapter(adapter);

        FloatingActionButton addField = findViewById(R.id.addField);
        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReportField field = new ReportField(Calendar.getInstance());
                field.setUuid(UUID.randomUUID().toString());
                report.addField(field);
                adapter.add(field);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculateExpenses() {
        int expenses = 0;
        for (Expense expense : report.getExpenses()){
            expenses += expense.getAmount();
        }

        expensesButton.setText(Integer.toString(expenses));
    }

    private void initDoneButton() {
        final Calendar doneDate = report.getDoneDate();
        if (doneDate != null) {
            simpleDateFormat.applyPattern("dd.MM.yy");
            doneDateButton.setText(simpleDateFormat.format(doneDate.getTime()));
            simpleDateFormat.applyPattern("HH:mm");
            doneTimeButton.setText(simpleDateFormat.format(doneDate.getTime()));
        }
    }

    private void buildRoute() {
        final Route route = report.getRoute();
        if (route != null){
            routeButton.setText(route.getValue());
        }
    }

    private void initDateButton() {
        final Calendar leaveTime = report.getLeaveTime();
        if (leaveTime != null){
            simpleDateFormat.applyPattern("dd.MM.yy");
            dateButton.setText(simpleDateFormat.format(leaveTime.getTime()));
            simpleDateFormat.applyPattern("HH:mm");
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
            save(true);
        } else if (itemId == R.id.cancel){
            onBackPressed();
        } else if (itemId == R.id.remove){

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.i("Edit", "Back");
        super.onBackPressed();
    }

    void save(boolean redirect){

        Log.i("Report Edit", "Save report, redirect: " + redirect);
        final String fareText = fareEdit.getText().toString();
        int fare = 0;
        if (!fareText.isEmpty()){
            fare = Integer.parseInt(fareText);
        }
        report.setFare(fare);
        report.setSync(false);


        reportsUtil.saveReport(report);

        if (redirect) {
            final Context context = getApplicationContext();
            Intent intent = new Intent(context, Reports.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            final List<ReportField> fields = report.getFields();
            Collections.sort(fields);
            adapter.clear();
            adapter.addAll(fields);
        }
    }
}
