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
import android.view.WindowManager;
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
import java.util.ArrayList;
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
import ua.svasilina.spedition.dialogs.InputDialog;
import ua.svasilina.spedition.dialogs.ExpensesDialog;
import ua.svasilina.spedition.dialogs.NoteEditDialog;
import ua.svasilina.spedition.dialogs.ReportNotCompletedDialog;
import ua.svasilina.spedition.dialogs.ReportRemoveDialog;
import ua.svasilina.spedition.dialogs.RouteEditDialog;
import ua.svasilina.spedition.dialogs.WeightDialog;
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Expense;
import ua.svasilina.spedition.entity.Person;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.ReportField;
import ua.svasilina.spedition.entity.ReportNote;
import ua.svasilina.spedition.entity.Route;
import ua.svasilina.spedition.entity.Weight;
import ua.svasilina.spedition.utils.CustomListener;
import ua.svasilina.spedition.utils.ProductsUtil;
import ua.svasilina.spedition.utils.ReportsUtil;
import ua.svasilina.spedition.utils.builders.WeightStringBuilder;
import ua.svasilina.spedition.utils.search.DriverSearchUtil;

import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.LEFT_BRACE;
import static ua.svasilina.spedition.constants.Keys.RIGHT_BRACE;
import static ua.svasilina.spedition.constants.Keys.SPACE;

public class ReportEdit extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    private ReportsUtil reportsUtil;
    private Report report;
    private ReportFieldAdapter adapter;

    private View driverBox;
    private Button driverButton;
    private EditText driverInput;
    private Button dateButton;
    private Button timeButton;
    private Button routeButton;
    private Spinner productList;
    private Button weightButton;
    private Button fixLeaveTime;
    private View leaveTimeContainer;
    private Button fareEdit;
    private Button expensesButton;
    private Button noteButton;

    private ProductsUtil productsUtil;
    private WeightStringBuilder weightStringBuilder;

    void updateDriverButtonValue(){
        final Driver driver = report.getDriver();
        if (driver != null){
            final Person person = driver.getPerson();
            final String value = person.getForename() + " " + person.getSurname();
            driverButton.setText(value);
            driverBox.setVisibility(View.VISIBLE);

        }
//        else {
//            driverBox.setVisibility(View.GONE);
//            driverInput.setVisibility(View.VISIBLE);
//        }
        driverInput.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_edit);
        final Context context = getApplicationContext();
        final ActionBar supportActionBar = getSupportActionBar();
        assert supportActionBar != null;
        supportActionBar.setTitle(R.string.edit);
        weightStringBuilder = new WeightStringBuilder(getResources());
        reportsUtil = new ReportsUtil(context);
        productsUtil = new ProductsUtil(context);

        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra(ID);

        if (uuid != null) {
            report = reportsUtil.openReport(uuid);
        } else {
            report = new Report();
        }


        driverBox = findViewById(R.id.driverBox);
        driverButton = driverBox.findViewById(R.id.driverButton);
        driverInput = findViewById(R.id.driverInputField);
        initDriverButton();

        routeButton = findViewById(R.id.routeButton);
        initRouteButton();

        productList = findViewById(R.id.productSpinner);
        initProductList();

        weightButton = findViewById(R.id.weightButton);
        initWeightButton();

        fixLeaveTime = findViewById(R.id.fixLeaveTime);
        leaveTimeContainer = findViewById(R.id.leaveTimeContainer);
        dateButton = findViewById(R.id.leaveDate);
        timeButton = findViewById(R.id.leaveTime);
        initLeaveButtons();
        checkLeaveTime();

        fareEdit = findViewById(R.id.fareEdit);
        initFareButton();

        expensesButton = findViewById(R.id.expensesEdit);
        initExpenseButton();

        noteButton = findViewById(R.id.notesButton);
        initNoteButton();

        adapter = new ReportFieldAdapter(context, R.layout.field_list_row, getSupportFragmentManager(), report, new CustomListener() {
            @Override
            public void onChange() {
                save(false);
            }
        });
        final List<ReportField> fields = report.getFields();
        Collections.sort(fields);
        adapter.addAll(fields);

        ListView reports = findViewById(R.id.fields);
        reports.setAdapter(adapter);

        FloatingActionButton addField = findViewById(R.id.addField);
        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReportField field = new ReportField();
                field.setUuid(UUID.randomUUID().toString());
                report.addField(field);
                adapter.add(field);
            }
        });
        final View reportRemove = findViewById(R.id.reportRemove);
        if (uuid != null) {
            reportRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ReportRemoveDialog(context, uuid).show(getSupportFragmentManager(), "RM");
                }
            });
        } else {
            reportRemove.setVisibility(View.GONE);
        }
    }

    private void initWeightButton() {
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightDialog();
            }
        });
        updateWeightButton();
    }

    private void updateWeightButton() {
        final Weight weight = report.getWeight();
        if (weight == null){
            weightButton.setVisibility(View.GONE);
        } else {
            weightButton.setVisibility(View.VISIBLE);

            weightButton.setText(weightStringBuilder.build(weight));
        }
    }

    private void showWeightDialog() {
        Weight weight = report.getWeight();
        if (weight == null){
            weight = new Weight();
            report.setWeight(weight);
        }
        new WeightDialog(weight, getLayoutInflater(), new CustomListener() {
            @Override
            public void onChange() {
                updateWeightButton();
                save(false);
            }
        }).show(getSupportFragmentManager(), "Weight Dialog");
    }

    private void initNoteButton() {
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            NoteEditDialog noteEditDialog = new NoteEditDialog(getApplicationContext(), report.getNotes(), getLayoutInflater(), new CustomListener() {
                @Override
                public void onChange() {
                    updateNoteButton();
                    save(false);
                }
            });
            noteEditDialog.show(getSupportFragmentManager(), "Notes Dialog");
            }
        });
        updateNoteButton();
    }

    private void updateNoteButton() {
        final ArrayList<ReportNote> notes = report.getNotes();
        StringBuilder builder = new StringBuilder();
        builder.append(getResources().getString(R.string.notes));
        if (notes.size() > 0){
            builder.append(SPACE).append(LEFT_BRACE).append(SPACE);
            builder.append(notes.size()).append(SPACE);
            builder.append(RIGHT_BRACE);
        }
        noteButton.setText(builder.toString());
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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        driverInput.setFocusable(false);
        driverInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputDialog(getApplicationContext(), new DriverSearchUtil()).show(getSupportFragmentManager(), "DIG");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void calculateFare() {
        int fare = 0;
        for (Expense expense : report.getFares()){
            fare += expense.getAmount();
        }

        fareEdit.setText(getResources().getString(R.string.fares) + SPACE + fare);
    }

    @SuppressLint("SetTextI18n")
    private void calculateExpenses() {
        int expenses = 0;
        for (Expense expense : report.getExpenses()){
            expenses += expense.getAmount();
        }
        expensesButton.setText(getResources().getString(R.string.expenses_title) + SPACE + expenses);
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
            if (report.getDoneDate() == null) {
                doneReport();
            }
        } else if (itemId == R.id.weight){
            showWeightDialog();
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
            DoneReportDialog drd = new DoneReportDialog(getLayoutInflater(), report, new CustomListener() {
                @Override
                public void onChange() {
                    save(false);
                    final Context context = getApplicationContext();
                    final Intent intent = new Intent(context, ReportShow.class);
                    intent.putExtra(ID, report.getUuid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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
