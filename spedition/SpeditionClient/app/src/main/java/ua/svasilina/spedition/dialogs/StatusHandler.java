package ua.svasilina.spedition.dialogs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

import static ua.svasilina.spedition.constants.Keys.REASON;

public class StatusHandler extends Handler {

    private final TextView statusView;

    public StatusHandler(TextView statusView) {
        this.statusView = statusView;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        final Bundle data = msg.getData();
        final String string = data.getString(REASON);
        statusView.setText(string);
    }


}
