package ua.svasilina.spedition.activity;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.adapters.ReportFieldAdapter;
import ua.svasilina.spedition.dialogs.DateDialog;
import ua.svasilina.spedition.dialogs.DateDialogState;
import ua.svasilina.spedition.dialogs.DoneReportDialog;
import ua.svasilina.spedition.dialogs.DriverEditDialog;
import ua.svasilina.spedition.dialogs.ExpensesDialog;
import ua.svasilina.spedition.dialogs.NoteEditDialog;
import ua.svasilina.spedition.dialogs.ReportNotCompletedDialog;
import ua.svasilina.spedition.dialogs.RouteEditDialog;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Expense;
import ua.svasilina.spedition.entity.Person;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.ReportField;
import ua.svasilina.spedition.entity.Route;
import ua.svasilina.spedition.utils.CustomListener;
import ua.svasilina.spedition.utils.ProductsUtil;
import ua.svasilina.spedition.utils.ReportsUtil;

import static ua.svasilina.spedition.constants.Keys.ID;

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
    private Spinner productList;
    private Button fixLeaveTime;
    private View leaveTimeContainer;
    private Button fareEdit;
    private Button expensesButton;
    private Button noteButton;
    private View doneLayout;
    private TextView doneDateView;

    private ProductsUtil productsUtil = new ProductsUtil();

    void updateDriverButtonValue(){
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

        reportsUtil = new ReportsUtil(context);
        adapter = new ReportFieldAdapter(context, R.layout.field_list_row, getSupportFragmentManager(), report, new CustomListener() {
            @Override
            public void onChange() {
                save(false);
            }
        });

        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra(ID);

        if (uuid != null) {
            report = reportsUtil.openReport(uuid);
            adapter.addAll(report.getFields());
        } else {
            report = new Report();
        }

        driverButton = findViewById(R.id.driverButton);
        initDriverButton();

        routeButton = findViewById(R.id.routeButton);
        initRouteButton();

        productList = findViewById(R.id.productSpinner);
        initProductList();

        fixLeaveTime = findViewById(R.id.fixLeaveTime);
        leaveTimeContainer = findViewById(R.id.lealeTimeContainer);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        initLeaveButtons();
        checkLeaveTime();

        fareEdit = findViewById(R.id.fareEdit);
        initFareButton();

        expensesButton = findViewById(R.id.expensesEdit);
        initExpenseButton();

        noteButton = findViewById(R.id.notesButton);
        initNoteButton();

        doneLayout = findViewById(R.id.doneLayout);
        doneDateView = findViewById(R.id.doneDateLabel);
        initDoneLabel();

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

    private void initNoteButton() {
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
    }

    private void initExpenseButton() {
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesDialog expensesDialog = new ExpensesDialog(report.getExpenses(), getLayoutInflater(), new CustomListener() {
                    @Override
                    public void onChange() {
                        calculateExpenses();
                        save(false);
                    }
                }, getResources().getString(R.string.expenses_title));
                expensesDialog.show(getSupportFragmentManager(), "Expenses Dialog");
            }
        });
        calculateExpenses();
    }

    private void initFareButton() {
        fareEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpensesDialog fareDialog = new ExpensesDialog(report.getFares(), getLayoutInflater(), new CustomListener() {
                    @Override
                    public void onChange() {
                        calculateFare();
                        save(false);
                    }
                }, getResources().getString(R.string.fares));
                fareDialog.show(getSupportFragmentManager(), "Fare Dialog");
            }
        });
        calculateFare();
    }

    private void initLeaveButtons() {
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
                        updateLeaveButtons();
                    }
                });
                dateDialog.show(getSupportFragmentManager(), "DateEdit");
            }
        });

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
                        updateLeaveButtons();
                    }
                });
                dateDialog.show(getSupportFragmentManager(), "DateEdit");
            }
        });
        updateLeaveButtons();
    }

    private void initFixLeaveButton() {
        fixLeaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setLeaveTime(Calendar.getInstance());
                checkLeaveTime();
            }
        });
    }

    private void checkLeaveTime() {
        if (report.getLeaveTime() == null){
            leaveTimeContainer.setVisibility(View.GONE);
            initFixLeaveButton();
        } else {
            fixLeaveTime.setVisibility(View.GONE);
            leaveTimeContainer.setVisibility(View.VISIBLE);
            updateLeaveButtons();
        }
    }

    private void initProductList() {
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
    }

    private void initRouteButton() {
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
                        updateRouteButtonValue();
                    }
                });
                routeEditDialog.show(getSupportFragmentManager(), "Route Edit");
            }
        });
        updateRouteButtonValue();
    }

    private void initDriverButton() {
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
                        updateDriverButtonValue();
                        save(false);
                    }
                };
                DriverEditDialog driverEditDialog = new DriverEditDialog(driver, getLayoutInflater(), cl);
                driverEditDialog.show(getSupportFragmentManager(), "DriverEdit");
            }
        });
        updateDriverButtonValue();
    }

    private void calculateFare() {
        int fare = 0;
        for (Expense expense : report.getFares()){
            fare += expense.getAmount();
        }
        fareEdit.setText(String.valueOf(fare));
    }

    private void calculateExpenses() {
        int expenses = 0;
        for (Expense expense : report.getExpenses()){
            expenses += expense.getAmount();
        }
        expensesButton.setText(String.valueOf(expenses));
    }

    private void initDoneLabel() {
        final Calendar doneDate = report.getDoneDate();
        if (doneDate != null) {
            doneLayout.setVisibility(View.VISIBLE);
            simpleDateFormat.applyPattern("dd.MM.yy HH:mm");
            doneDateView.setText(simpleDateFormat.format(doneDate.getTime()));
        } else {
            doneLayout.setVisibility(View.GONE);
        }
    }

    private void updateRouteButtonValue() {
        final Route route = report.getRoute();
        if (route != null){
            routeButton.setText(route.getValue());
        }
    }

    private void updateLeaveButtons() {
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
        } else if (itemId == R.id.done){
            doneReport();

        }
        return false;
    }

    private void doneReport() {
        final Driver driver = report.getDriver();
        boolean emptyDriver = driver == null || driver.isEmpty();
        final Route route = report.getRoute();
        boolean emptyRoute = route == null || route.isEmpty();
        final Calendar leaveTime = report.getLeaveTime();
        boolean emptyLeave = leaveTime == null;
        boolean emptyFields = report.getFields().size() == 0;

        if (emptyDriver || emptyRoute || emptyLeave || emptyFields){
            final ReportNotCompletedDialog dialog = new ReportNotCompletedDialog(emptyDriver, emptyRoute, emptyLeave, emptyFields);
            dialog.show(getSupportFragmentManager(), "NoNoNo");
        } else {
            DoneReportDialog drd = new DoneReportDialog(new CustomListener() {
                @Override
                public void onChange() {
                    save(false);
                    initDoneLabel();
                }
            });
            drd.show(getSupportFragmentManager(), "Done dialog");
        }
    }

    @Override
    public void onBackPressed() {
        Log.i("Edit", "Back");
        super.onBackPressed();
    }

    void save(boolean redirect){

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
