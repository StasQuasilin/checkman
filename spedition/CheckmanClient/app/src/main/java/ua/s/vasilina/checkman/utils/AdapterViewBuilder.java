package ua.s.vasilina.checkman.utils;

import android.view.View;

public interface AdapterViewBuilder<T> {
    View build(T item, View view);
}
