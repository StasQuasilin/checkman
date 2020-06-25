package ua.svasilina.spedition.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.List;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.entity.Product;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.ReportField;
import ua.svasilina.spedition.entity.Weight;
import ua.svasilina.spedition.utils.CustomListener;
import ua.svasilina.spedition.utils.ProductsUtil;
import ua.svasilina.spedition.utils.builders.DateTimeBuilder;

import static ua.svasilina.spedition.constants.Patterns.DATE_PATTERN;
import static ua.svasilina.spedition.constants.Patterns.TIME_PATTERN;

public class ReportFieldEditDialog extends DialogFragment {

    private final ReportField reportField;
    private final LayoutInflater inflater;
    private final CustomListener saveListener;

    private EditText counterparty;
    private Spinner product;
    private EditText moneyEdit;
    private EditText gross;
    private EditText tare;
    private Button fixArrive;
    private View arriveContainer;
    private Button arriveDate;
    private Button arriveTime;
    private Button fixLeave;
    private View leaveContainer;
    private Button leaveDate;
    private Button leaveTime;
    private boolean haveWeight;
    private Button addWeight;
    private ConstraintLayout weightLayout;
    private Switch paymentSwitch;
    private final List<Product> products;
    private final DateTimeBuilder dateBuilder;
    private final DateTimeBuilder timeBuilder;

    public ReportFieldEditDialog(ReportField reportField, LayoutInflater inflater, Report report, CustomListener saveListener) {
        this.reportField = reportField;
        this.inflater = inflater;
        this.saveListener = saveListener;

        ProductsUtil productsUtil = new ProductsUtil();
        if (report != null) {
            products = productsUtil.getChildren(report.getProduct());
        } else {
            products = productsUtil.getProducts();
        }
        dateBuilder = new DateTimeBuilder(DATE_PATTERN);
        timeBuilder = new DateTimeBuilder(TIME_PATTERN);
    }

    private void switchWeight(){
        if (haveWeight){
            addWeight.setVisibility(View.GONE);
            weightLayout.setVisibility(View.VISIBLE);
        } else {
            addWeight.setVisibility(View.VISIBLE);
            weightLayout.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.field_edit_dialog, null);

        counterparty = view.findViewById(R.id.counterparty);
        counterparty.setText(reportField.getCounterparty());

        fixArrive = view.findViewById(R.id.fixArrive);
        arriveContainer = view.findViewById(R.id.arriveContainer);
        arriveDate = view.findViewById(R.id.arriveDate);
        arriveTime = view.findViewById(R.id.arriveTime);
        fixLeave = view.findViewById(R.id.fixLeave);
        leaveContainer = view.findViewById(R.id.leaveContainer);
        leaveDate = view.findViewById(R.id.leaveDate);
        leaveTime = view.findViewById(R.id.leaveTime);
        initArrive();


        product = view.findViewById(R.id.details);
        initProductSpinner();

        paymentSwitch = view.findViewById(R.id.paymentSwitch);
        final int money = reportField.getMoney();

        paymentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paymentSwitch.setText(R.string.give);
                } else {
                    paymentSwitch.setText(R.string.discarded);
                }
            }
        });
        paymentSwitch.setChecked(money < 0);

        moneyEdit = view.findViewById(R.id.money);
        moneyEdit.setText(String.valueOf(Math.abs(money)));

        gross = view.findViewById(R.id.gross);
        tare = view.findViewById(R.id.tare);
        EditText net = view.findViewById(R.id.net);
        NetWatcher watcher = new NetWatcher(gross, tare, net);
        gross.addTextChangedListener(watcher);
        tare.addTextChangedListener(watcher);
        addWeight = view.findViewById(R.id.addWeight);
        weightLayout = view.findViewById(R.id.weight);
        final Weight weight = reportField.getWeight();
        haveWeight = weight != null;

        if(haveWeight){
            gross.setText(String.valueOf(weight.getGross()));
            tare.setText(String.valueOf(weight.getTare()));
        }

        switchWeight();
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveWeight = !haveWeight;
                switchWeight();
            }
        });

        builder.setView(view);

        builder.setNegativeButton(R.string.cancel, null);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        return builder.create();
    }

    private void initLeave() {
        fixLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportField.setLeaveTime(Calendar.getInstance());
                switchLeaveDateTime();
            }
        });
        final CustomListener cl = new CustomListener() {
            @Override
            public void onChange() {
                updateLeaveDateTime();
            }
        };
        leaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getLeaveTime(), inflater, DateDialogState.date, cl);
                dateDialog.show(getChildFragmentManager(), "LeaveDateDialog");
            }
        });
        leaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getLeaveTime(), inflater, DateDialogState.time, cl);
                dateDialog.show(getChildFragmentManager(), "LeaveTimeDialog");
            }
        });
        switchLeaveDateTime();
    }

    private void switchLeaveDateTime() {
        final Calendar leaveTime = reportField.getLeaveTime();
        if (leaveTime == null){
            fixLeave.setVisibility(View.VISIBLE);
            leaveContainer.setVisibility(View.GONE);
        } else {
            fixLeave.setVisibility(View.GONE);
            leaveContainer.setVisibility(View.VISIBLE);
            updateLeaveDateTime();
        }
    }

    private void updateLeaveDateTime() {
        final Calendar ldt = reportField.getLeaveTime();
        leaveDate.setText(dateBuilder.build(ldt));
        leaveTime.setText(timeBuilder.build(ldt));
    }

    private void initArrive() {
        fixArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportField.setArriveTime(Calendar.getInstance());
                switchArriveDateTime();
            }
        });
        final CustomListener cl = new CustomListener() {
            @Override
            public void onChange() {
                updateArriveDateTime();
            }
        };
        arriveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.date, cl);
                dateDialog.show(getChildFragmentManager(), "ArriveDateDialog");
            }
        });
        arriveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.time, cl);
                dateDialog.show(getChildFragmentManager(), "ArriveTimeDialog");
            }
        });
        switchArriveDateTime();
    }

    private void switchArriveDateTime(){
        final Calendar arriveTime = reportField.getArriveTime();
        if (arriveTime == null){
            fixArrive.setVisibility(View.VISIBLE);
            arriveContainer.setVisibility(View.GONE);
            fixLeave.setVisibility(View.GONE);
            leaveContainer.setVisibility(View.GONE);
        } else {
            fixArrive.setVisibility(View.GONE);
            arriveContainer.setVisibility(View.VISIBLE);
            initLeave();
            updateArriveDateTime();
        }
    }

    private void updateArriveDateTime(){
        final Calendar adt = reportField.getArriveTime();
        arriveDate.setText(dateBuilder.build(adt));
        arriveTime.setText(timeBuilder.build(adt));
    }

    ArrayAdapter<Product> adapter;
    private void initProductSpinner() {
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(products);
        product.setAdapter(adapter);
        if (reportField.getProduct() != null){
            product.setSelection(adapter.getPosition(reportField.getProduct()));
        }
    }

    private void save(){

        final String counterparty = this.counterparty.getText().toString();
        reportField.setCounterparty(counterparty);

        reportField.setProduct(adapter.getItem(product.getSelectedItemPosition()));

        final String moneyText = moneyEdit.getText().toString();
        if(!moneyText.isEmpty()){
            int money = Integer.parseInt(moneyText);
            if(paymentSwitch.isChecked()){
                money = -money;
            }
            reportField.setMoney(money);
        }

        if(haveWeight){
            Weight weight = reportField.getWeight();
            if (weight == null){
                weight = new Weight();
                reportField.setWeight(weight);
            }
            final String grossString = gross.getText().toString();
            if (!grossString.isEmpty()){
                final float gross = Float.parseFloat(grossString);
                weight.setGross(gross);
            }
            final String tareString = tare.getText().toString();
            if (!tareString.isEmpty()){
                final float tare = Float.parseFloat(tareString);
                weight.setTare(tare);
            }
        } else {
            reportField.setWeight(null);
        }
        saveListener.onChange();
//        reportField.setIndex(Integer.parseInt(numberText.getText().toString()));
    }
}
