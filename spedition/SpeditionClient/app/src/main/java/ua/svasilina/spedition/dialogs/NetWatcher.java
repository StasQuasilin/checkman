package ua.svasilina.spedition.dialogs;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NetWatcher implements TextWatcher {

    private final EditText editGross;
    private final EditText editTare;
    private final EditText editNet;

    public NetWatcher(EditText editGross, EditText editTare, EditText editNet) {
        this.editGross = editGross;
        this.editTare = editTare;
        this.editNet = editNet;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        computeNet();
    }

    private void computeNet(){
        final String grossString = editGross.getText().toString();
        final String tareString = editTare.getText().toString();
        if (!grossString.isEmpty() && !tareString.isEmpty()){
            float gross = Float.parseFloat(grossString);
            float tare = Float.parseFloat(tareString);
            float net = gross - tare;
            editNet.setText(String.valueOf(net));
        }
    }
}
