package ua.svasilina.spedition.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.entity.ReportDetail;
import ua.svasilina.spedition.entity.Weight;
import ua.svasilina.spedition.utils.CustomListener;

public class WeightEditDialog extends DialogFragment {

    private final ReportDetail item;
    private final LayoutInflater inflater;
    private EditText editGross;
    private EditText editTare;
    public WeightEditDialog(ReportDetail item, LayoutInflater inflater, CustomListener customListener) {
        this.item = item;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.edit_weight, null);
        final TextView driverLabel = view.findViewById(R.id.driverLabel);

        if (item.getDriver() != null){
            driverLabel.setText(item.getDriver().toString());
        }

        editGross = view.findViewById(R.id.editGross);
        editTare = view.findViewById(R.id.editTare);
        final EditText editNet = view.findViewById(R.id.editNet);

        Weight ownWeight = item.getOwnWeight();
        if (ownWeight == null){
            ownWeight = new Weight();
            item.setOwnWeight(ownWeight);
        }
        editGross.setText(String.valueOf(ownWeight.getGross()));
        editTare.setText(String.valueOf(ownWeight.getTare()));

        new NetWatcher(editGross, editTare, editNet);

        editGross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGross.selectAll();
            }
        });

        editTare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTare.selectAll();
            }
        });

        builder.setTitle(R.string.weightTitle);
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        return builder.create();
    }

    private void save() {
        final Weight weight = item.getOwnWeight();
        int gross = 0;
        final Editable grossText = editGross.getText();
        if (grossText.length() > 0){
            gross = Integer.parseInt(grossText.toString());
        }
        weight.setGross(gross);
        int tare = 0;
        final Editable tareText = editTare.getText();
        if (tareText.length() > 0){
            tare = Integer.parseInt(tareText.toString());
        }
        weight.setTare(tare);
    }
}
