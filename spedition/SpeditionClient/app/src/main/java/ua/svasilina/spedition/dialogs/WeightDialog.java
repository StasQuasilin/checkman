package ua.svasilina.spedition.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.entity.Weight;
import ua.svasilina.spedition.utils.CustomListener;

public class WeightDialog extends DialogFragment {
    private final Weight weight;
    private final LayoutInflater inflater;
    private final CustomListener listener;
    private EditText grossEdit;
    private EditText tareEdit;
    private EditText netEdit;

    public WeightDialog(Weight weight, LayoutInflater inflater, CustomListener listener) {
        this.weight = weight;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View view = inflater.inflate(R.layout.edit_weight, null);
        grossEdit = view.findViewById(R.id.editGross);
        tareEdit = view.findViewById(R.id.editTare);
        netEdit =view.findViewById(R.id.editNet);
        NetWatcher watcher = new NetWatcher(grossEdit, tareEdit, netEdit);
        grossEdit.addTextChangedListener(watcher);
        grossEdit.setSelectAllOnFocus(true);
        tareEdit.addTextChangedListener(watcher);
        tareEdit.setSelectAllOnFocus(true);
        grossEdit.setText(String.valueOf(weight.getGross()));
        tareEdit.setText(String.valueOf(weight.getTare()));

        builder.setView(view);
        builder.setTitle(R.string.weightTitle);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        return builder.create();
    }


    private void save() {
        int gross = 0;
        final String grossText = grossEdit.getText().toString();
        if (!grossText.isEmpty()){
            gross = Integer.parseInt(grossText);
        }
        int tare = 0;
        final String tareText = tareEdit.getText().toString();
        if (!tareText.isEmpty()){
            tare = Integer.parseInt(tareText);
        }

        weight.setGross(gross);
        weight.setTare(tare);
        listener.onChange();
    }
}
