package ua.quasilin.chekmanszpt.utils;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by szpt_user045 on 08.08.2019.
 */

public class AdapterList {
    private final static ArrayList<ArrayAdapter> adapters = new ArrayList<>();
    public static void add(ArrayAdapter adapter){
        adapters.add(adapter);
    }

    public static void update(){
        for (ArrayAdapter adapter : adapters) {
            adapter.notifyDataSetChanged();
        }
    }

}
