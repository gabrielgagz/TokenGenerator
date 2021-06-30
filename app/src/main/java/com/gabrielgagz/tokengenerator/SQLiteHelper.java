package com.gabrielgagz.tokengenerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_TOKEN_NAME(TOKEN_NAME VARCHAR(30) PRIMARY KEY)");
        db.execSQL("CREATE TABLE USER_TOKEN_CODE(TOKEN_NAME_ID VARCHAR(30) PRIMARY KEY, TOKEN_CODE VARCHAR(64))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER_TOKEN_NAME");
        db.execSQL("DROP TABLE IF EXISTS USER_TOKEN_CODE");
        onCreate(db);
    }
}
