package ua.quasilin.chekmanszpt.entity;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by szpt_user045 on 08.08.2019.
 */

public class ChatContact implements Comparable<ChatContact> {
    private final Worker worker;
    public ChatContact(Object c) {
        worker = new Worker(c);
    }
    private boolean selected = false;

    public Worker getWorker() {
        return worker;
    }

    @Override
    public int compareTo(@NonNull ChatContact o) {
        return worker.compareTo(o.worker);
    }

    public void select(boolean select) {
        Log.i(worker.getValue() + " select", String.valueOf(select));
        selected = select;
    }

    public boolean isSelected() {
        return selected;
    }
}
