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
    private final List<Product> products;
    private boolean isPlus = true;


    public ReportFieldEditDialog(ReportField reportField, LayoutInflater inflater, CustomListener saveListener) {
        this.reportField = reportField;
        this.inflater = inflater;
        this.saveListener = saveListener;
        haveWeight = reportField.getWeight() != null;
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

        final Switch paymentSwitch = view.findViewById(R.id.paymentSwitch);
        paymentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPlus = isChecked;
                if (isChecked){
                    paymentSwitch.setText(R.string.give);
                } else {
                    paymentSwitch.setText(R.string.discarded);
                }
            }
        });

        moneyEdit = view.findViewById(R.id.money);

        gross = view.findViewById(R.id.gross);
        tare = view.findViewById(R.id.tare);
        EditText net = view.findViewById(R.id.net);
        NetWatcher watcher = new NetWatcher(gross, tare, net);
        gross.addTextChangedListener(watcher);
        tare.addTextChangedListener(watcher);
        addWeight = view.findViewById(R.id.addWeight);
        weightLayout = view.findViewById(R.id.weight);

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
            if(!isPlus){
                money *= -1;
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
                final int gross = Integer.parseInt(grossString);
                weight.setGross(gross);
            }
            final String tareString = tare.getText().toString();
            if (!tareString.isEmpty()){
                final int tare = Integer.parseInt(tareString);
                weight.setTare(tare);
            }
        } else {
            reportField.setWeight(null);
        }
        saveListener.onChange();
//        reportField.setIndex(Integer.parseInt(numberText.getText().toString()));
    }
}
