package ua.quasilin.chekmanszpt.entity;

/**
 * Created by szpt_user045 on 08.08.2019.
 */

public class ChatContact {
    private final Worker worker;
    public ChatContact(Object c) {
        worker = new Worker(c);
    }

    public Worker getWorker() {
        return worker;
    }
}
