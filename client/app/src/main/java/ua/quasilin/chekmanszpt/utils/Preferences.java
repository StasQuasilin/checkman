package ua.quasilin.chekmanszpt.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class Preferences extends Preference {

    private static final String PREFERENCE_NAME = "preferences";
    private Context context;

    public Preferences(Context context) {
        super(context);
        this.context = context;
    }

    private SharedPreferences getSettings(){
        return context.getSharedPreferences(PREFERENCE_NAME,  Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor(){
        return getSettings().edit();
    }

    public void put(String key, String value){
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.apply();
    }

    public String get(String key, String def){
        return getSettings().getString(key, def);
    }
}
