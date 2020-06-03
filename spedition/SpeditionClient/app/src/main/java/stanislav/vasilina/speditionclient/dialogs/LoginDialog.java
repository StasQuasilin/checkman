package stanislav.vasilina.speditionclient.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import stanislav.vasilina.speditionclient.R;
import stanislav.vasilina.speditionclient.utils.LoginUtil;

public class LoginDialog extends DialogFragment {

    private final LoginUtil loginUtil;
    private final LayoutInflater inflater;
    private boolean isAuthorize;

    public LoginDialog(Context context, LayoutInflater inflater) {
        this.inflater = inflater;
        loginUtil = new LoginUtil(context);
        isAuthorize = loginUtil.getToken() != null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (isAuthorize){
            final View view = inflater.inflate(R.layout.already_login, null);
            builder.setView(view);
        } else {
            final View view = inflater.inflate(R.layout.activity_login, null);
            builder.setView(view);
        }

        return builder.create();
    }
}
