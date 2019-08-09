package ua.quasilin.chekmanszpt.entity;

import android.support.annotation.NonNull;

/**
 * Created by szpt_user045 on 08.08.2019.
 */

public class ChatContact implements Comparable<ChatContact> {
    private final Worker worker;
    public ChatContact(Object c) {
        worker = new Worker(c);
    }

    public Worker getWorker() {
        return worker;
    }

    @Override
    public int compareTo(@NonNull ChatContact o) {
        return worker.compareTo(o.worker);
    }
}
