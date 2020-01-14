package com.example.kvasik.workmanager.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chb";
    private static final String TABLE = "user_data";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + TABLE + " (_key varchar, value varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
