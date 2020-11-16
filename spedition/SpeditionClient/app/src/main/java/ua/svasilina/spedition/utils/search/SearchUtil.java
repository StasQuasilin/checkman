package ua.svasilina.spedition.utils.search;

import android.widget.ArrayAdapter;

import java.util.LinkedList;

public abstract class SearchUtil<T> {

    public void search(ArrayAdapter<T> adapter, String key){
        adapter.clear();
        adapter.addAll(findItems(key));
    }
    abstract LinkedList<T> findItems(String key);
}
