package ua.svasilina.spedition.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ua.svasilina.spedition.R;
import ua.svasilina.spedition.activity.Reports;
import ua.svasilina.spedition.constants.ApiLinks;
import ua.svasilina.spedition.utils.LoginUtil;
import ua.svasilina.spedition.utils.NetworkUtil;

import static ua.svasilina.spedition.constants.Keys.EMPTY;
import static ua.svasilina.spedition.constants.Keys.PASSWORD;
import static ua.svasilina.spedition.constants.Keys.PHONE;
import static ua.svasilina.spedition.constants.Keys.REASON;
import static ua.svasilina.spedition.constants.Keys.STATUS;
import static ua.svasilina.spedition.constants.Keys.SUCCESS;
import static ua.svasilina.spedition.constants.Keys.TOKEN;

public class LoginDialog extends DialogFragment {

    private Context context;
    private EditText phoneEdit;
    private EditText passwordEdit;
    private ProgressBar progressBar;
    private TextView statusView;
    private final LoginUtil loginUtil;
    private NetworkUtil networkUtil;
    private final LayoutInflater inflater;
    private boolean isAuthorize;
    private boolean waitAnswer = false;
    private Reports reports;

    public LoginDialog(Reports reports, LayoutInflater inflater) {
        this.context = reports.getApplicationContext();
        this.reports = reports;
        this.inflater = inflater;
        loginUtil = new LoginUtil(reports);
        networkUtil = new NetworkUtil();
        isAuthorize = loginUtil.getToken() != null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        Log.i("Login", "Create dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (isAuthorize) {
            final View view = inflater.inflate(R.layout.already_login, null);
            final Button removeToken = view.findViewById(R.id.removeToken);
            removeToken.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUtil.removeToken();
                    dismiss();
                    reports.showLoginDialog();
                }
            });
            final Button close = view.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            builder.setView(view);
        } else {
            final View view = inflater.inflate(R.layout.activity_login, null);

            phoneEdit = view.findViewById(R.id.editTextPhone);
            phoneEdit.setText(getNumber());

            passwordEdit = view.findViewById(R.id.editPassword);
            final Button loginButton = view.findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Login", "Press login button");
                    if (!waitAnswer) {
                        login();
                    }
                }
            });
            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);
            statusView = view.findViewById(R.id.statusText);

            builder.setView(view);
        }

        return builder.create();
    }

    @SuppressLint("HardwareIds")
    private String getNumber() {
//        final Context context = getContext();
//        assert context != null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//                List<SubscriptionInfo> subscription = SubscriptionManager.from(context).getActiveSubscriptionInfoList();
//                return subscription.get(0).getNumber();
//            }
//
//        } else {
//            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//                assert telephonyManager != null;
//                return telephonyManager.getLine1Number();
//            }
//        }
        return EMPTY;
    }

    private void login() {
        progressBar.setVisibility(View.INVISIBLE);
        waitAnswer = true;
        progressBar.setVisibility(View.VISIBLE);
        final String login = phoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        final JSONObject json = new JSONObject();
        json.put(PHONE, login);
        json.put(PASSWORD, Base64.encodeToString(password.getBytes(), Base64.NO_WRAP));

        final StatusHandler statusHandler = new StatusHandler(statusView, progressBar);
        final LoginHandler loginHandler = new LoginHandler(loginUtil);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String post = networkUtil.post(ApiLinks.LOGIN, json.toJSONString(), null);
                    waitAnswer = false;
                    if (post != null) {
                        Log.i("Login", post);

                        JSONParser parser = new JSONParser();
                        if (post.contains("<")) {
                            sendMessage(statusHandler, REASON, "Probably answer is html\nprobably answer is 404");
                        } else {
                            try {
                                JSONObject json = (JSONObject) parser.parse(post);
                                String status = String.valueOf(json.get(STATUS));
                                if (status.equals(SUCCESS)) {
                                    String token = String.valueOf(json.get(TOKEN));
                                    sendMessage(loginHandler, TOKEN, token);
                                    statusHandler.removeCallbacksAndMessages(null);
                                    dismiss();
                                } else {
                                    String reason = String.valueOf(json.get(REASON));
                                    sendMessage(statusHandler, REASON, reason);
                                }
                            } catch (ParseException e) {
                                sendMessage(statusHandler, REASON, "Can't parse answer");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        sendMessage(statusHandler, REASON, "No server answer");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }){
        }.start();
    }

    private void sendMessage(Handler handler, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
