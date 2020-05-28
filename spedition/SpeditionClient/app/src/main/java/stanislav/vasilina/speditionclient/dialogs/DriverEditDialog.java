package stanislav.vasilina.speditionclient.dialogs;

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

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.entity.Driver;
import stanislav.vasilina.speditionclient.entity.Person;
import stanislav.vasilina.speditionclient.utils.CustomListener;

public class DriverEditDialog extends DialogFragment {

    private final Driver driver;
    private final LayoutInflater inflater;

    private EditText surnameEdit;
    private EditText forenameEdit;
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

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void save() {
        final Person person = driver.getPerson();
        person.setSurname(surnameEdit.getText().toString());
        person.setForename(forenameEdit.getText().toString());
        customListener.onChange();
    }
}
