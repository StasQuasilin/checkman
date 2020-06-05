package stanislav.vasilina.speditionclient.dialogs;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.List;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.entity.Product;
import stanislav.vasilina.speditionclient.entity.ReportField;
import stanislav.vasilina.speditionclient.entity.Weight;
import stanislav.vasilina.speditionclient.utils.CustomListener;
import stanislav.vasilina.speditionclient.utils.ProductsUtil;

public class ReportFieldEditDialog extends DialogFragment {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat();
    private final ReportField reportField;
    private final LayoutInflater inflater;
    private final CustomListener saveListener;

    private EditText counterparty;
    private Spinner product;
    private EditText moneyEdit;
    private EditText gross;
    private EditText tare;
    private Button dateButton;
    private Button timeButton;
    private boolean haveWeight;
    private Button addWeight;
    private ConstraintLayout weightLayout;
    private Switch paymentSwitch;
    private final List<Product> products;

    public ReportFieldEditDialog(ReportField reportField, LayoutInflater inflater, CustomListener saveListener) {
        this.reportField = reportField;
        this.inflater = inflater;
        this.saveListener = saveListener;

        ProductsUtil productsUtil = new ProductsUtil();
        products = productsUtil.getProducts();

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

        dateButton = view.findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.date, new CustomListener() {
                    @Override
                    public void onChange() {
                        changeDate();
                    }
                });
                dateDialog.show(getChildFragmentManager(), "DateDialog");
            }
        });
        changeDate();

        timeButton = view.findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.time, new CustomListener() {
                    @Override
                    public void onChange() {
                        changeTime();
                    }
                });
                dateDialog.show(getChildFragmentManager(), "TimeDialog");
            }
        });
        changeTime();

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

    private void changeDate(){
        sdf.applyPattern("dd.MM.yy");
        dateButton.setText(sdf.format(reportField.getArriveTime().getTime()));
    }
    private void changeTime(){
        sdf.applyPattern("HH:mm");
        timeButton.setText(sdf.format(reportField.getArriveTime().getTime()));
    }

    private void initProductSpinner() {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        adapter.addAll(products);
        product.setAdapter(adapter);
    }

    private void save(){

        final String counterparty = this.counterparty.getText().toString();
        reportField.setCounterparty(counterparty);

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
