package ua.s.vasilina.checkman.utils.subscribes;

import ua.s.vasilina.checkman.adapters.SimpleAdapter;

public class Subscriber<T> {

    private final SubscribeType type;
    private final SimpleAdapter<T> adapter;

    public Subscriber(SubscribeType type, SimpleAdapter<T> adapter) {
        this.type = type;
        this.adapter = adapter;
    }
}
