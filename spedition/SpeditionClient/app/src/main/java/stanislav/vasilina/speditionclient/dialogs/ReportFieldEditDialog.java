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
import android.widget.EditText;
import android.widget.Spinner;

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
import stanislav.vasilina.speditionclient.utils.ProductsUtil;

public class ReportFieldEditDialog extends DialogFragment {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat();
    private final ReportField reportField;
    private final LayoutInflater inflater;

    private EditText counterparty;
    private Spinner product;
    private EditText sum;
    private EditText gross;
    private EditText tare;
    private Button dateButton;
    private Button timeButton;
    private boolean haveWeight;
    private Button addWeight;
    private ConstraintLayout weightLayout;
    private final ProductsUtil productsUtil = new ProductsUtil();
    private final List<Product> products;


    public ReportFieldEditDialog(ReportField reportField, LayoutInflater inflater) {
        this.reportField = reportField;
        this.inflater = inflater;
        haveWeight = reportField.getWeight() != null;
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
         dateButton = view.findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.date, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                final DateDialog dateDialog = new DateDialog(reportField.getArriveTime(), inflater, DateDialogState.time, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeTime();
                    }
                });
                dateDialog.show(getChildFragmentManager(), "TimeDialog");
            }
        });
        changeTime();

        product = view.findViewById(R.id.product);
        initProductSpinner();
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

        final int selectedItemPosition = this.product.getSelectedItemPosition();
        final Product product = products.get(selectedItemPosition);
        reportField.setProduct(product);

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
//        reportField.setIndex(Integer.parseInt(numberText.getText().toString()));
    }
}
