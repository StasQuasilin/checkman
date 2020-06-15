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
import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Person;
import ua.svasilina.spedition.utils.CustomListener;

public class DriverEditDialog extends DialogFragment {

    private final Driver driver;
    private final LayoutInflater inflater;

    private EditText surnameEdit;
    private EditText forenameEdit;
    private EditText patronymicEdit;
    private final CustomListener customListener;

    public DriverEditDialog(Driver driver, LayoutInflater inflater, CustomListener customListener) {
        this.driver = driver;
        this.inflater = inflater;
        this.customListener = customListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.driver_edit_dialog, null);

        surnameEdit = view.findViewById(R.id.surnameEdit);
        surnameEdit.setText(driver.getPerson().getSurname());
        forenameEdit = view.findViewById(R.id.forenameEdit);
        forenameEdit.setText(driver.getPerson().getForename());
        patronymicEdit = view.findViewById(R.id.patronymicEdit);
        patronymicEdit.setText(driver.getPerson().getPatronymic());

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void save() {
        final Person person = driver.getPerson();
        person.setSurname(surnameEdit.getText().toString());
        person.setForename(forenameEdit.getText().toString());
        person.setPatronymic(patronymicEdit.getText().toString());
        customListener.onChange();
    }
}
