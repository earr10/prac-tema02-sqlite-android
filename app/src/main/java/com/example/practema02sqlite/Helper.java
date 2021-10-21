package com.example.practema02sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contactos2 (id INTEGER, nombre TEXT, apellidos TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos2");
        db.execSQL("CREATE TABLE contactos2 (id INTEGER, nombre TEXT, apellidos TEXT)");
    }
}
