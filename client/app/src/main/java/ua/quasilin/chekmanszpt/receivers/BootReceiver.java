package ua.quasilin.chekmanszpt.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import ua.quasilin.chekmanszpt.utils.Starter;

/**
 * Created by szpt_user045 on 30.07.2019.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Starter.serviceStart(context);
    }
}
